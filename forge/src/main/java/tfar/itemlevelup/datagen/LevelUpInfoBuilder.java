package tfar.itemlevelup.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpInfo;

import java.util.HashSet;
import java.util.Set;

public class LevelUpInfoBuilder {
    Set<Action> validActions = new HashSet<>();

    JsonObject toJson() {
        JsonObject object = new JsonObject();

        JsonArray actionArray = new JsonArray();

        validActions.forEach(action -> actionArray.add(action.name()));
        object.add(LevelUpInfo.VALID_ACTIONS,actionArray);
        return object;
    }

}
