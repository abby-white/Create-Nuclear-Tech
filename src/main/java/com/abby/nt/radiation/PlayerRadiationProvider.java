package com.abby.nt.radiation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerRadiationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerRadiationData> PLAYER_RADIATION_DATA = CapabilityManager.get(new CapabilityToken<PlayerRadiationData>() {
    });

    private PlayerRadiationData data = null;
    private final LazyOptional<PlayerRadiationData> optional = LazyOptional.of(this::createPlayerData);

    private PlayerRadiationData createPlayerData() {
        if (this.data == null) {
            this.data = new PlayerRadiationData();
        }

        return this.data;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_RADIATION_DATA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerData().loadNBTData(nbt);
    }


}
