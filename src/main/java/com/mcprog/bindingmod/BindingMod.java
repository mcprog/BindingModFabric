package com.mcprog.bindingmod;

import com.mcprog.bindingmod.setup.Registration;
import net.fabricmc.api.ModInitializer;

public class BindingMod implements ModInitializer {

	public static final String MODID = "bindingmod";

	@Override
	public void onInitialize() {
		Registration.registerParticleTypes();

		// Order of these two should not matter since blocks register their own items
		Registration.registerItems();
		Registration.registerBlocks();

		Registration.registerBlockEntities();
		Registration.registerScreenHandlers();

		Registration.registerRecipeTypes();
	}
}
