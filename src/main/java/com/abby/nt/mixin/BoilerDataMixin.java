package com.abby.nt.mixin;

import com.abby.nt.registry.NuclearFluids;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BoilerData.BoilerFluidHandler.class)
public class BoilerDataMixin {

    @Final
    @Shadow(remap = false)  BoilerData this$0;
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public boolean isFluidValid(int tank, FluidStack stack) {
        return FluidHelper.isWater(stack.getFluid())||stack.getFluid().getFluidType().equals(NuclearFluids.STEAM.get().getFluidType());
    }


    @Inject(method = "fill",at = @At(value = "TAIL"), remap = false, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void fillSteam(FluidStack resource, IFluidHandler.FluidAction action, CallbackInfoReturnable<Integer> cir) {
        if(resource.getFluid().getFluidType().equals(NuclearFluids.STEAM.get().getFluidType())){
            this$0.activeHeat=20;
            this$0.needsHeatLevelUpdate=false;
        }
        else
            if(this$0.activeHeat==20)
                this$0.needsHeatLevelUpdate=true;
    }

}
