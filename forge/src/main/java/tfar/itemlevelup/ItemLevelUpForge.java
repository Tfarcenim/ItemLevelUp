package tfar.itemlevelup;

import net.minecraftforge.fml.common.Mod;

@Mod(ItemLevelUp.MOD_ID)
public class ItemLevelUpForge {
    
    public ItemLevelUpForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.

        ItemLevelUp.init();
        
    }
}