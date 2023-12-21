package com.abby.nt.radiation;


import net.minecraft.nbt.CompoundTag;

public class PlayerRadiationData {
    private double rad = 0;

    public double getRadiation() {
        return Math.round(rad*100)/100.0;
    }
    public void addRadiation(double radAmount) {
        if(Math.abs(radAmount)>.001)
            rad+=radAmount;
    }


    public void copyFrom(PlayerRadiationData source) {
        this.rad = source.rad;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putDouble("rad", rad);
    }

    public void loadNBTData(CompoundTag nbt) {
        rad = nbt.getDouble("rad");
    }
}