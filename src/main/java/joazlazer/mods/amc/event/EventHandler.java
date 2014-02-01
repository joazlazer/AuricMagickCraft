// Package for events in amc: Two parts: Custom events from the 
// mod & subscribing to existing ones.
package joazlazer.mods.amc.event;

// Necessary Imports
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * The event handler to subscribe to existing events and to initialize handlers.
 * @author joazlazer
 */
public class EventHandler {
	
	// The instance of the event handler to use when calling registerSubs().
	public static EventHandler instance;
	
	// To initialize the class:
	static {
		// Initialize the instance.
		instance = new EventHandler();
	}
	
	/**
	 * Registers the subscribers to existing events and initializes handlers.
	 */
	public void registerSubs() {
		// Get the effective side (What side we are on: client or server)
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			// Register the client subscribers and handlers.
			registerClientSubs();
		}
		else {
			// Register the server subscribers and handlers.
			registerServerSubs();
		}
	}
	
	/**
	 * Registers the subscribers to existing events and initializes handlers on the client side.
	 */
	public void registerClientSubs() {
		
	}
	
	/**
	 * Registers the subscribers to existing events and initializes handlers on the server side.
	 */
	public void registerServerSubs() {
		
	}
}
