package tfar.itemlevelup.data.scales;

import com.mojang.serialization.Codec;

public abstract class ScaleType<SC extends ScaleConfiguration> {

    private final Codec<ConfiguredScale<SC, ScaleType<SC>>> configuredCodec;


    public ScaleType(Codec<SC> pCodec) {
        this.configuredCodec = pCodec.fieldOf("config").xmap((p_65806_) -> {
            return new ConfiguredScale<>(this, p_65806_);
        }, ConfiguredScale::config).codec();
    }

    public abstract long compute(SC pConfig,long value);


}
