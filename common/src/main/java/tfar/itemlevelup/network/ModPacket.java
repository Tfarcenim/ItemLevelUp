package tfar.itemlevelup.network;

import net.minecraft.network.FriendlyByteBuf;

public interface ModPacket {

    void write(FriendlyByteBuf to);

}
