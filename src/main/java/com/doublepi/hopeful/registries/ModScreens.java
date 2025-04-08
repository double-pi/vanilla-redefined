package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.forge.ForgeScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModScreens {

    public static void register(RegisterMenuScreensEvent event){
        event.register(ModMenus.FORGE_MENU.get(), ForgeScreen::new);
    }
}

