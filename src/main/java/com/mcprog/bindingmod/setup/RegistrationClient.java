package com.mcprog.bindingmod.setup;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.inventory.IntegrationGeneratorScreen;
import com.mcprog.bindingmod.inventory.SimpleScreen;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class RegistrationClient {

    public static void registerScreens() {
        ScreenRegistry.register(Registration.INTEGRATION_GENERATOR_SCREEN_HANDLER, IntegrationGeneratorScreen::new);
    }

    public static void setupBlockRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), Registration.BRIGHT_FUNGUS);
    }

    public static void registerParticles() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(BindingMod.MODID, "particle/fungus_flame"));
        }));
        ParticleFactoryRegistry.getInstance().register(Registration.FUNGUS_FLAME, FlameParticle.Factory::new);
    }
}
