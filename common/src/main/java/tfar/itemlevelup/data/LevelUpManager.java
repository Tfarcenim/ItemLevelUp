package tfar.itemlevelup.data;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LevelUpManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Item, LevelUpInfo> levelUpProviders = new HashMap<>();
    private boolean someRecipesErrored;

    public static final String FOLDER = "levelup";

    public LevelUpManager() {
        super(GSON, FOLDER);
    }

    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        this.someRecipesErrored = false;

        levelUpProviders.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            try {
                Pair<Item, LevelUpInfo> provider = deserializeProvider(GsonHelper.convertToJsonObject(entry.getValue(), "top element"));
                if (provider == null) {
                    LOGGER.info("Skipping loading provider {} as it is empty", resourcelocation);
                    continue;
                }
                levelUpProviders.put(provider.getFirst(),provider.getSecond());
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading provider {}", resourcelocation, jsonparseexception);
            }
        }

        LOGGER.info("Loaded {} providers", levelUpProviders.size());
    }

    /**
     * Deserializes a conversion object from json data.
     */
    public static Pair<Item, LevelUpInfo> deserializeProvider(JsonObject json) {
        if (json.asMap().isEmpty()) {
            return null;
        }
        Item item = GsonHelper.getAsItem(json,"item");

        LevelUpInfo provider = LevelUpInfo.fromJson(GsonHelper.getAsJsonObject(json,"provider"));

        return Pair.of(item,provider);
    }

    public Map<Item, LevelUpInfo> getLevelUpProviders() {
        return levelUpProviders;
    }
}
