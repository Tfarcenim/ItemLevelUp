package tfar.itemlevelup.datagen;

import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ibm.icu.impl.locale.XCldrStub;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpManager;
import tfar.itemlevelup.data.LevelUpReward;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.config.LinearScaleConfiguration;
import tfar.itemlevelup.data.scales.types.LinearScaleType;
import tfar.itemlevelup.data.scales.ScaleTypes;

import java.nio.file.Path;
import java.util.*;
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

        applyDefaultSwords(consumer);
        applyDefaultTools(consumer);
        applyArmorSets(consumer);
    }

    protected void applyDefaultTools(Consumer<FinishedLevelUpInfo> consumer) {
        LevelUpProviderBuilder<LinearScaleConfiguration, LinearScaleType> builder = LevelUpProviderBuilder.create();
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof DiggerItem) {
                builder.addItems(item);
            }
        }
        ConfiguredScale<LinearScaleConfiguration, LinearScaleType> basicLinear = new ConfiguredScale<>(ScaleTypes.LINEAR,new LinearScaleConfiguration(10,0));

        builder.addActions(Action.MINE_BLOCK)
                .withConfig(basicLinear)
                .addReward(new LevelUpReward(Attributes.ATTACK_DAMAGE,.25, AttributeModifier.Operation.ADDITION,Set.of(EquipmentSlot.MAINHAND)));

        builder.build(consumer,ItemLevelUp.id("tools"));
    }

    protected void applyDefaultSwords(Consumer<FinishedLevelUpInfo> consumer) {
        LevelUpProviderBuilder<LinearScaleConfiguration, LinearScaleType> builder = LevelUpProviderBuilder.create();
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SwordItem) {
                builder.addItems(item);
            }
        }
        ConfiguredScale<LinearScaleConfiguration, LinearScaleType> basicLinear = new ConfiguredScale<>(ScaleTypes.LINEAR,new LinearScaleConfiguration(10,0));

        builder.addActions(Action.ATTACK)
                .withConfig(basicLinear)
                .addReward(new LevelUpReward(Attributes.ATTACK_DAMAGE,.25, AttributeModifier.Operation.ADDITION,Set.of(EquipmentSlot.MAINHAND)));

        builder.build(consumer,ItemLevelUp.id("sword"));
    }

    protected void applyArmorSets(Consumer<FinishedLevelUpInfo> consumer) {
        ImmutableMultimap.Builder<EquipmentSlot,ArmorItem> build = ImmutableMultimap.builder();
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof ArmorItem armorItem) {
                build.put(armorItem.getEquipmentSlot(),armorItem);
            }
        }
        ImmutableMultimap<EquipmentSlot,ArmorItem> armorItems = build.build();

        for (EquipmentSlot equipmentSlot : armorItems.keySet()) {
            LevelUpProviderBuilder<LinearScaleConfiguration, LinearScaleType> builder = LevelUpProviderBuilder.create();
            ImmutableCollection<ArmorItem> ai = armorItems.get(equipmentSlot);
            builder.addItems(ai.toArray(ArmorItem[]::new));

            ConfiguredScale<LinearScaleConfiguration, LinearScaleType> basicLinear = new ConfiguredScale<>(ScaleTypes.LINEAR,new LinearScaleConfiguration(100,0));

            builder.addActions(Action.BLOCK_DAMAGE)
                    .withConfig(basicLinear)
                    .addReward(new LevelUpReward(Attributes.ARMOR_TOUGHNESS,.25, AttributeModifier.Operation.ADDITION,Set.of(equipmentSlot)));
            builder.build(consumer,ItemLevelUp.id(equipmentSlot.getName()+ "_armor"));
        }



    }
}