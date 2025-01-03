package tfar.itemlevelup.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.itemlevelup.ItemLevelUp;
import tfar.itemlevelup.client.ClientPacketHandler;
import tfar.itemlevelup.data.scales.ScaleTypes;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, ItemLevelUp.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addTranslatableComponent(ClientPacketHandler.getXPLine(0,0),"XP : %s / %s");
        addTranslatableComponent(ClientPacketHandler.getLevelLine(0,false), "Level : %s");
        addTranslatableComponent(ClientPacketHandler.getLevelLine(0,true), "Level : %s (MAX)");

        addTranslatableComponent(ClientPacketHandler.getXPScalingLine(""), "Level Scaling : %s");

        addTranslatableComponent(ClientPacketHandler.scaling(ScaleTypes.reverseLookup(ScaleTypes.CONSTANT)),"Constant");
        addTranslatableComponent(ClientPacketHandler.scaling(ScaleTypes.reverseLookup(ScaleTypes.LINEAR)),"Linear");
        addTranslatableComponent(ClientPacketHandler.scaling(ScaleTypes.reverseLookup(ScaleTypes.QUADRATIC)),"Quadratic");
    }

    protected void addTranslatableComponent(MutableComponent component, String text) {
        ComponentContents contents = component.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(),text);
        } else {
            throw new UnsupportedOperationException(component +" is not translatable");
        }
    }
}
