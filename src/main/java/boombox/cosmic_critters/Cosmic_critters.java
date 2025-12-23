package boombox.cosmic_critters;

import boombox.cosmic_critters.entity.ModEntities;
import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import boombox.cosmic_critters.item.ModItemGroups;
import boombox.cosmic_critters.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.loading.math.MolangQueries;


public class Cosmic_critters implements ModInitializer {

    public static String MOD_ID = "cosmic_critters";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(ModEntities.PEEPER, PeeperEntity.setAttributes());
        MolangQueries.<PeeperEntity>setActorVariable("query.cc_movespeed", actor -> {return 1.0F;});

        ModItems.registerModItems();
        ModItemGroups.registerModItemGroups();
        ModEntities.registerModEntities();
    }
}
