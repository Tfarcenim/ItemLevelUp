package tfar.itemlevelup.data.scales.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

//equation is y = ax + b
public record LinearScaleConfiguration(int a,int b) {


    public static final Codec<LinearScaleConfiguration> CODEC = RecordCodecBuilder.create(group ->
            group.group(
                            ExtraCodecs.POSITIVE_INT.fieldOf("a").forGetter(LinearScaleConfiguration::a),
                            ExtraCodecs.POSITIVE_INT.optionalFieldOf("b",0).forGetter(LinearScaleConfiguration::a)
                    )
                    .apply(group, LinearScaleConfiguration::new)
    );
}
