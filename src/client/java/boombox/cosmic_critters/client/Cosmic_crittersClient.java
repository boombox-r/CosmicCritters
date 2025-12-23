package boombox.cosmic_critters.client;

import boombox.cosmic_critters.client.entity.peeper.PeeperRenderer;
import boombox.cosmic_critters.entity.ModEntities;
import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import software.bernie.geckolib.loading.math.MolangQueries;

public class Cosmic_crittersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.PEEPER, PeeperRenderer::new);



    }
}
