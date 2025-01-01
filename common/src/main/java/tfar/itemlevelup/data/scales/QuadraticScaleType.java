package tfar.itemlevelup.data.scales;

import com.mojang.serialization.Codec;

public class QuadraticScaleType extends ScaleType<QuadraticScaleConfiguration>{
    public QuadraticScaleType(Codec<QuadraticScaleConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public long compute(QuadraticScaleConfiguration pConfig, long value) {
        return pConfig.a() * value * value + pConfig.b() * value + pConfig.c();
    }
}
