package com.doublepi.hopeful.forge;

import com.doublepi.hopeful.HopefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForgeScreen extends ItemCombinerScreen<ForgeMenu> {
    private static final ResourceLocation TEXT_FIELD_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation ERROR_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/error");
    private static final ResourceLocation ANVIL_LOCATION =
            ResourceLocation.withDefaultNamespace("textures/gui/container/anvil.png");

    public ForgeScreen(ForgeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, ANVIL_LOCATION);
        this.titleLabelX = 60;
    }

    public void resize(Minecraft minecraft, int width, int height) {
        this.init(minecraft, width, height);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        guiGraphics.blitSprite(this.menu.getSlot(0).hasItem() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 59, this.topPos + 20, 110, 16);
    }


    protected void renderErrorIcon(GuiGraphics guiGraphics, int x, int y) {
        if(!this.menu.getSlot(ForgeMenu.INPUT_SLOT).hasItem())
            return;
        if(!this.menu.getSlot(ForgeMenu.TOME_SLOT).hasItem())
            return;
        if(!this.menu.getSlot(ForgeMenu.RESULT_SLOT).hasItem())
            return;
        guiGraphics.blitSprite(ERROR_SPRITE, x + 99, y + 45, 28, 21);

    }

}
