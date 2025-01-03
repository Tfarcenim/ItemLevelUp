package tfar.itemlevelup.client;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.network.client.S2CLevelUpInfoPacket;

import java.util.Map;

public class ClientPacketHandler {
    public static Map<Item, LevelUpInfo> MAP;
    public static void handle(S2CLevelUpInfoPacket s2CLevelUpInfoPacket) {
        MAP = s2CLevelUpInfoPacket.levelUpProviders;
    }

    public static MutableComponent getXPLine(long xp, long required) {
        return Component.translatable("text.itemlevelup.xp",xp,required);
    }

    public static MutableComponent getLevelLine(int level,boolean max) {
        return max ? Component.translatable("text.levelup.level.max",level) :  Component.translatable("text.levelup.level",level) ;
    }

    public static MutableComponent getXPScalingLine(String type) {
        return Component.translatable("text.levelup.scaling",scaling(type));
    }

    public static MutableComponent scaling(String type) {
        return Component.translatable("text.levelup.scaling."+type);
    }

}
