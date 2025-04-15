package com.doublepi.hopeful.forge;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModBlocks;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.registries.ModItems;
import com.doublepi.hopeful.registries.ModMenus;
import com.doublepi.hopeful.scrolls.Scroll;
import com.doublepi.hopeful.scrolls.ScrollHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeMenu extends ItemCombinerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int SCROLL_SLOT = 1;
    public static final int RESULT_SLOT = 2;

    public ForgeMenu(int containerId, Inventory playerInventory,
                     RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ForgeMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.FORGE_MENU.get(), containerId, playerInventory, access);
    }


    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(INPUT_SLOT, 27, 47, ItemStack::isDamageableItem)
                .withSlot(SCROLL_SLOT, 76, 47, (p_266634_) -> p_266634_.is(ModItems.SCROLL))
                .withResultSlot(RESULT_SLOT, 134, 47)
                .build();
    }


    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.FORGE);
    }


    @Override
    protected boolean mayPickup(Player player, boolean b) {
        return true;
    }

    protected void onTake(Player player, ItemStack stack) {
        HopefulMod.LOGGER.error("onTake called");
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        base.shrink(1);
        this.inputSlots.setItem(INPUT_SLOT, base);

        ItemStack scroll = this.inputSlots.getItem(SCROLL_SLOT);
        scroll.shrink(1);
        this.inputSlots.setItem(SCROLL_SLOT, scroll);
    }

    public void createResult() {
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        ItemStack scrollItem = this.inputSlots.getItem(SCROLL_SLOT);
        ItemStack result = base.copy();

        if(!scrollItem.has(ModDataComponentTypes.SCROLL)) {
            this.resultSlots.setItem(RESULT_SLOT,ItemStack.EMPTY);
            return;
        }
        Scroll scroll = scrollItem.get(ModDataComponentTypes.SCROLL);
        if(base.isEmpty() || !ScrollHelper.supportsScroll(base, scroll)) {
            this.resultSlots.setItem(RESULT_SLOT,ItemStack.EMPTY);
            return;
        }

        ScrollHelper.enchant(result, scroll);
        resultSlots.setItem(RESULT_SLOT,result);
    }
}
