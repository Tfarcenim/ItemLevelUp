package tfar.itemlevelup.datagen;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpManager;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.QuadraticScaleConfiguration;
import tfar.itemlevelup.data.scales.QuadraticScaleType;
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
        ConfiguredScale<QuadraticScaleConfiguration,QuadraticScaleType> basicQuadratic = new ConfiguredScale<>(ScaleTypes.QUADRATIC,new QuadraticScaleConfiguration(1,0,0));
        LevelUpProviderBuilder.<QuadraticScaleConfiguration,QuadraticScaleType>createLevelUp(Items.WOODEN_PICKAXE)
                .addActions(Action.MINE_BLOCK)
                .withConfig(basicQuadratic)
                .build(consumer, ItemLevelUp.id("wooden_pickaxe"));
       // LevelUpProviderBuilder.createLevelUp(Items.WOODEN_SWORD).addActions(Action.ATTACK).build(consumer,ItemLevelUp.id("wooden_sword"));
    }
}