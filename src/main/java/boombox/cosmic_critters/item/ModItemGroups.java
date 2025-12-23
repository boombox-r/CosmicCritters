package boombox.cosmic_critters.item;

import boombox.cosmic_critters.Cosmic_critters;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(Cosmic_critters.MOD_ID, "mod_item_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.PEEPER_SPAWN_EGG))
                    .displayName(Text.translatable("itemgroup.cosmic_critters.mod_item_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.PEEPER_SPAWN_EGG);
                    })
                    .build());

    public static void registerModItemGroups(){
        Cosmic_critters.LOGGER.info("Registering mod item groups");
    }

}
