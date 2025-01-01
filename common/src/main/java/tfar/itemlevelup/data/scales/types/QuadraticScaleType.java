package tfar.itemlevelup.data.scales.types;

import com.mojang.serialization.Codec;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.QuadraticScaleConfiguration;
import tfar.itemlevelup.network.NetworkHelper;

public class QuadraticScaleType extends ScaleType<QuadraticScaleConfiguration> {

    public QuadraticScaleType(Codec<QuadraticScaleConfiguration> pCodec, NetworkHelper<QuadraticScaleConfiguration> helper) {
        super(pCodec, helper);
    }

    @Override
    public long compute(QuadraticScaleConfiguration pConfig, long value) {
        return pConfig.a() * value * value + pConfig.b() * value + pConfig.c();
    }
}
