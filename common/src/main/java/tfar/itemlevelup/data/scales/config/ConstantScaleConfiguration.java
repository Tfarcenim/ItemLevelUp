package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

//equation is y = a
public record ConstantScaleConfiguration(int a) {


    public static final Codec<ConstantScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(ConstantScaleConfiguration::a)
                    )
                    .apply(group, ConstantScaleConfiguration::new)
    );
}
