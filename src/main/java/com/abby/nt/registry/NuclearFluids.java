package com.abby.nt.registry;

import com.abby.nt.CreateNuclear;
import com.simibubi.create.AllFluids;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.*;

import static com.abby.nt.CreateNuclear.REGISTRATE;

public class NuclearFluids {

    public static void init(){};

    public static final FluidEntry<Flowing> STEAM = register("steam", NoColorFluidAttributes::new);



    @SuppressWarnings("SameParameterValue")
    private static FluidEntry<Flowing> register(String name, String resourceLocation, FluidBuilder.FluidTypeFactory typeFactory,
                                                                  NonNullFunction<ForgeFlowingFluid.Properties, ? extends ForgeFlowingFluid> customSource,
                                                                  int viscosity, int density, int levelDecreasePerBlock,
                                                                  int tickRate, int slopeFindDistance, float explosionResistance) {

        return REGISTRATE.fluid(name,
                        CreateNuclear.asResource("fluid/%s_still".formatted(resourceLocation)),
                        CreateNuclear.asResource("fluid/%s_flow".formatted(resourceLocation)),
                        typeFactory)
                .lang(Character.toUpperCase(name.charAt(0)) + name.substring(1))
                .properties(b -> b.viscosity(viscosity).density(density))
                .fluidProperties(p ->
                        p.levelDecreasePerBlock(levelDecreasePerBlock)
                                .tickRate(tickRate)
                                .slopeFindDistance(slopeFindDistance)
                                .explosionResistance(explosionResistance)
                )
                .source(customSource)
                .bucket()
                .build()
                .register();
    }
    private static FluidEntry<Flowing> register(String name, String resourceLocation, FluidBuilder.FluidTypeFactory typeFactory) {
        return register(name, resourceLocation, typeFactory, ForgeFlowingFluid.Source::new);
    }

    private static FluidEntry<Flowing> register(String name, String resourceLocation, FluidBuilder.FluidTypeFactory typeFactory,
                                                NonNullFunction<ForgeFlowingFluid.Properties, ? extends ForgeFlowingFluid> customSource) {
        return register(name, resourceLocation, typeFactory, customSource, 2000, 0, 2, 25, 3, 100f);
    }

    private static FluidEntry<Flowing> register(String name, FluidBuilder.FluidTypeFactory typeFactory) {
        return register(name, name, typeFactory);
    }
    private static class NoColorFluidAttributes extends AllFluids.TintedFluidType {
        public NoColorFluidAttributes(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }
    }
}
