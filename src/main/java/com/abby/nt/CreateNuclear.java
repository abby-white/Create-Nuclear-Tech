package com.abby.nt;

import com.abby.nt.events.CommonEvent;
import com.abby.nt.radiation.WorldRadiationManager;
import com.abby.nt.registry.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mod(CreateNuclear.MODID)
public class CreateNuclear {
    public static final String MODID = "create_nuclear";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final WorldRadiationManager RADIATION = new WorldRadiationManager();

    public CreateNuclear() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        NuclearCreativeTabs.register(modEventBus);
        NuclearLang.init();
        NuclearItems.init();
        NuclearBlocks.init();
        NuclearBlockEntities.init();
        NuclearPartials.init();
        CommonEvent.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MODID, name);
    }

    public static String toHumanReadable(String key) {
        String s = key.replaceAll("_", " ");
        s = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(s)).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        s = StringUtils.normalizeSpace(s);
        return s;
    }

}
