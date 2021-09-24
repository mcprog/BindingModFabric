package com.mcprog.bindingmod;

import com.mcprog.bindingmod.setup.Registration;
import com.mcprog.bindingmod.setup.RegistrationClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

@Environment(EnvType.CLIENT)
public class BindingModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		RegistrationClient.registerScreens();
		RegistrationClient.setupBlockRenderLayerMap();
		RegistrationClient.registerParticles();
	}
}
