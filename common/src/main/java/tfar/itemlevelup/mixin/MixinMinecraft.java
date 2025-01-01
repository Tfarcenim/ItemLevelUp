package tfar.itemlevelup.mixin;

import tfar.itemlevelup.ItemLevelUp;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        
        ItemLevelUp.LOG.info("This line is printed by an example mod common mixin!");
        ItemLevelUp.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }
}