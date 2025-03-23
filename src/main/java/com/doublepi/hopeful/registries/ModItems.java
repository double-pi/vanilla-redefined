package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.items.AbstractRune;
import com.doublepi.hopeful.items.SculkDebug;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HopefulMod.MODID);


    public static final DeferredItem<Item> AQUATIC_RUNE = ITEMS.register("aquatic_rune",
            ()-> new AbstractRune(new Item.Properties()));

    public static final DeferredItem<Item> BLAZE_RUNE = ITEMS.register("blaze_rune",
            ()-> new AbstractRune(new Item.Properties()));

    public static final DeferredItem<Item> ROOTED_RUNE = ITEMS.register("rooted_rune",
            ()-> new AbstractRune(new Item.Properties()));

    public static final DeferredItem<Item> BREEZE_RUNE = ITEMS.register("breeze_rune",
            ()-> new AbstractRune(new Item.Properties()));

    public static final DeferredItem<Item> DEEP_SLATED_RUNE = ITEMS.register("deep_slated_rune",
            ()-> new AbstractRune(new Item.Properties()));

    public static final DeferredItem<Item> SCULK_DEBUG = ITEMS.register("sculk_debug",
            ()-> new SculkDebug(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
