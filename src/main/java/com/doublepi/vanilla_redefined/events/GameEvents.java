package com.doublepi.vanilla_redefined.events;

import com.doublepi.vanilla_redefined.VanillaRedefinedMod;
import com.doublepi.vanilla_redefined.registries.ModGamerules;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

@EventBusSubscriber(modid = VanillaRedefinedMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvents {

    @SubscribeEvent
    public static void saplingReplant(ItemExpireEvent event){
        ItemEntity itemEntity = event.getEntity() ;
        if(!itemEntity.getServer().getGameRules().getBoolean(ModGamerules.SAPLINGS_REPLACE))
            return;
        if(!itemEntity.getItem().is(ItemTags.SAPLINGS))
            return;
        BlockPos pos = event.getEntity().getOnPos();
        Level level = event.getEntity().level();
        if(!level.getBlockState(pos).is(BlockTags.DIRT))
            return;
        if(!level.getBlockState(pos.above()).is(BlockTags.REPLACEABLE))
            return;
        Item saplingItem = itemEntity.getItem().getItem();
        if(saplingItem instanceof BlockItem blockItem) {
            level.setBlockAndUpdate(pos.above(), blockItem.getBlock().defaultBlockState());
        }

    }
}
