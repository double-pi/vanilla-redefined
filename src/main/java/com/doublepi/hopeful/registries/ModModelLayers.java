package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation MOURNER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HopefulMod.MODID, "mourner"), "main");

    public static final ModelLayerLocation MOURNER_OUTER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HopefulMod.MODID, "mourner"), "outer");

}
