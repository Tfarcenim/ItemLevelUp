package tfar.itemlevelup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;

import java.util.HashSet;
import java.util.Set;

public record LevelUpInfo(Set<Action> validActions) {

    public static final String VALID_ACTIONS = "valid_actions";

    public static LevelUpInfo fromJson(JsonObject json) {
        JsonArray actionArray = GsonHelper.getAsJsonArray(json,VALID_ACTIONS);
        Set<Action> valid = new HashSet<>();
        actionArray.forEach(jsonElement -> valid.add(new Action(jsonElement.getAsString())));
        return new LevelUpInfo(valid);
    }

}
