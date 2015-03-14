package joazlazer.mods.amc.api.order;

import joazlazer.mods.amc.reference.Reference;

public class ModOrders {
	
	public static class ORDERS
	{
		// The different order variables used to register.
		public static final OrderBase arcana = new OrderArcana();
		public static final OrderBase alchemia = new OrderAlchemia();
	}
	
	// A method for registering all of the mod orders.
	public static void registerOrders()
	{
		// Register all of the orders in AMC.
		OrderRegistry.registerOrder(Reference.MOD_ID, ORDERS.arcana);
		OrderRegistry.registerOrder(Reference.MOD_ID, ORDERS.alchemia);
	}
}
