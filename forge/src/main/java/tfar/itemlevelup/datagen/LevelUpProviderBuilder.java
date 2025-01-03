package tfar.itemlevelup.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpReward;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.ScaleConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class LevelUpProviderBuilder<SC extends ScaleConfiguration, ST extends ScaleType<SC>> {

    private final Set<Item> items = new HashSet<>();
    private final LevelUpInfoBuilder<SC,ST> infoBuilder = new LevelUpInfoBuilder<>();

    public LevelUpProviderBuilder(){}

    public static <SC extends ScaleConfiguration, ST extends ScaleType<SC>> LevelUpProviderBuilder<SC,ST> create() {
        return new LevelUpProviderBuilder<>();
    }

    public LevelUpProviderBuilder<SC,ST> addItems(Item... items) {
        Collections.addAll(this.items, items);
        return this;
    }


        public LevelUpProviderBuilder<SC,ST> addActions(Action... actions) {
        infoBuilder.validActions.addAll(List.of(actions));
        return this;
    }

    public LevelUpProviderBuilder<SC,ST> withConfig(ConfiguredScale<SC,ST> configuredScale) {
        infoBuilder.configuredScale = configuredScale;
        return this;
    }

    public LevelUpProviderBuilder<SC,ST> maxLevel(int level) {
        infoBuilder.maxLevel = level;
        return this;
    }

    public LevelUpProviderBuilder<SC,ST> addReward(LevelUpReward reward) {
        infoBuilder.rewards.add(reward);
        return this;
    }


    public void build(Consumer<FinishedLevelUpInfo> consumer,ResourceLocation location) {
        consumer.accept(new Result<>(location, items,infoBuilder));
    }

    public record Result<SC extends ScaleConfiguration, ST extends ScaleType<SC>>(ResourceLocation id, Set<Item> tools, LevelUpInfoBuilder<SC,ST>  infoBuilder) implements FinishedLevelUpInfo {

        public void serialize(JsonObject json) {
            JsonArray itemArray = new JsonArray(tools.size());

            tools.forEach(item -> itemArray.add(BuiltInRegistries.ITEM.getKey(item).toString()));

            json.add("items", itemArray);
            json.add("provider",infoBuilder.toJson());
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getID() {
            return this.id;
        }
    }
}
