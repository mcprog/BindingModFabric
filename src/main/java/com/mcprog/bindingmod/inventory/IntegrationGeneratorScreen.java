package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.BindingMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IntegrationGeneratorScreen extends SimpleScreen{
    public IntegrationGeneratorScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected Identifier getId() {
        return new Identifier(BindingMod.MODID, "textures/gui/integration_generator_gui.png");
    }
}
