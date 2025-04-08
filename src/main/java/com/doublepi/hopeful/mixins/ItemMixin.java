package com.doublepi.hopeful.mixins;

import com.doublepi.hopeful.registries.ModDataComponentTypes;
import com.doublepi.hopeful.tomes.TomeHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {


    @Inject(method = "appendHoverText", at=@At("HEAD"))
    private void injected(ItemStack stack, Item.TooltipContext context,
                          List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci){
        if(stack.getEnchantmentValue()!=0){
            MutableComponent text = Component.empty();
            text.append(Component.translatable("tooltip.hopeful.enchant_status"));
            text.append(Component.literal(": ("));
            if(stack.has(ModDataComponentTypes.ENCHANTABILITY_STATUS))
                text.append(Component.literal(stack.get(ModDataComponentTypes.ENCHANTABILITY_STATUS)+"/"));

            text.append(Component.literal(TomeHelper.enchantabilityToScore(stack.getEnchantmentValue())+")"));
            tooltipComponents.add(text);
        }
    }

}
