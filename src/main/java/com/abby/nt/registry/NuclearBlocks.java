package com.abby.nt.registry;


import com.abby.nt.block.RadiationBlockItem;
import com.abby.nt.block.RadioactiveBlock;
import com.tterrag.registrate.util.entry.BlockEntry;

import static com.abby.nt.CreateNuclear.REGISTRATE;

public class NuclearBlocks {
    public static void init(){}

    public static final BlockEntry<RadioactiveBlock> URANIUM_BLOCK= REGISTRATE
            .block("uranium_block",properties -> new RadioactiveBlock(properties,5))
            .item((radioactiveBlock, properties) -> new RadiationBlockItem(radioactiveBlock,properties,5))
            .build()
            .register();

    public static final BlockEntry<RadioactiveBlock> SPONGE_BLOCK= REGISTRATE
            .block("radioactive_sponge",properties -> new RadioactiveBlock(properties,-1))
            .item((radioactiveBlock, properties) -> new RadiationBlockItem(radioactiveBlock,properties,-1))
            .build()
            .register();




}
