package com.doublepi.hopeful.items;

import com.doublepi.hopeful.HopefulMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SculkDebug extends Item {
    public static Map<Block, Integer> scoreMap = new HashMap<>();
    public static final int SCULK_RADIUS = 10;
    public SculkDebug(Properties properties) {
        super(properties);
        scoreMap.put(Blocks.SCULK, 1);
        scoreMap.put(Blocks.SCULK_SENSOR, 5);
        scoreMap.put(Blocks.SCULK_SHRIEKER, 10);
        scoreMap.put(Blocks.SCULK_VEIN, 1);
        scoreMap.put(Blocks.SCULK_CATALYST,0);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!level.getBlockState(pos).is(Blocks.SCULK_CATALYST))
            return super.useOn(context);
        context.getPlayer().displayClientMessage(
                Component.literal("Current Sculk storage: "+calcSculkStorage(level, pos)),
                true);
        return InteractionResult.SUCCESS_NO_ITEM_USED;
    }

    public static int calcSculkStorage(Level level, BlockPos source){
        List<BlockPos> family = new ArrayList<>();
        return recursiveSculkCalc(level,source, family,0);

    }

    private static int recursiveSculkCalc(Level level, BlockPos pos, List<BlockPos> family,int depth){
        Block block = level.getBlockState(pos).getBlock();
        HopefulMod.LOGGER.error("Current Block: "+block+ " current pos: "+pos+" current family: "+family);
        int score=0;
        if(!family.contains(pos) && scoreMap.containsKey(block)) {
            family.add(pos);
            score += scoreMap.get(block);
            HopefulMod.LOGGER.error("score changed to "+score);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1 ; j++) {
                    for (int k = -1; k <= 1; k++) {
                        if(depth ==SCULK_RADIUS)
                            return score;
                        Vec3i straightVec = new Vec3i(i,j,k);
                        HopefulMod.LOGGER.error("Direction vec: "+straightVec);
                        score += recursiveSculkCalc(level, pos.offset(straightVec), family, depth+1);
                    }
                }


            }
        }
        return score;
    }
}
