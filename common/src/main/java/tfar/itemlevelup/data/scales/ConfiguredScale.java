package tfar.itemlevelup.data.scales;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import tfar.itemlevelup.ItemLevelUp;

public record ConfiguredScale<SC extends ScaleConfiguration, ST extends ScaleType<SC>>(ST scaleType, SC config)  {

    public JsonObject toJson() {
        JsonObject object = (JsonObject) scaleType.configuredCodec().encodeStart(JsonOps.INSTANCE, (ConfiguredScale<SC, ScaleType<SC>>) this).resultOrPartial(ItemLevelUp.LOG::error).get();
        object.addProperty("type", ScaleTypes.reverseLookup(scaleType));
        return object;
    }

    public static ConfiguredScale<?,?> fromJson(JsonObject object) {
        ScaleType<?> type = ScaleTypes.LOOKUP.get(object.get("type").getAsString());
        ConfiguredScale<?,?> configuredScale = type.configuredCodec().parse(new Dynamic<>(JsonOps.INSTANCE,object)).resultOrPartial(ItemLevelUp.LOG::error).orElseThrow();
        return configuredScale;
    }

}
