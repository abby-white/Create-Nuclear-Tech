package com.abby.nt.block;

import com.abby.nt.CreateNuclear;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RadioactiveBlock extends Block {
    private final double radPerSecond;

    public RadioactiveBlock(Properties pProperties, double radPerSecond) {
        super(pProperties);
        this.radPerSecond = radPerSecond;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if(!pLevel.isClientSide)
            CreateNuclear.RADIATION.setRadiation(pPos,radPerSecond,pLevel);

        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(!pLevel.isClientSide)
            CreateNuclear.RADIATION.remove(pPos,pLevel);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }


}
