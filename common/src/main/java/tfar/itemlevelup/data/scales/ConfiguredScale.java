package tfar.itemlevelup.data.scales;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.data.scales.config.ScaleConfiguration;

public record ConfiguredScale<SC extends ScaleConfiguration, ST extends ScaleType<SC>>(ST scaleType, SC config)  {

    public long compute(int level) {
        return scaleType.compute(config,level);
    }

    public static ConfiguredScale<?, ?> fromPacket(FriendlyByteBuf buf) {
        ScaleType<?> type = ScaleTypes.LOOKUP.get(buf.readUtf());
        ScaleConfiguration config = type.helper().reader().apply(buf);
        return new ConfiguredScale(type,config);
    }

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

    public void toPacket(FriendlyByteBuf buf) {
        buf.writeUtf(ScaleTypes.reverseLookup(scaleType));
        scaleType.helper().writer().accept(buf,config);
    }

}
