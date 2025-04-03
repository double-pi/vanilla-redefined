package com.doublepi.hopeful.registries;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.tomes.Tome;
import com.doublepi.hopeful.tomes.TomeItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.ref.Reference;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HopefulMod.MODID);

    public static final Supplier<CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register("hopeful_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("hopeful.tab"))
                    .icon(() -> new ItemStack(ModItems.TOME.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pParameters.holders().lookup(ModTomes.TOME_REGISTRY_KEY).ifPresent(
                                tomeRegistryLookup -> {
                                    HopefulMod.LOGGER.error("registry lookup "+ tomeRegistryLookup.listElements().count());
                                    generateTomes(pOutput,tomeRegistryLookup);
                                });


                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static void generateTomes(CreativeModeTab.Output output, HolderLookup<Tome> tomes) {
        tomes.listElements()
                .map(tomeReference -> TomeItem.createForTome(tomeReference.value()))
                        .forEach(itemStack -> output.accept(itemStack,
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
    }
}
