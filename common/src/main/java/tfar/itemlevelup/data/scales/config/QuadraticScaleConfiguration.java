package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;

import java.util.Objects;

//equation is y = ax^2 +bx + c
public final class QuadraticScaleConfiguration extends ScaleConfiguration {

    public static final Codec<QuadraticScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(QuadraticScaleConfiguration::a),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("b", 0).forGetter(QuadraticScaleConfiguration::b),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("c", 0).forGetter(QuadraticScaleConfiguration::c)
                    )
                    .apply(group, QuadraticScaleConfiguration::new)
    );
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

    @Override
    public void toPacket(FriendlyByteBuf buf) {
        buf.writeInt(a);
        buf.writeInt(b);
        buf.writeInt(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (QuadraticScaleConfiguration) obj;
        return this.a == that.a &&
                this.b == that.b &&
                this.c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public String toString() {
        return "QuadraticScaleConfiguration[" +
                "a=" + a + ", " +
                "b=" + b + ", " +
                "c=" + c + ']';
    }

}
