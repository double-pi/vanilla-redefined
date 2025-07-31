package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.anvil.AnvilScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModScreens {

    public static void register(RegisterMenuScreensEvent event){
        event.register(ModMenus.NEW_ANVIL_MENU.get(), AnvilScreen::new);
    }
}

