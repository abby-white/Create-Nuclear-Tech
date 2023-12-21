package com.abby.nt.radiation;

import com.abby.nt.CreateNuclear;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class WorldRadiationData extends SavedData {

    private final Map<BlockPos,Double> RadiationSourceMap = new HashMap<>();
    public static WorldRadiationData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(WorldRadiationData::load, WorldRadiationData::new, "nuclear_radiation");
    }

    public Map<BlockPos, Double> getRadiationSourceMap() {
        return RadiationSourceMap;
    }

    private static WorldRadiationData load(CompoundTag nbt) {
        WorldRadiationData data=new WorldRadiationData();
        NBTHelper.iterateCompoundList(nbt.getList("radTags", Tag.TAG_COMPOUND), c -> {
            data.RadiationSourceMap.put(NbtUtils.readBlockPos(c.getCompound("blockPos")),c.getDouble("rad"));
        });
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag list = new ListTag();
        CreateNuclear.RADIATION.radiationSourceMap.forEach((BlockPos, radiation) -> {
            CompoundTag radTag = new CompoundTag();
            radTag.put("blockPos",NbtUtils.writeBlockPos(BlockPos));
            radTag.putDouble("rad", radiation);
            list.add(radTag);
        });
        pCompoundTag.put("radTags", list);
        return pCompoundTag;
    }

}
