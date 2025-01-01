package tfar.itemlevelup.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.ScaleType;

import java.util.HashSet;
import java.util.Set;

public class LevelUpInfoBuilder<SC, ST extends ScaleType<SC>> {
    Set<Action> validActions = new HashSet<>();
    ConfiguredScale<SC,ST> configuredScale;

    JsonObject toJson() {
        JsonObject object = new JsonObject();

        JsonArray actionArray = new JsonArray();

        validActions.forEach(action -> actionArray.add(action.name()));
        object.add(LevelUpInfo.VALID_ACTIONS,actionArray);

        JsonObject configObj = configuredScale.toJson();

        object.add(LevelUpInfo.SCALE,configObj);
        return object;
    }

}
