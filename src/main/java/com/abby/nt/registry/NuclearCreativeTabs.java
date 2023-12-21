package com.abby.nt.registry;

import com.abby.nt.CreateNuclear;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.abby.nt.CreateNuclear.MODID;
import static com.abby.nt.CreateNuclear.REGISTRATE;

public class NuclearCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateNuclear.MODID);

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = addTab("base", "Create: Nuclear Tech",
            AllItems.CRUSHED_IRON::asStack);


    public static RegistryObject<CreativeModeTab> addTab(String id, String name, Supplier<ItemStack> icon) {
        REGISTRATE.addRawLang("itemGroup."+MODID+"."+ id, name);
        CreativeModeTab.Builder tabBuilder= CreativeModeTab.builder()
                .icon(icon)
                .displayItems((pParameters, pOutput) -> {
                    for (RegistryEntry<Item> item:REGISTRATE.getAll(ForgeRegistries.ITEMS.getRegistryKey())) {
                        pOutput.accept(item.get());
                    }
                })
                .title(Components.translatable("itemGroup."+MODID+"."+ id))
                .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey());
        return REGISTER.register(id, tabBuilder::build);
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }


}
