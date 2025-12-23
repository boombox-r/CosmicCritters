package boombox.cosmic_critters.item;

import boombox.cosmic_critters.Cosmic_critters;
import boombox.cosmic_critters.entity.ModEntities;
import boombox.cosmic_critters.entity.custom.Peeper.PeeperEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static Item PEEPER_SPAWN_EGG = registerItem(
            "peeper_spawn_egg",
            SpawnEggItem::new,
            new Item.Settings().spawnEgg(ModEntities.PEEPER)
    );

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings){
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Cosmic_critters.MOD_ID, name));
        Item item = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems(){
        Cosmic_critters.LOGGER.info("Registering Mod Items");
    }
}
