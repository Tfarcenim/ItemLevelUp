package tfar.itemlevelup.datagen;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface FinishedLevelUpInfo {
        void serialize(JsonObject json);

        /**
         * Gets the JSON for the recipe.
         */
        default JsonObject getRecipeJson() {
            JsonObject jsonobject = new JsonObject();
            this.serialize(jsonobject);
            return jsonobject;
        }

        /**
         * Gets the ID for the recipe.
         */
        ResourceLocation getID();
    }
