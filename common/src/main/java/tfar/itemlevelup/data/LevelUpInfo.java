package tfar.itemlevelup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.itemlevelup.data.scales.ConfiguredScale;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public record LevelUpInfo(Set<Action> validActions, ConfiguredScale<?,?> scale, int maxLevel,
                          List<LevelUpReward> levelUpRewards) {

    public static final String VALID_ACTIONS = "valid_actions";
    public static final String SCALE = "scale";
    public static final String MAX_LEVEL = "max_level";
    public static final String REWARDS = "rewards";

    public void getModifiers(BiConsumer<Attribute, AttributeModifier> consumer, int level, EquipmentSlot slot) {
        for (LevelUpReward levelUpReward : levelUpRewards) {
            if (levelUpReward.slots().contains(slot)) {
                AttributeModifier modifier = levelUpReward.createModifier(level);
                consumer.accept(levelUpReward.attribute(), modifier);
            }
        }
    }

    public static LevelUpInfo fromJson(JsonObject json) {
        JsonArray actionArray = GsonHelper.getAsJsonArray(json,VALID_ACTIONS);
        Set<Action> valid = new HashSet<>();
        actionArray.forEach(jsonElement -> valid.add(new Action(jsonElement.getAsString())));
        ConfiguredScale<?,?> configuredScale = ConfiguredScale.fromJson(GsonHelper.getAsJsonObject(json,SCALE));
        int maxLevel = json.get(MAX_LEVEL).getAsInt();
        JsonArray rewardArray = GsonHelper.getAsJsonArray(json,REWARDS);

        List<LevelUpReward> rewards = new ArrayList<>();
        for (JsonElement element : rewardArray) {
            rewards.add(LevelUpReward.fromJson((JsonObject) element));
        }

        return new LevelUpInfo(valid,configuredScale,maxLevel,rewards);
    }

    public static LevelUpInfo fromPacket(FriendlyByteBuf buf) {
        Set<Action> valid = new HashSet<>(buf.readList(buf1 -> new Action(buf1.readUtf())));
        ConfiguredScale<?,?> config = ConfiguredScale.fromPacket(buf);
        int maxLevel = buf.readInt();
        List<LevelUpReward> rewards = buf.readList(LevelUpReward::fromPacket);
        return new LevelUpInfo(valid,config,maxLevel,rewards);
    }

    public void toPacket(FriendlyByteBuf buf) {
        buf.writeCollection(validActions,(buf1, action) -> buf.writeUtf(action.name()));
        scale.toPacket(buf);
        buf.writeInt(maxLevel);
        buf.writeCollection(levelUpRewards,(buf1, reward) -> reward.toPacket(buf1));
    }

}
