package com.doublepi.hopeful.anvil;

import com.doublepi.hopeful.scrolls.ScrollHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnvilScreen extends ItemCombinerScreen<AnvilMenu> {
    private static final ResourceLocation TEXT_FIELD_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation ERROR_SPRITE =
            ResourceLocation.withDefaultNamespace("container/anvil/error");
    private static final ResourceLocation ANVIL_LOCATION =
            ResourceLocation.withDefaultNamespace("textures/gui/container/anvil.png");
    private MultiLineTextWidget enchantStatus;

    public AnvilScreen(AnvilMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, ANVIL_LOCATION);
        this.titleLabelX = 60;
    }

    @Override
    protected void subInit() {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.enchantStatus = new MultiLineTextWidget(i + 62, j + 24, Component.empty(), this.font);
        this.enchantStatus.setMessage(Component.literal("amongus"));
        this.addWidget(this.enchantStatus);
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

    public void renderFg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.enchantStatus.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public void slotChanged(AbstractContainerMenu containerToSend, int slotInd, ItemStack stack) {
        if (!this.menu.getSlot(2).hasItem() || !this.menu.getSlot(0).hasItem()){
            this.enchantStatus.setMessage(Component.empty());
            return;
        }
        int prevStatus = ScrollHelper.getScore(this.menu.getSlot(0).getItem());
        int nextStatus = ScrollHelper.getScore(this.menu.getSlot(2).getItem());
        int maxStatus = ScrollHelper.getMaxScore(this.menu.getSlot(0).getItem());
        this.enchantStatus.setMessage(Component.literal(prevStatus+" -> "+nextStatus+" out of "+maxStatus));

    }

    protected void renderErrorIcon(GuiGraphics guiGraphics, int x, int y) {
        if(!this.menu.getSlot(AnvilMenu.INPUT_SLOT).hasItem())
            return;
        if(this.menu.getSlot(AnvilMenu.RESULT_SLOT).hasItem())
            return;
        guiGraphics.blitSprite(ERROR_SPRITE, x + 99, y + 45, 28, 21);

    }

}
