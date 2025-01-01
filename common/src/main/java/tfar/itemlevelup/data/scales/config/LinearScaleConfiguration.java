package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;

import java.util.Objects;

//equation is y = ax + b
public final class LinearScaleConfiguration extends ScaleConfiguration {


    public static final Codec<LinearScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(LinearScaleConfiguration::a),
                            ExtraCodecs.POSITIVE_INT.optionalFieldOf("b", 0).forGetter(LinearScaleConfiguration::b)
                    )
                    .apply(group, LinearScaleConfiguration::new)
    );
    private final int a;
    private final int b;

    public LinearScaleConfiguration(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    @Override
    public void toPacket(FriendlyByteBuf buf) {
        buf.writeInt(a);
        buf.writeInt(b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LinearScaleConfiguration) obj;
        return this.a == that.a &&
                this.b == that.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "LinearScaleConfiguration[" +
                "a=" + a + ", " +
                "b=" + b + ']';
    }

}
