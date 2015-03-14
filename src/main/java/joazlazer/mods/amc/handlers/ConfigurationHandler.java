package joazlazer.mods.amc.handlers;

import com.ibm.icu.impl.ICUService;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import joazlazer.mods.amc.reference.Reference;
import joazlazer.mods.amc.util.LogHelper;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ConfigurationHandler {

    // ConfigurationHandler object (May not be safe).
    public static net.minecraftforge.common.config.Configuration config;

    // Variables loaded from the configuration.
    public static boolean debugMode = false;

    public static void init(File configFile) {

        if(config == null) {
            // Create the configuration object from the given configuration file.
            config = new net.minecraftforge.common.config.Configuration(configFile);

            // Load the handlers file.
            load();
        }
    }

    public static void load() {

        try {
            // Load the configuration file.
            config.load();

            // Read the properties of the configuration file.
            debugMode = config.get(net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL, "debugMode", false, "Whether or not you want your console to be clogged up with debug information.").getBoolean(false);
            if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                KeyHandler.spellCast.setKeyCode(config.get("keys", "spellCastKey", -99, "The key used for casting spells.").getInt(-99));
                KeyHandler.cancelSpellCastMod.setKeyCode(config.get("keys", "spellCastCancelKey", Keyboard.KEY_LSHIFT, "The key used, in combination with the spell cast key, to cancel spell casting.").getInt(Keyboard.KEY_F));
            }
        }
        catch(Exception ex) {
            // Log the exception.
            LogHelper.error("Error while loading " + Reference.MOD_ID + ".cfg: " + ex);
        }

        if(config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if(event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            // Resync configs.
        }
    }
}
