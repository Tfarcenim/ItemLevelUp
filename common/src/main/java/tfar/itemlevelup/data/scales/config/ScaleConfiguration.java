package tfar.itemlevelup.data.scales.config;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ScaleConfiguration {


    public ScaleConfiguration() {
    }

    public abstract void toPacket(FriendlyByteBuf buf);

}
