package tfar.itemlevelup;

import net.minecraft.world.item.ItemStack;

public class PoorMansDataComponents {

    public static void incrementLong(ItemStack stack,String key) {
        long l = stack.getOrCreateTag().getLong(key);
        stack.getTag().putLong(key, l+1);
    }

}
