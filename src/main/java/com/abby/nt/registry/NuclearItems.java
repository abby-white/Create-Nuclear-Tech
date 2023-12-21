package com.abby.nt.registry;

import com.abby.nt.CreateNuclear;
import com.abby.nt.item.GeigerCounter;
import com.abby.nt.item.RadiationItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;

import static com.abby.nt.CreateNuclear.REGISTRATE;

public class NuclearItems {
    public static void init(){}

    public static final ItemEntry<RadiationItem> URANIUM_INGOT= REGISTRATE
            .item("uranium_ingot",properties -> new RadiationItem(properties,.5))
            .register();

    public static final ItemEntry<GeigerCounter> GEIGER_COUNTER= REGISTRATE
            .item("geiger_counter", GeigerCounter::new)
            .register();
    private static <T extends Item> ItemEntry<T> register(String name, NonNullFunction<Item.Properties, T> factory) {
        return REGISTRATE.item(name, factory).register();
    }
    private static ItemEntry<Item> simple(String name) {
        return register(name, Item::new);
    }
}
