package tfar.itemlevelup.data.scales.types;

import com.mojang.serialization.Codec;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.LinearScaleConfiguration;
import tfar.itemlevelup.network.NetworkHelper;

public class LinearScaleType extends ScaleType<LinearScaleConfiguration> {


    public LinearScaleType(Codec<LinearScaleConfiguration> pCodec, NetworkHelper<LinearScaleConfiguration> helper) {
        super(pCodec, helper);
    }

    @Override
    public long compute(LinearScaleConfiguration pConfig, long value) {
        return pConfig.a() * value + pConfig.b();
    }
}
