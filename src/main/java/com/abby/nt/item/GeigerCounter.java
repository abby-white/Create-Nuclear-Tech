package com.abby.nt.item;

import com.abby.nt.CreateNuclear;
import com.abby.nt.radiation.PlayerRadiationData;
import com.abby.nt.radiation.PlayerRadiationProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class GeigerCounter extends Item {
    public GeigerCounter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        LazyOptional<PlayerRadiationData> data = pPlayer.getCapability(PlayerRadiationProvider.PLAYER_RADIATION_DATA);
        if(!pLevel.isClientSide) {
            data.ifPresent(playerRadiationData ->
                    pPlayer.sendSystemMessage(Component.literal("Player Radiation: " + playerRadiationData.getRadiation()+" RAD").withStyle(ChatFormatting.RED)));
            pPlayer.sendSystemMessage(Component.literal("Environment Radiation: " + CreateNuclear.RADIATION.getRadiationInChunk(pLevel,pPlayer.getOnPos())+" RAD/s").withStyle(ChatFormatting.RED));
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
