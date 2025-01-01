package tfar.itemlevelup.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfar.itemlevelup.Constants;
import tfar.itemlevelup.PoorMansDataComponents;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.data.scales.ScaleTypes;

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
            boolean notMax = level < levelUpInfo.maxLevel();
            if (notMax) {
                long nextRequirement = levelUpInfo.scale().compute(level + 1);
                tooltips.add(Component.literal("XP : " + PoorMansDataComponents.getOrDefaultJ(stack, Constants.XP_KEY) +" / "+ nextRequirement));
                if (event.getFlags().isAdvanced()) {
                    tooltips.add(Component.literal("Scaling Type : "+ ScaleTypes.reverseLookup(levelUpInfo.scale().scaleType())).withStyle(ChatFormatting.YELLOW));
                }
            }
            tooltips.add(Component.literal("Level : " + level + (notMax ? "" : " (MAX)")));
        }
    }
}
