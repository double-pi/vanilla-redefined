package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.tomes.Tome;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class ModTomes {
    public static final ResourceKey<Registry<Tome>> TOME_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(HopefulMod.MODID, "tomes"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        HopefulMod.LOGGER.error("is this being run?"); //yes
        event.dataPackRegistry(TOME_REGISTRY_KEY,Tome.CODEC,Tome.CODEC,builder -> builder.maxId(256));
    }
}
