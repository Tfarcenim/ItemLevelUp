package tfar.itemlevelup.network;

import tfar.itemlevelup.data.scales.config.ConstantScaleConfiguration;
import tfar.itemlevelup.data.scales.config.LinearScaleConfiguration;
import tfar.itemlevelup.data.scales.config.QuadraticScaleConfiguration;

public class NetworkHelpers {

    public static final NetworkHelper<ConstantScaleConfiguration> constant =
            new NetworkHelper<>((buf, config) -> config.toPacket(buf),buf -> new ConstantScaleConfiguration(buf.readInt()));

    public static final NetworkHelper<LinearScaleConfiguration> linear =
            new NetworkHelper<>((buf, config) -> config.toPacket(buf),buf -> new LinearScaleConfiguration(buf.readInt(),buf.readInt()));

    public static final NetworkHelper<QuadraticScaleConfiguration> quadratic =
            new NetworkHelper<>((buf, config) -> config.toPacket(buf),buf -> new QuadraticScaleConfiguration(buf.readInt(),buf.readInt(),buf.readInt()));

}
