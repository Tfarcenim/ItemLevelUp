package tfar.itemlevelup.data.scales;

import com.mojang.serialization.Codec;
import tfar.itemlevelup.data.scales.config.ScaleConfiguration;
import tfar.itemlevelup.network.NetworkHelper;

public abstract class ScaleType<SC extends ScaleConfiguration> {

    private final Codec<ConfiguredScale<SC, ScaleType<SC>>> configuredCodec;
    private final NetworkHelper<SC> helper;


    public ScaleType(Codec<SC> pCodec, NetworkHelper<SC> helper) {
        this.configuredCodec = pCodec.fieldOf("config")
                .xmap(config -> new ConfiguredScale<>(this, config), ConfiguredScale::config).codec();
        this.helper = helper;
    }

    public Codec<ConfiguredScale<SC, ScaleType<SC>>> configuredCodec() {
        return configuredCodec;
    }

    public NetworkHelper<SC> helper() {
        return helper;
    }

    public abstract long compute(SC pConfig, long value);


}
