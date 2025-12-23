package boombox.cosmic_critters.client.entity.peeper;

import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PeeperRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<PeeperEntity, R> {
    public PeeperRenderer(EntityRendererFactory.Context context) {
        super(context, new PeeperModel());
    }
}