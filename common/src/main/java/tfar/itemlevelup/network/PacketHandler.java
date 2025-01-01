package tfar.itemlevelup.network;

import net.minecraft.resources.ResourceLocation;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.network.client.S2CLevelUpInfoPacket;
import tfar.itemlevelup.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerClientPacket(S2CLevelUpInfoPacket.class, S2CLevelUpInfoPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return ItemLevelUp.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
