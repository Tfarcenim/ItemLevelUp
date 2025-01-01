package tfar.itemlevelup.network.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import tfar.itemlevelup.client.ClientPacketHandler;
import tfar.itemlevelup.data.LevelUpInfo;

import java.util.Map;

public class S2CLevelUpInfoPacket implements S2CModPacket {

    public final Map<Item, LevelUpInfo> levelUpProviders;

    public S2CLevelUpInfoPacket(Map<Item, LevelUpInfo> levelUpProviders) {
        this.levelUpProviders = levelUpProviders;
    }

    public S2CLevelUpInfoPacket(FriendlyByteBuf buf) {
        this(buf.readMap(buf1 -> buf1.readById(BuiltInRegistries.ITEM), LevelUpInfo::fromPacket));
    }

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeMap(levelUpProviders,(buf, item) -> buf.writeId(BuiltInRegistries.ITEM,item),(buf, levelUpInfo) -> levelUpInfo.toPacket(buf));
    }
}
