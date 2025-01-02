package tfar.itemlevelup.data;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.itemlevelup.ItemLevelUp;

import java.util.UUID;

public record LevelUpReward(Attribute attribute, double increasePerLevel, AttributeModifier.Operation operation) {

    static final UUID uuid = new UUID(ItemLevelUp.MOD_ID.hashCode(),"level_up_reward".hashCode());
    public AttributeModifier createModifier(int level) {
        return new AttributeModifier(uuid,"Level Up Reward",increasePerLevel * level,operation);
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("attribute", BuiltInRegistries.ATTRIBUTE.getKey(attribute).toString());
        object.addProperty("increase_per_level",increasePerLevel);
        object.addProperty("operation",operation.name());
        return object;
    }

    public static LevelUpReward fromPacket(FriendlyByteBuf buf) {
        Attribute attribute = buf.readById(BuiltInRegistries.ATTRIBUTE);
        double increasePerLevel = buf.readDouble();
        AttributeModifier.Operation operation = buf.readEnum(AttributeModifier.Operation.class);
        return new LevelUpReward(attribute,increasePerLevel,operation);
    }

    public void toPacket(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ATTRIBUTE,attribute);
        buf.writeDouble(increasePerLevel);
        buf.writeEnum(operation);
    }

    public static LevelUpReward fromJson(JsonObject object) {
        Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(object.get("attribute").getAsString()));
        double increasePerLevel = object.get("increase_per_level").getAsDouble();
        AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(object.get("operation").getAsString());
        return new LevelUpReward(attribute,increasePerLevel,operation);
    }

}
