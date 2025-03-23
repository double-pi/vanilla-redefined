package com.doublepi.hopeful.mixins;

import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Shadow @Final private static int COST_RENAME = 0;
}
