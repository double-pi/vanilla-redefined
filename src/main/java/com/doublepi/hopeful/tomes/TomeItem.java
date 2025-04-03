package com.doublepi.hopeful.tomes;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.registries.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TomeItem extends Item {
    public TomeItem(Item.Properties properties) {
        super(properties);
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public static ItemStack createForTome(Tome instance) {
        ItemStack itemstack = new ItemStack(ModItems.TOME.get());
        itemstack.set(ModDataComponentTypes.TOME_DATA, instance);
        return itemstack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(!stack.has(ModDataComponentTypes.TOME_DATA))
            return;
        Tome tome =stack.get(ModDataComponentTypes.TOME_DATA);

        MutableComponent text = Component.empty();
        text.append(tome.tomeType().getDisplayName());
        text.append(Component.literal(" of "));
        text.append(tome.title());
        tooltipComponents.add(text);

    }
}
