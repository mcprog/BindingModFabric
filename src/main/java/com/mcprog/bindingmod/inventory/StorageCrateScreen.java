package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.BindingMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StorageCrateScreen extends SimpleScreen{

    public StorageCrateScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundWidth = 184;
    }

    @Override
    protected Identifier getId() {
        return new Identifier(BindingMod.MODID, "textures/gui/storage_crate_gui.png");
    }
}
