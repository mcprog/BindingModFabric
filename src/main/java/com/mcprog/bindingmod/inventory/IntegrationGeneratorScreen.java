package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.BindingMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IntegrationGeneratorScreen extends SimpleScreen{
    public IntegrationGeneratorScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected Identifier getId() {
        return new Identifier(BindingMod.MODID, "textures/gui/integration_generator_gui.png");
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        int i = this.x;
        int j = this.y;
        int l;

        l = ((IntegrationGeneratorScreenHandler)this.handler).getProcessTime();
        this.drawTexture(matrices, i + 79, j + 34, 176, 14, l + 1, 16);
    }
}
