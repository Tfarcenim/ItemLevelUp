package tfar.itemlevelup.client;

import net.minecraft.world.item.Item;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.network.client.S2CLevelUpInfoPacket;

import java.util.Map;

public class ClientPacketHandler {
    public static Map<Item, LevelUpInfo> MAP;
    public static void handle(S2CLevelUpInfoPacket s2CLevelUpInfoPacket) {
        MAP = s2CLevelUpInfoPacket.levelUpProviders;
    }
}
