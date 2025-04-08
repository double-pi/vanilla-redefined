package com.doublepi.hopeful.tomes;

import com.doublepi.hopeful.HopefulMod;
import com.doublepi.hopeful.registries.ModDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class TomeHelper {

    public static void enchant(ItemStack item, Tome tome){
        for(Holder<Enchantment> enchantment : tome.enchantments()){
            boolean itemSupportsEnchantment = item.supportsEnchantment(enchantment);
            boolean isNotMaxLevel = item.getEnchantmentLevel(enchantment)< tome.maxLevel();
            if(itemSupportsEnchantment && isNotMaxLevel && getScore(item) < enchantabilityToScore(item.getEnchantmentValue())){
                int newLevel = item.getEnchantmentLevel(enchantment) + 1;
                HopefulMod.LOGGER.error("Item enchanted to lvl "+ newLevel+ " score was changed from "+getScore(item)+
                        " to "+ (getScore(item) + tome.scorePerLevel()));
                item.enchant(enchantment, newLevel);
                setScore(item, getScore(item) + tome.scorePerLevel());
            }
        }
    }

    public static boolean supportsTome(ItemStack item, Tome tome){
        int maxScore = getMaxScore(item);
        int currentScore = getScore(item);
        if(maxScore == 0)
            return false;

        for(Holder<Enchantment> enchantment : tome.enchantments()){
            boolean enchantmentSupported = item.supportsEnchantment(enchantment);
            if(!enchantmentSupported)
                continue;

            if(item.getEnchantmentLevel(enchantment) >= tome.maxLevel())
                continue;

            if(currentScore + tome.scorePerLevel() <= maxScore)
                return true;

        }
        return false;
    }

    public static int enchantabilityToScore(int enchantability){
        if(enchantability==1)
            return 5;
        return enchantability;
    }

    public static int getMaxScore(ItemStack stack){
        int enchantability = stack.getEnchantmentValue();
        return enchantabilityToScore(enchantability);
    }
    public static int getScore(ItemStack stack){
        if(!stack.has(ModDataComponentTypes.ENCHANTABILITY_STATUS)){
            stack.set(ModDataComponentTypes.ENCHANTABILITY_STATUS,0);
        }
        return enchantabilityToScore(stack.get(ModDataComponentTypes.ENCHANTABILITY_STATUS));
    }

    public static void setScore(ItemStack stack, int value){
        stack.set(ModDataComponentTypes.ENCHANTABILITY_STATUS,value);
    }
}
