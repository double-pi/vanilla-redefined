package com.doublepi.hopeful.mixins;

import com.doublepi.hopeful.anvil.AnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnvilBlock.class)
public class AnvilMixin extends Block {

    public AnvilMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) ->
                new AnvilMenu(p_48785_, p_48786_, ContainerLevelAccess.create(level, pos)),
                Component.translatable("container.hopeful.anvil"));
    }
}
