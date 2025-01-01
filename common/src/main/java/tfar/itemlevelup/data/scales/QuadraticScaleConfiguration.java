package tfar.itemlevelup.data.scales;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;

//equation is y = ax^2 +bx + c
public class QuadraticScaleConfiguration extends ScaleConfiguration {

    private final int a;
    private final int b;
    private final int c;

    public QuadraticScaleConfiguration(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    public int c() {
        return c;
    }

    public static final Codec<QuadraticScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(QuadraticScaleConfiguration::a),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("b").forGetter(QuadraticScaleConfiguration::b),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("c").forGetter(QuadraticScaleConfiguration::c)
                    )
                    .apply(group, QuadraticScaleConfiguration::new)
    );
}
