package tfar.itemlevelup.network;

import net.minecraft.network.FriendlyByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record NetworkHelper<B>(Writer<B> writer,Reader<B> reader) {


    @FunctionalInterface
    public interface Writer<T> extends BiConsumer<FriendlyByteBuf, T> {

    }

    @FunctionalInterface
    public interface Reader<T> extends Function<FriendlyByteBuf, T> {
    }

}
