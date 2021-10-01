package com.mcprog.bindingmod.items;

import com.mcprog.bindingmod.setup.Tabs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Material;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;

public class PackingTapeItem extends ToolItem {

    static final int DURABILITY = 96;

    public PackingTapeItem() {
        super(ToolMaterials.IRON, new FabricItemSettings().group(Tabs.MAIN_TAB).maxDamageIfAbsent(DURABILITY));
    }
}
