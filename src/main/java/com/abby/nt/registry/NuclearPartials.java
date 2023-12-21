package com.abby.nt.registry;

import com.abby.nt.CreateNuclear;
import com.jozufozu.flywheel.core.PartialModel;

public class NuclearPartials {

    public static void init(){};

    private static PartialModel block(String path) {
        return new PartialModel(CreateNuclear.asResource("block/" + path));
    }

}
