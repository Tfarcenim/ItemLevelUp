package tfar.itemlevelup.network.server;

import net.minecraft.server.level.ServerPlayer;
import tfar.itemlevelup.network.ModPacket;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
