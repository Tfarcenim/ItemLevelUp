package tfar.itemlevelup.datagen;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.ScaleConfiguration;

import java.util.List;
import java.util.function.Consumer;

public class LevelUpProviderBuilder<SC extends ScaleConfiguration, ST extends ScaleType<SC>> {

    private final Item item;
    private final LevelUpInfoBuilder<SC,ST> infoBuilder = new LevelUpInfoBuilder<>();

    public LevelUpProviderBuilder(Item item) {
        this.item = item;
    }

    public static <SC extends ScaleConfiguration, ST extends ScaleType<SC>> LevelUpProviderBuilder<SC,ST> createLevelUp(Item item) {
        return new LevelUpProviderBuilder<>(item);
    }

    public LevelUpProviderBuilder<SC,ST> addActions(Action... actions) {
        infoBuilder.validActions.addAll(List.of(actions));
        return this;
    }

    public LevelUpProviderBuilder<SC,ST> withConfig(ConfiguredScale<SC,ST> configuredScale) {
        infoBuilder.configuredScale = configuredScale;
        return this;
    }

    public void build(Consumer<FinishedLevelUpInfo> consumer,ResourceLocation location) {
        consumer.accept(new Result(location,item,infoBuilder));
    }

    public record Result(ResourceLocation id,Item tool,LevelUpInfoBuilder infoBuilder) implements FinishedLevelUpInfo {

        public void serialize(JsonObject json) {
            json.addProperty("item", BuiltInRegistries.ITEM.getKey(tool).toString());
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
