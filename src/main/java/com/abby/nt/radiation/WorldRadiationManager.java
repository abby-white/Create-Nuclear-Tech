package com.abby.nt.radiation;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public class WorldRadiationManager {
    Map<BlockPos,Double> radiationSourceMap = new HashMap<>();
    Map<ChunkPos,Double> radiationChunkMap = new HashMap<>();

    public static final int MAX_CHUNK_SPREAD =4;
    private WorldRadiationData savedData;
    public void levelLoaded(LevelAccessor level) {
        MinecraftServer server = level.getServer();
        if (server == null || server.overworld() != level)
            return;
        savedData = null;
        loadRadiationData(server);
        calculateChunkMap(level);

    }

    private void loadRadiationData(MinecraftServer server) {
        if (savedData != null)
            return;
        savedData = WorldRadiationData.load(server);
        radiationSourceMap =savedData.getRadiationSourceMap();
    }

    private void calculateChunkMap(LevelAccessor level) {
        radiationChunkMap.clear();
        radiationSourceMap.forEach((blockPos, rad) -> {
            ChunkPos radiationPos=level.getChunk(blockPos).getPos();
            updateSurroundingChunks(radiationPos,rad);
        });
    }

    private void updateSurroundingChunks(ChunkPos sourcePos, double sourceRadiation) {
        for (int i = -MAX_CHUNK_SPREAD; i < MAX_CHUNK_SPREAD; i++) {
            for (int j = -MAX_CHUNK_SPREAD; j < MAX_CHUNK_SPREAD; j++) {
                ChunkPos surroundingPos = new ChunkPos(sourcePos.x + i, sourcePos.z + j);
                int chunkSpread=calculateChunkDistance(sourcePos,surroundingPos);
                if(chunkSpread<= MAX_CHUNK_SPREAD){
                    double exisitingRadiation=0;
                    if(radiationChunkMap.containsKey(surroundingPos))
                        exisitingRadiation=radiationChunkMap.get(surroundingPos);
                    radiationChunkMap.put(surroundingPos,exisitingRadiation+(sourceRadiation*Math.pow(0.5,chunkSpread)));
                }
            }
        }
    }

    private int calculateChunkDistance(ChunkPos posA,ChunkPos posB){
       return Math.abs(posA.x - posB.x)+Math.abs(posA.z - posB.z);
    }

    public void setRadiation(BlockPos pos, Double rad, Level pLevel){
        if(Math.abs(rad)>=.01) {
            radiationSourceMap.put(pos, rad);
            markRadiationDirty();
            calculateChunkMap(pLevel);
        }
    }

    public static void applyRadiationToPlayer(Player player,double amount){
        if(player.level().isClientSide()) return;
        LazyOptional<PlayerRadiationData> data= player.getCapability(PlayerRadiationProvider.PLAYER_RADIATION_DATA);
        data.ifPresent(playerRadiationData -> playerRadiationData.addRadiation(amount));
    }
    public double getRadiationInChunk(Level level, BlockPos pos){
        ChunkPos chunkPos=level.getChunk(pos).getPos();
        if(radiationChunkMap.containsKey(chunkPos))
            return radiationChunkMap.get(chunkPos);
        return 0;
    }

    public void playerTick(Player player){
        applyRadiationToPlayer(player,getRadiationInChunk(player.level(),player.getOnPos())/20);
    }
    public void markRadiationDirty() {
        if (savedData != null)
            savedData.setDirty();
    }

    public void remove(BlockPos pPos, Level pLevel) {
        radiationSourceMap.remove(pPos);
        markRadiationDirty();
        calculateChunkMap(pLevel);
    }
}
