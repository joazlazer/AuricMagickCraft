// Default root package for all things Auric Magic Craft
package joazlazer.mods.amc;

// Imports for other packages.
import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * A reference class designed to be used to reference options defined in the config file.
 * @author joazlazer
 */
public class ConfigSheet {
	
	// Configurable Objects
	
	
	/**
	 * Loads the configurable options into their objects from the config file.
	 * @param configFile The configuration file reference.
	 */
	public static void load(File configFile) {
		
		// Load the config from the file.
		Configuration config = new Configuration(configFile);
		config.load();
		
		// Log
		AMCLogger.debugLog("Loading advanced configuration file.");
		
		// General
    	AuricMagickCraft.debugMode = config.get(Configuration.CATEGORY_GENERAL, "debugMode", false).getBoolean(false);
		
		// Close the config file & save changes.
		config.save();
	}
}