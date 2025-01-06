package dev.dooshii;

import dev.dooshii.tracking_compass.TrackingCompassProperty;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RiptideDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RiptideModelProvider::new);
        pack.addProvider(RiptideRecipeProvider::new);
    }
}

class RiptideModelProvider extends FabricModelProvider {
    public RiptideModelProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        List<RangeDispatchItemModel.Entry> list = itemModelGenerator
                .createCompassRangeDispatchEntries(ModItems.TRACKING_COMPASS);
        itemModelGenerator.output
                .accept(
                        ModItems.TRACKING_COMPASS,
                        ItemModels.rangeDispatch(new TrackingCompassProperty(true), 32.0F, list));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }
}

class RiptideRecipeProvider extends FabricRecipeProvider {
    RiptideRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                ShapelessRecipeJsonBuilder.create(Registries.ITEM, RecipeCategory.TOOLS, ModItems.TRACKING_COMPASS) // You can also specify an int to produce more than one
                        .input(Items.COMPASS)
                        .input(Items.ENDER_EYE)
                        .criterion(RecipeGenerator.hasItem(Items.COMPASS), this.conditionsFromItem(Items.COMPASS))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return Riptide.MOD_ID;
    }
}
