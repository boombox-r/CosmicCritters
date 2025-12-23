package boombox.cosmic_critters.client.entity.peeper;

import boombox.cosmic_critters.Cosmic_critters;
import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class PeeperModel extends DefaultedEntityGeoModel<PeeperEntity> {
    public PeeperModel() {
        super(Identifier.of(Cosmic_critters.MOD_ID, "peeper"));
        this.headBone = "head";
    }
}
