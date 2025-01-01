package tfar.itemlevelup.data.scales;

import java.util.HashMap;
import java.util.Map;

public class ScaleTypes {
    public static final Map<String,ScaleType<?>> LOOKUP = new HashMap<>();
    public static final QuadraticScaleType QUADRATIC = add(new QuadraticScaleType(QuadraticScaleConfiguration.CODEC),"quadratic");

    public static <SC extends ScaleConfiguration,ST extends ScaleType<SC>> ST add(ST scaleType,String name) {
        LOOKUP.put(name,scaleType);
        return scaleType;
    }

    public static <SC extends ScaleConfiguration,ST extends ScaleType<SC>> String reverseLookup(ST scaleType) {
        for (Map.Entry<String,ScaleType<?>> entry: LOOKUP.entrySet()) {
            if (entry.getValue().equals(scaleType)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
