package tfar.itemlevelup;

import net.minecraft.world.item.ItemStack;

public class PoorMansDataComponents {

    public static void incrementLong(ItemStack stack,String key) {
        incrementLong(stack,key,1);
    }

    public static void incrementInt(ItemStack stack,String key) {
        incrementInt(stack,key,1);
    }

    public static void incrementInt(ItemStack stack,String key,int amount) {
        int i = stack.getOrCreateTag().getInt(key);
        stack.getTag().putInt(key, i+amount);
    }

    public static void incrementLong(ItemStack stack,String key,long amount) {
        long l = stack.getOrCreateTag().getLong(key);
        stack.getTag().putLong(key, l+amount);
    }

    public static int getOrDefaultI(ItemStack stack,String key) {
        if (!stack.hasTag()) return 0;
        return stack.getTag().getInt(key);
    }

    public static long getOrDefaultJ(ItemStack stack,String key) {
        if (!stack.hasTag()) return 0;
        return stack.getTag().getLong(key);
    }

}
