package tfar.itemlevelup.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.itemlevelup.Constants;

import java.util.List;

public class ModClientForge {
    public static void init(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(ModClientForge::tooltip);
    }

    static void tooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltips = event.getToolTip();
        if (stack.hasTag() && stack.getTag().contains(Constants.XP_KEY)) {
           tooltips.add(Component.literal("XP :" + stack.getTag().getInt(Constants.XP_KEY)));
        }
    }
}
