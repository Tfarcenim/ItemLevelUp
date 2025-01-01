package tfar.itemlevelup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import tfar.itemlevelup.data.scales.ConfiguredScale;

import java.util.HashSet;
import java.util.Set;

public record LevelUpInfo(Set<Action> validActions, ConfiguredScale<?,?> scale) {

    public static final String VALID_ACTIONS = "valid_actions";
    public static final String SCALE = "scale";

    public static final int MAX = 100;

    public static LevelUpInfo fromJson(JsonObject json) {
        JsonArray actionArray = GsonHelper.getAsJsonArray(json,VALID_ACTIONS);
        Set<Action> valid = new HashSet<>();
        actionArray.forEach(jsonElement -> valid.add(new Action(jsonElement.getAsString())));

        ConfiguredScale<?,?> configuredScale = ConfiguredScale.fromJson(GsonHelper.getAsJsonObject(json,SCALE));

        return new LevelUpInfo(valid,configuredScale);
    }

    public static LevelUpInfo fromPacket(FriendlyByteBuf buf) {
        Set<Action> valid = new HashSet<>(buf.readList(buf1 -> new Action(buf1.readUtf())));
        ConfiguredScale<?,?> config = ConfiguredScale.fromPacket(buf);
        return new LevelUpInfo(valid,config);
    }

    public void toPacket(FriendlyByteBuf buf) {
        buf.writeCollection(validActions,(buf1, action) -> buf.writeUtf(action.name()));
        scale.toPacket(buf);
    }

}
