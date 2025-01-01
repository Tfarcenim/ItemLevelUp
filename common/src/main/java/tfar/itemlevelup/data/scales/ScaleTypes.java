package tfar.itemlevelup.data.scales;

import tfar.itemlevelup.data.scales.config.ConstantScaleConfiguration;
import tfar.itemlevelup.data.scales.config.QuadraticScaleConfiguration;
import tfar.itemlevelup.data.scales.types.ConstantScaleType;
import tfar.itemlevelup.data.scales.types.QuadraticScaleType;

import java.util.HashMap;
import java.util.Map;

public class ScaleTypes {
    public static final Map<String,ScaleType<?>> LOOKUP = new HashMap<>();
    public static final ConstantScaleType CONSTANT = add(new ConstantScaleType(ConstantScaleConfiguration.CODEC),"constant");
    public static final ConstantScaleType LINEAR = add(new ConstantScaleType(ConstantScaleConfiguration.CODEC),"linear");
    public static final QuadraticScaleType QUADRATIC = add(new QuadraticScaleType(QuadraticScaleConfiguration.CODEC),"quadratic");

    public static <SC,ST extends ScaleType<SC>> ST add(ST scaleType, String name) {
        LOOKUP.put(name,scaleType);
        return scaleType;
    }

    public static <SC,ST extends ScaleType<SC>> String reverseLookup(ST scaleType) {
        for (Map.Entry<String,ScaleType<?>> entry: LOOKUP.entrySet()) {
            if (entry.getValue().equals(scaleType)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
