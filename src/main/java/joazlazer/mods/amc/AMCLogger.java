// Root amc package.
package joazlazer.mods.amc;

// Necessary Imports.
import joazlazer.mods.amc.lib.Reference;
import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.FMLLog;

/**
 * A class designed simply for the east logging of messages through
 * Auric Magick Craft. NOTE: This is only a wrapper class for FMLLog.
 * @author joazlazer
 */
public class AMCLogger {
	
	/**
	 * Log something if we are in debug mode as an info message and as the mod id.
	 * @param str The message to log
	 */
	public static void debugLog(String str) {
		// Simply pass the method call onto the overloaded method.
		debugLog(Level.INFO, str);
	}
	
	/**
	 * Log something if we are in debug mode as the specified level and as AMC.
	 * @param level The level of severity to log as.
	 * @param str The message to log.
	 */
	public static void debugLog(Level level, String str) {
		// Pass the info onto the next overloaded method if in debug mode.
		if(AuricMagickCraft.debugMode) log(level, str, Reference.MOD_ID);
	}
	
	/**
	 * Log something if we are in debug mode as the specified level and as AMC.
	 * @param level The level of severity to log as.
	 * @param str The message to log.
	 * @param denotation The mod id/domain/denotation to log with. See   
	 * joazlazer.mods.amc.AMCLogger.log(Level, String str, String modid)
	 */
	public static void debugLog(Level level, String str, String denotation) {
		// If we are in debugMode then pass the info onto the next overloaded method.
		if (AuricMagickCraft.debugMode) log(level, str, denotation);
	}
	
	/**
	 * A method for logging information with other mod id's (Or denotations)
	 * @param level The level of severity to log as.
	 * @param str The message to log.
	 * @param modid The mod id/ domain/ denotation to log with. IE When logging
	 * orders being registered: "[AMC OrderRegistry] Registered Order _______"
	 */
	public static void log(Level level, String str, String modid) {
		// Log the message with the modid in front.
		FMLLog.log(level, "[" + modid + "] " + str);
	}
	
	public static void log(Level level, String str) {
		// Log the message with the mod id in front.
		FMLLog.log(level, "[" + Reference.MOD_ID + "] " + str); 
			
	}
}
