package joazlazer.mods.amc.spells;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import joazlazer.mods.amc.AMCLogger;
import joazlazer.mods.amc.orders.OrderBase;
import joazlazer.mods.amc.orders.OrderRegistry;

import org.apache.logging.log4j.Level;

public class SpellRegistry {
	public static void registerSpell(SpellBase spellToRegister, String modid, String unlocName)
	{
		// Get the order with the specified unlocalized name and mod id.
		Object order = OrderRegistry.getOrder(modid, unlocName);
		
		// Create a new array list for storing the new spell list.
		ArrayList<SpellBase> newSpells = new ArrayList<SpellBase>();
		
		if (((OrderBase)order).getSpells() != null)
		{
			// Set the new list to have all of the old spells.
			newSpells = ((OrderBase)order).getSpells();
		}
			
		newSpells.add(spellToRegister);
		
		// Add the spell to it.
		((OrderBase)order).setSpells(newSpells);
		
		AMCLogger.debugLog(Level.INFO, "Registered spell " + 
				((SpellBase)spellToRegister).getUnlocName() + " for " + 
				((OrderBase)order).getUnlocName(), "AMC SpellRegistry");
	}
	public static void registerGenericSpell(SpellBase spellToRegister) {
		// Create a new variable to store the order hashtable.
		Hashtable<String, ArrayList<Object>> newList = OrderRegistry.getOrderList();
		
		// Enumerate through all of the different orders.
		for (Enumeration<String> e = OrderRegistry.getModIdsEnumerator(); e.hasMoreElements();)
		{
			String modid = (String) e.nextElement();
			
			// Create an array list for the different orders registered with that mod id.
			ArrayList<Object> orders = OrderRegistry.getOrderList().get(modid);
			
			// Iterate through that.
			for (int i = 0; i < orders.size(); i++)
			{
				// Change the order to have that spell.
				((OrderBase)newList.get(modid).get(i)).getSpells().add(spellToRegister);
			}
		}
		
		// Replace the main order list.
		OrderRegistry.setAllOrders(newList);
		
		// Debug log.
		AMCLogger.debugLog(Level.INFO, "Registered generic spell " + 
			((SpellBase)spellToRegister).getUnlocName(), "AMC SpellRegistry");
		
	}
	public static void initSpecialties() {
		// Create a new variable to store the order hashtable.
		Hashtable<String, ArrayList<Object>> newList = OrderRegistry.getOrderList();
		
		// Enumerate through all of the different orders.
		for (Enumeration<String> e = OrderRegistry.getModIdsEnumerator(); e.hasMoreElements();)
		{
			String modid = (String) e.nextElement();
			
			// Create an array list for the different orders registered with that mod id.
			ArrayList<Object> orders = OrderRegistry.getOrderList().get(modid);
			
			// Iterate through that.
			for (int i = 0; i < orders.size(); i++)
			{
				// Initialize the specialties.
				((OrderBase)orders.get(i)).addSpecialties();
			}
		}
		
		// Replace the main order list.
		OrderRegistry.setAllOrders(newList);
	}
}
