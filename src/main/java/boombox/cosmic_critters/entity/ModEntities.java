package boombox.cosmic_critters.entity;

import boombox.cosmic_critters.Cosmic_critters;
import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static void registerModEntities(){
        Cosmic_critters.LOGGER.info("Registering mod entities");
    }

    private static final RegistryKey<EntityType<?>> PEEPER_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Cosmic_critters.MOD_ID, "peeper"));

    public static final EntityType<PeeperEntity> PEEPER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Cosmic_critters.MOD_ID, "peeper"),
            EntityType.Builder.create(PeeperEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.45F, 0.55F).build(PEEPER_KEY)
    );

}
