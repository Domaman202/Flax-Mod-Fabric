package marioandweegee3.flax;

import marioandweegee3.flax.blocks.FlaxCrop;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;

public class FlaxMod implements ModInitializer {
    public static final Block flaxCrop = new FlaxCrop(FabricBlockSettings.copy(Blocks.WHEAT).build());
    public static final Item flaxSeeds = new AliasedBlockItem(flaxCrop, new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item roastedFlaxSeeds = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.45f).snack().alwaysEdible().build()).group(ItemGroup.FOOD));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("dmn_flax", "flax_seeds"), flaxSeeds);
        Registry.register(Registry.ITEM, new Identifier("dmn_flax", "roasted_flax_seeds"), roastedFlaxSeeds);

        registerCrop("flax", flaxCrop);

        LootTableLoadingCallback.EVENT.register(
                (resourceManager, lootManager, id, supplier, setter) -> {
                    if (isGrass(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder
                                .builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .withCondition(RandomChanceLootCondition.builder(0.125f).build())
                                .withEntry(ItemEntry.builder(flaxSeeds).build());

                        supplier.withPool(poolBuilder.build());
                    }
                }
        );
    }

    public void registerCrop(String name, Block crop) {
        Registry.register(Registry.BLOCK, new Identifier("dmn_flax", name), crop);
    }

    public boolean isGrass(Identifier id) {
        if (id.equals(new Identifier("blocks/grass"))) return true;
        if (id.equals(new Identifier("blocks/fern"))) return true;
        if (id.equals(new Identifier("blocks/tall_grass"))) return true;
        return false;
    }
}