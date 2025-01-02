package tfar.itemlevelup.datagen;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpManager;
import tfar.itemlevelup.data.LevelUpReward;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.config.LinearScaleConfiguration;
import tfar.itemlevelup.data.scales.config.QuadraticScaleConfiguration;
import tfar.itemlevelup.data.scales.types.LinearScaleType;
import tfar.itemlevelup.data.scales.types.QuadraticScaleType;
import tfar.itemlevelup.data.scales.ScaleTypes;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LevelUpProvider implements DataProvider {


    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected final PackOutput.PathProvider recipePathProvider;

    public LevelUpProvider(PackOutput generator) {
        this.recipePathProvider = generator.createPathProvider(PackOutput.Target.DATA_PACK, LevelUpManager.FOLDER);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Set<ResourceLocation> set = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();
        registerInfos((conversion) -> {
            if (!set.add(conversion.getID())) {
                throw new IllegalStateException("Duplicate info " + conversion.getID());
            } else {
                list.add(DataProvider.saveStable(cache, conversion.getRecipeJson(), this.recipePathProvider.json(conversion.getID())));
                saveRecipe(cache, conversion.getRecipeJson(), this.recipePathProvider.json(conversion.getID()));
            }
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    /**
     * Saves a recipe to a file.
     */
    private static void saveRecipe(CachedOutput cache, JsonObject json, Path path) {
        try {
            DataProvider.saveStable(cache, json, path);
        } catch (Exception ioexception) {
            LOGGER.error("Couldn't save info {}", path, ioexception);
        }
    }

    @Override
    public String getName() {
        return "Level Up Infos";
    }

    protected void registerInfos(Consumer<FinishedLevelUpInfo> consumer) {
        simpleTool(consumer,Items.GOLDEN_AXE);
        simpleTool(consumer,Items.GOLDEN_PICKAXE);
        simpleTool(consumer,Items.GOLDEN_SHOVEL);
        simpleSword(consumer,Items.GOLDEN_SWORD);

        simpleTool(consumer,Items.WOODEN_AXE);
        simpleTool(consumer,Items.WOODEN_PICKAXE);
        simpleTool(consumer,Items.WOODEN_SHOVEL);
        simpleSword(consumer,Items.WOODEN_SWORD);

        simpleTool(consumer,Items.STONE_AXE);
        simpleTool(consumer,Items.STONE_PICKAXE);
        simpleTool(consumer,Items.STONE_SHOVEL);
        simpleSword(consumer,Items.STONE_SWORD);

        simpleTool(consumer,Items.IRON_AXE);
        simpleTool(consumer,Items.IRON_PICKAXE);
        simpleTool(consumer,Items.IRON_SHOVEL);
        simpleSword(consumer,Items.IRON_SWORD);

        simpleTool(consumer,Items.DIAMOND_AXE);
        simpleTool(consumer,Items.DIAMOND_PICKAXE);
        simpleTool(consumer,Items.DIAMOND_SHOVEL);
        simpleSword(consumer,Items.DIAMOND_SWORD);

        simpleTool(consumer,Items.NETHERITE_AXE);
        simpleTool(consumer,Items.NETHERITE_PICKAXE);
        simpleTool(consumer,Items.NETHERITE_SHOVEL);
        simpleSword(consumer,Items.NETHERITE_SWORD);
    }

    protected void simpleTool(Consumer<FinishedLevelUpInfo> consumer,Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        ConfiguredScale<LinearScaleConfiguration, LinearScaleType> basicLinear = new ConfiguredScale<>(ScaleTypes.LINEAR,new LinearScaleConfiguration(10,0));

        LevelUpProviderBuilder.<LinearScaleConfiguration, LinearScaleType>createLevelUp(item)
                .addActions(Action.MINE_BLOCK)
                .withConfig(basicLinear)
                .addReward(new LevelUpReward(Attributes.ATTACK_DAMAGE,.25, AttributeModifier.Operation.ADDITION))
                .build(consumer, ItemLevelUp.id(path));
    }

    protected void simpleSword(Consumer<FinishedLevelUpInfo> consumer,Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        ConfiguredScale<LinearScaleConfiguration, LinearScaleType> basicLinear = new ConfiguredScale<>(ScaleTypes.LINEAR,new LinearScaleConfiguration(10,0));

        LevelUpProviderBuilder.<LinearScaleConfiguration, LinearScaleType>createLevelUp(item)
                .addActions(Action.ATTACK)
                .withConfig(basicLinear)
                .addReward(new LevelUpReward(Attributes.ATTACK_DAMAGE,.25, AttributeModifier.Operation.ADDITION))
                .build(consumer, ItemLevelUp.id(path));
    }

}