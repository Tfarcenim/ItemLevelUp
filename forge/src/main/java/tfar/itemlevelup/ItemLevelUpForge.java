package tfar.itemlevelup;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tfar.itemlevelup.client.ModClientForge;
import tfar.itemlevelup.data.Action;
import tfar.itemlevelup.data.LevelUpInfo;
import tfar.itemlevelup.data.LevelUpManager;
import tfar.itemlevelup.datagen.ModDatagen;
import tfar.itemlevelup.network.client.S2CLevelUpInfoPacket;
import tfar.itemlevelup.platform.Services;

@Mod(ItemLevelUp.MOD_ID)
public class ItemLevelUpForge {
    
    public ItemLevelUpForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        MinecraftForge.EVENT_BUS.addListener(this::reload);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW,this::onBlockBreak);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW,this::onAttack);
        MinecraftForge.EVENT_BUS.addListener(this::onDataSync);
        bus.addListener(ModDatagen::gather);
        if (FMLEnvironment.dist.isClient()) {
            ModClientForge.init(bus);
        }
        ItemLevelUp.init();
    }

    void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        Player player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();
        if (state == null || !ForgeHooks.isCorrectToolForDrops(state, player)) {
            return;
        }
        LevelUpInfo levelUpInfo = ItemLevelUp.manager.getLevelUpProviders().get(stack.getItem());

        if (levelUpInfo != null) {
            if (levelUpInfo.validActions().contains(Action.MINE_BLOCK)) {
                addXp(levelUpInfo,stack,1);
            }
        }
    }

    static void addXp(LevelUpInfo info,ItemStack stack,long xp) {
        int level = PoorMansDataComponents.getOrDefaultI(stack,Constants.LEVEL_KEY);
        if (level < info.maxLevel()) {
            PoorMansDataComponents.incrementLong(stack, Constants.XP_KEY,xp);
            long required = info.scale().compute(level+1);
            long exp =PoorMansDataComponents.getOrDefaultJ(stack,Constants.XP_KEY);
            long leftover = exp - required;
            while (leftover >= 0 && level < info.maxLevel()) {
                if (leftover < required) {
                    stack.getOrCreateTag().putLong(Constants.XP_KEY,leftover);
                }
                leftover = leftover - required;
                required = info.scale().compute(level+1);
                level++;
            }
            stack.getOrCreateTag().putInt(Constants.LEVEL_KEY,level);
        }
    }

    void onDataSync(OnDatapackSyncEvent event) {
        Services.PLATFORM.sendToClient(new S2CLevelUpInfoPacket(ItemLevelUp.manager.getLevelUpProviders()),event.getPlayer());
    }

    void onAttack(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        if (attacker instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            LevelUpInfo levelUpInfo = ItemLevelUp.manager.getLevelUpProviders().get(stack.getItem());

            if (levelUpInfo != null) {
                if (levelUpInfo.validActions().contains(Action.ATTACK)) {
                    addXp(levelUpInfo,stack,1);
                }
            }
        }
    }

    private void reload(AddReloadListenerEvent event) {
        event.addListener(ItemLevelUp.manager = new LevelUpManager());
    }
}