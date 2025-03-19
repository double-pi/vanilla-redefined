package com.doublepi.vanilla_redefined.registries;

import com.doublepi.vanilla_redefined.VanillaRedefinedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VanillaRedefinedMod.MODID);

    public static final Supplier<CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register("vanilla_redefined_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("vanilla_redefined.tab"))
                    .icon(() -> new ItemStack(ModItems.AQUATIC_RUNE.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.AQUATIC_RUNE);
                        pOutput.accept(ModItems.BLAZE_RUNE);
                        pOutput.accept(ModItems.ROOTED_RUNE);
                        pOutput.accept(ModItems.BREEZE_RUNE);
                        pOutput.accept(ModItems.DEEP_SLATED_RUNE);


                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
