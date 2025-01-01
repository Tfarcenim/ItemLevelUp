package tfar.itemlevelup.data.scales.types;

import com.mojang.serialization.Codec;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.LinearScaleConfiguration;

public class LinearScaleType extends ScaleType<LinearScaleConfiguration> {
    public LinearScaleType(Codec<LinearScaleConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public long compute(LinearScaleConfiguration pConfig, long value) {
        return pConfig.a() * value + pConfig.b();
    }
}
