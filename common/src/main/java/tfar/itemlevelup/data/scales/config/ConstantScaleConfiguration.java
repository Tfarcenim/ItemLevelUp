package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;

import java.util.Objects;

//equation is y = a
public final class ConstantScaleConfiguration extends ScaleConfiguration {

    public static final Codec<ConstantScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(ConstantScaleConfiguration::a)
                    )
                    .apply(group, ConstantScaleConfiguration::new)
    );
    private final int a;

    public ConstantScaleConfiguration(int a) {
        this.a = a;
    }


    public void toPacket(FriendlyByteBuf buf) {
        buf.writeInt(a);
    }

    public int a() {
        return a;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ConstantScaleConfiguration) obj;
        return this.a == that.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }

    @Override
    public String toString() {
        return "ConstantScaleConfiguration[" +
                "a=" + a + ']';
    }

}
