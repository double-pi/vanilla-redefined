package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModEnchantmentEffect {

    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_COMPONENT_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, HopefulMod.MODID);

//    DataComponentType<Unit> PREVENT_EQUIPMENT_DROP =
//            ENCHANTMENT_COMPONENT_TYPES.register("prevent_equipment_drop", () -> p_346368_.persistent(Unit.CODEC));


}
// https://github.com/iMoonDay/Soulbound/blob/neoforge-1.21/src/main/java/com/imoonday/soulbound/mixin/InventoryMixin.java