package boombox.cosmic_critters.client;

import boombox.cosmic_critters.client.datagen.ModEntityLootTableProvider;
import boombox.cosmic_critters.client.datagen.ModEntityTypeTagProvider;
import boombox.cosmic_critters.client.datagen.ModItemTagProvider;
import boombox.cosmic_critters.client.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class Cosmic_crittersDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModelProvider::new);

    }

}
