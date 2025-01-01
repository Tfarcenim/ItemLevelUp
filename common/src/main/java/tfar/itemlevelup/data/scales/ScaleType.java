package tfar.itemlevelup.data.scales;

import com.mojang.serialization.Codec;

public abstract class ScaleType<SC> {

    private final Codec<ConfiguredScale<SC, ScaleType<SC>>> configuredCodec;


    public ScaleType(Codec<SC> pCodec) {
        this.configuredCodec = pCodec.fieldOf("config")
                .xmap(config -> new ConfiguredScale<>(this, config), ConfiguredScale::config).codec();
    }

    public Codec<ConfiguredScale<SC, ScaleType<SC>>> configuredCodec() {
        return configuredCodec;
    }

    public abstract long compute(SC pConfig, long value);


}
