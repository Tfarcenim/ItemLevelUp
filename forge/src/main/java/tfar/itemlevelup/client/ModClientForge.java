package tfar.itemlevelup.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.itemlevelup.Constants;
import tfar.itemlevelup.PoorMansDataComponents;
import tfar.itemlevelup.data.LevelUpInfo;

import java.util.List;

public class ModClientForge {
    public static void init(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(ModClientForge::tooltip);
    }

    static void tooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltips = event.getToolTip();

        LevelUpInfo levelUpInfo = ClientPacketHandler.MAP.get(stack.getItem());

        if (levelUpInfo != null) {
            int level =  PoorMansDataComponents.getOrDefaultI(stack,Constants.LEVEL_KEY);
            if (level < LevelUpInfo.MAX) {
                long nextRequirement = levelUpInfo.scale().compute(level + 1);
                tooltips.add(Component.literal("XP : " + PoorMansDataComponents.getOrDefaultJ(stack, Constants.XP_KEY) +" / "+ nextRequirement));
            }
            tooltips.add(Component.literal("Level : " + level));
        }
    }
}
