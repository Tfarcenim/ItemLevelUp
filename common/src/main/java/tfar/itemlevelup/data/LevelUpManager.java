package tfar.itemlevelup.data;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LevelUpManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Block, Block> conversionMap = new HashMap<>();
    private boolean someRecipesErrored;

    public static final String BLOCK_CONVS = "block_conversions";

    public LevelUpManager() {
        super(GSON,BLOCK_CONVS);
    }

    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        this.someRecipesErrored = false;

        conversionMap.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.

            try {
                Pair<Block,Block> blockPair = deserializeConversion(GsonHelper.convertToJsonObject(entry.getValue(), "top element"));
                if (blockPair == null) {
                    LOGGER.info("Skipping loading conversion {} as it is empty", resourcelocation);
                    continue;
                }
                conversionMap.put(blockPair.getFirst(),blockPair.getSecond());
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading conversion {}", resourcelocation, jsonparseexception);
            }
        }

        LOGGER.info("Loaded {} block conversions", conversionMap.size());
    }

    /**
     * Deserializes a conversion object from json data.
     */
    public static Pair<Block,Block> deserializeConversion(JsonObject json) {
        if (json.isEmpty()) {
            return null;
        }
     //   Block s1 = JSONUtils2.getBlock(json, "from");
     //   Block s2 = JSONUtils2.getBlock(json, "to");
        return Pair.of(s1,s2);
    }

    public Map<Block, Block> getConversionMap() {
        return conversionMap;
    }
}
