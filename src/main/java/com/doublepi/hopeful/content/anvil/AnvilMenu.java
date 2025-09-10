package com.doublepi.hopeful.content.anvil;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.registries.ModItems;
import com.doublepi.hopeful.registries.ModMenus;
import com.doublepi.hopeful.content.scrolls.Scroll;
import com.doublepi.hopeful.content.scrolls.ScrollHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AnvilMenu extends ItemCombinerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int SCROLL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public boolean used = false;
    private String itemName;

    public AnvilMenu(int containerId, Inventory playerInventory,
                     RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public AnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.NEW_ANVIL_MENU.get(), containerId, playerInventory, access);
    }

    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(INPUT_SLOT, 27, 47, (itemstack)->true)
                .withSlot(SCROLL_SLOT, 76, 47, (p_266634_) -> p_266634_.is(ModItems.SCROLL))
                .withResultSlot(RESULT_SLOT, 134, 47)
                .build();
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
        return s.length() <= 50 ? s : null;
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
//            if (this.getSlot(2).hasItem()) {
//                ItemStack itemstack = this.getSlot(2).getItem();
//                if (StringUtil.isBlank(s)) {
//                    itemstack.remove(DataComponents.CUSTOM_NAME);
//                } else {
//                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
//                }
//            }

            this.createResult();
            return true;
        } else {
            return false;
        }
    }


    protected boolean isValidBlock(BlockState state) {
        return state.is(BlockTags.ANVIL);
    }

    @Override
    protected boolean mayPickup(Player player, boolean b) {
        return true;
    }

    protected void onTake(Player player, ItemStack stack) {
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        base.shrink(1);
        this.inputSlots.setItem(INPUT_SLOT, base);

        ItemStack scroll = this.inputSlots.getItem(SCROLL_SLOT);
        scroll.shrink(1);
        this.inputSlots.setItem(SCROLL_SLOT, scroll);

        HopefulMod.LOGGER.error("stack: "+stack);
        player.playSound(SoundEvents.ANVIL_USE);

    }

    public void createResult() { //TODO: Add Name
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        ItemStack scrollItem = this.inputSlots.getItem(SCROLL_SLOT);
        ItemStack result = base.copy();

        if(scrollItem.has(ModDataComponentTypes.SCROLL)) {
            Scroll scroll = scrollItem.get(ModDataComponentTypes.SCROLL).value();
            if(base.isEmpty() || !ScrollHelper.supportsScroll(base, scroll)) {

                this.resultSlots.setItem(RESULT_SLOT,ItemStack.EMPTY);
                used = false;
                return;
            }
            used = true;
            ScrollHelper.enchant(result, scroll);
        } else {
            if(!StringUtil.isBlank(itemName)){
                HopefulMod.LOGGER.error("loading name: "+itemName);
                result.set(DataComponents.CUSTOM_NAME,Component.literal(itemName));
            }
        }
        resultSlots.setItem(RESULT_SLOT,result);

    }
}
