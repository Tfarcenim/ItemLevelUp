package tfar.itemlevelup.datagen;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tfar.itemlevelup.data.Action;

import java.util.List;
import java.util.function.Consumer;

public class LevelUpProviderBuilder {

    private final Item item;
    private final LevelUpInfoBuilder infoBuilder = new LevelUpInfoBuilder();

    public LevelUpProviderBuilder(Item item) {
        this.item = item;
    }

    public static LevelUpProviderBuilder createLevelUp(Item item) {
        return new LevelUpProviderBuilder(item);
    }

    public LevelUpProviderBuilder addActions(Action... actions) {
        infoBuilder.validActions.addAll(List.of(actions));
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
