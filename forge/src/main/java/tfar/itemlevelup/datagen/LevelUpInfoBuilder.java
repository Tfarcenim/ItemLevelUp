package tfar.itemlevelup.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.data.LevelUpReward;
import tfar.itemlevelup.data.scales.ConfiguredScale;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.ScaleConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelUpInfoBuilder<SC extends ScaleConfiguration, ST extends ScaleType<SC>> {
    Set<Action> validActions = new HashSet<>();
    ConfiguredScale<SC,ST> configuredScale;
    int maxLevel = 255;
    List<LevelUpReward> rewards = new ArrayList<>();

    JsonObject toJson() {
        JsonObject object = new JsonObject();

        JsonArray actionArray = new JsonArray();

        validActions.forEach(action -> actionArray.add(action.name()));
        object.add(LevelUpInfo.VALID_ACTIONS,actionArray);

        JsonObject configObj = configuredScale.toJson();

        object.add(LevelUpInfo.SCALE,configObj);
        object.addProperty(LevelUpInfo.MAX_LEVEL,maxLevel);

        JsonArray rewardArray = new JsonArray();
        rewards.forEach(reward -> rewardArray.add(reward.toJson()));

        object.add(LevelUpInfo.REWARDS,rewardArray);
        return object;
    }

}
