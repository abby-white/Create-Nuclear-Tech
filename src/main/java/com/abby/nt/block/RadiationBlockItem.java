package com.abby.nt.block;

import com.abby.nt.CreateNuclear;
import com.abby.nt.radiation.PlayerRadiationData;
import com.abby.nt.radiation.PlayerRadiationProvider;
import com.abby.nt.radiation.WorldRadiationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadiationBlockItem extends BlockItem {
    private final double radsPerSecond;

    public RadiationBlockItem(Block pBlock, Properties pProperties, double radsPerSecond) {
        super(pBlock, pProperties);
        this.radsPerSecond = radsPerSecond;
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pEntity instanceof Player player)
            WorldRadiationManager.applyRadiationToPlayer(player,radsPerSecond/20);
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Math.abs(radsPerSecond)>=.01){
            pTooltipComponents.add(Component.literal("[radioactive]").withStyle(ChatFormatting.GREEN));
            pTooltipComponents.add(Component.literal(radsPerSecond+ " RAD/s").withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
