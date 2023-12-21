package com.abby.nt.registry;

import com.abby.nt.CreateNuclear;
import com.simibubi.create.foundation.utility.LangBuilder;

import static com.abby.nt.CreateNuclear.REGISTRATE;

public class NuclearLang {

    public static LangBuilder builder() {
        return new LangBuilder(CreateNuclear.MODID);
    }

    public static void init(){};



    private static LangBuilder translate(String langKey, String en, Object... args) {
        REGISTRATE.addRawLang(CreateNuclear.MODID+"."+langKey,en);
        return builder().translate(langKey, args);
    }
}
