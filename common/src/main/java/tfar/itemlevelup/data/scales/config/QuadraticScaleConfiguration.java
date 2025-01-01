package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

//equation is y = ax^2 +bx + c
public record QuadraticScaleConfiguration(int a, int b, int c) {

    public static final Codec<QuadraticScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(QuadraticScaleConfiguration::a),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("b", 0).forGetter(QuadraticScaleConfiguration::b),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("c", 0).forGetter(QuadraticScaleConfiguration::c)
                    )
                    .apply(group, QuadraticScaleConfiguration::new)
    );
}
