package com.doublepi.hopeful.forge;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModBlocks;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.registries.ModItems;
import com.doublepi.hopeful.registries.ModMenus;
import com.doublepi.hopeful.tomes.Tome;
import com.doublepi.hopeful.tomes.TomeHelper;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class ForgeMenu extends ItemCombinerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int TOME_SLOT = 1;
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
                .withSlot(INPUT_SLOT, 27, 47, (p_266635_) -> true)
                .withSlot(TOME_SLOT, 76, 47, (p_266634_) -> p_266634_.is(ModItems.TOME))
                .withResultSlot(RESULT_SLOT, 134, 47)
                .build();
    }


    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.FORGE);
    }

    protected boolean mayPickup(Player player, boolean hasStack) {
        return player.hasInfiniteMaterials();
    }

    protected void onTake(Player player, ItemStack stack) {
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        base.shrink(1);
        this.inputSlots.setItem(INPUT_SLOT, base);

        ItemStack tome = this.inputSlots.getItem(TOME_SLOT);
        tome.shrink(1);
        this.inputSlots.setItem(TOME_SLOT, tome);
    }

    public void createResult() {
        ItemStack base = this.inputSlots.getItem(INPUT_SLOT);
        ItemStack tomeItem = this.inputSlots.getItem(TOME_SLOT);
        ItemStack result = base.copy();

        if(!tomeItem.has(ModDataComponentTypes.TOME_DATA))
            return;
        Tome tome = tomeItem.get(ModDataComponentTypes.TOME_DATA);
        if(base.isEmpty() || !TomeHelper.supportsTome(base,tome))
            return;

        TomeHelper.enchant(result, tome);
        resultSlots.setItem(RESULT_SLOT,result);
    }
}
