package com.abby.nt.events;

import com.abby.nt.CreateNuclear;
import com.abby.nt.radiation.PlayerRadiationData;
import com.abby.nt.radiation.PlayerRadiationProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateNuclear.MODID)
public class CommonEvent {

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerRadiationProvider.PLAYER_RADIATION_DATA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerRadiationProvider.PLAYER_RADIATION_DATA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerRadiationData.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerRadiationProvider.PLAYER_RADIATION_DATA).isPresent()) {
                event.addCapability(CreateNuclear.asResource("radiation_data"), new PlayerRadiationProvider());
            }
        }
    }
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        CreateNuclear.RADIATION.playerTick(event.player);
    }

    @SubscribeEvent
    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor world = event.getLevel();
        CreateNuclear.RADIATION.levelLoaded(world);
    }


    public static void init() {
    }
}
