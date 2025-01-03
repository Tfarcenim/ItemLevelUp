package tfar.itemlevelup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.itemlevelup.ItemLevelUp;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record LevelUpReward(Attribute attribute, double increasePerLevel, AttributeModifier.Operation operation,
                            Set<EquipmentSlot> slots) {

    static final UUID uuid = new UUID(ItemLevelUp.MOD_ID.hashCode(),"level_up_reward".hashCode());
    public AttributeModifier createModifier(int level) {
        return new AttributeModifier(uuid,"Level Up Reward",increasePerLevel * level,operation);
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("attribute", BuiltInRegistries.ATTRIBUTE.getKey(attribute).toString());
        object.addProperty("increase_per_level",increasePerLevel);
        object.addProperty("operation",operation.name());
        JsonArray slotArray = new JsonArray();
        slots.forEach(slot -> slotArray.add(slot.name()));
        object.add("slots",slotArray);
        return object;
    }

    public static LevelUpReward fromPacket(FriendlyByteBuf buf) {
        Attribute attribute = buf.readById(BuiltInRegistries.ATTRIBUTE);
        double increasePerLevel = buf.readDouble();
        AttributeModifier.Operation operation = buf.readEnum(AttributeModifier.Operation.class);
        Set<EquipmentSlot> slots = new HashSet<>(buf.readList(buf1 -> buf1.readEnum(EquipmentSlot.class)));
        return new LevelUpReward(attribute,increasePerLevel,operation,slots);
    }

    public void toPacket(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ATTRIBUTE,attribute);
        buf.writeDouble(increasePerLevel);
        buf.writeEnum(operation);
        buf.writeCollection(slots, FriendlyByteBuf::writeEnum);
    }

    public static LevelUpReward fromJson(JsonObject object) {
        Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(object.get("attribute").getAsString()));
        double increasePerLevel = object.get("increase_per_level").getAsDouble();
        AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(object.get("operation").getAsString());
        JsonArray slotArray = object.getAsJsonArray("slots");
        Set<EquipmentSlot> slots = new HashSet<>();
        for (JsonElement element:slotArray) {
            slots.add(EquipmentSlot.valueOf(element.getAsString()));
        }

        return new LevelUpReward(attribute,increasePerLevel,operation,slots);
    }

}
