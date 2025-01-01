package tfar.itemlevelup.data.scales.types;

import com.mojang.serialization.Codec;
import tfar.itemlevelup.data.scales.ScaleType;
import tfar.itemlevelup.data.scales.config.ConstantScaleConfiguration;
import tfar.itemlevelup.network.NetworkHelper;

public class ConstantScaleType extends ScaleType<ConstantScaleConfiguration> {


    public ConstantScaleType(Codec<ConstantScaleConfiguration> pCodec, NetworkHelper<ConstantScaleConfiguration> helper) {
        super(pCodec, helper);
    }

    @Override
    public long compute(ConstantScaleConfiguration pConfig, long value) {
        return pConfig.a();
    }
}
