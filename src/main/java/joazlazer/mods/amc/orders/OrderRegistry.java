package joazlazer.mods.amc.orders;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import joazlazer.mods.amc.AMCLogger;

import org.apache.logging.log4j.Level;

public class OrderRegistry {
	
	/**
	 * The global orders HashTable variable.
	 */
	private static Hashtable<String, ArrayList<Object >> orders;
	
	/**
	 * Initialize the static list. 
	 */
	public static void initOrderMap() {
		
		// Called by the main mod class.
		orders = new Hashtable<String, ArrayList<Object>>();
	}
	
	/**
	 * The main registration method.
	 * @param modid The mod id of the mod to register the order with.
	 * @param orderToPut The order to register.
	 */
	public static void registerOrder(String modid, Object orderToPut) {
		
		// Safely,
		try
		{
			/* Make sure that neither of the order to register or the 
				modid are null. */
			assert orderToPut != null : "registerOrder: order class cannot be null!";
            assert modid != null : "registerOrder: modid cannot be null!";
            assert getOrder(((OrderBase) orderToPut).getUnlocName()) != orderToPut : "Another order already has this unlocalized name!";
            
            // Create a new array list to store the orders.
            ArrayList<Object> newList = new ArrayList<Object>();
            
            /* If the modid already has orders, then pre-fill the array
            list with the already existing mod orders. */
            if (orders.containsKey(modid)) 
            {
               	newList = orders.get(modid);	
            }
            
            // Add the new order to the list.
            newList.add((OrderBase) orderToPut);
            
            /* Replace the new list with the old list or add it for
            	the first time. */
            orders.put(modid, newList);
            
            // Print to the console.
            AMCLogger.log(Level.INFO, "Registered order " + 
            ((OrderBase)orderToPut).getUnlocName(), "AMC OrderRegistry");
		}
		// Catch any error.
		catch ( Exception ex)
		{
			// Log in the AMC error log (Mostly for debug purposes)
			AMCLogger.log(Level.ERROR, "Caught an exception during an AMC" +
					" or addon order registration");
						
			// Throw a new exception.
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * The method used to return the order class with the unlocalized name 
	 * of the parameter.
	 * @param par1Unloc The unlocalized name of the order who's class is returned.
	 * @return The class of the order who has the given unlocalized name.
	 */
	public static Class<? extends OrderBase> getOrderClass(String par1Unloc)	{
		
		// Return the class of the order with the given unlocalized name.
		return getOrder(par1Unloc).getClass();
	}
	
	/**
	 * A method that returns an enumerator for the mod ids.
	 * @return The enumeration of the modids.
	 */
	public static Enumeration<String> getModIdsEnumerator()
	{
		// Return the enumeration for the keys (modids).
		return orders.keys();
	}
	
	/**
	 * A method that returns an enumerator for the order classes.
	 * @return The enumeration of the different lists of orders.
	 */
	public static Enumeration<ArrayList<Object>> getOrdersEnumerator()
	{
		// Return the enumeration for the elements (Lists of order classes).
		return orders.elements();
	}
	
	/**
	 * A method to give you the list of orders associated with a given mod/modid.
	 * @param modid The modid in which to get the order list of.
	 * @return The order list of the modid specified.
	 */
	public static ArrayList<Object> getOrderList(String modid)
	{
		// Return the list form the modid key.
		return orders.get(modid);
	}
	
	/**
	 * A method to return the order with the given unlocalized name.
	 * @param unlocName The unlocalized name of the order to get.
	 * @return The gotten order from the unlocalized name.
	 */
	public static OrderBase getOrder(String unlocName)
	{
		// Create a OrderBase variable that will eventually be returned.
		OrderBase end = null;
		
		// Enumerate through the different mod ids with at least one order.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Iterate through the objects.
			for (Object obj : orders.get(e.nextElement()))
			{
				// If the current order/s unlocalizedName
				// is equal to the one wanted, return it.
				if (((OrderBase) obj).getUnlocName() == unlocName)
				{
					// Set it to the ending variable.
					end = (OrderBase) obj;
				}
			}
		}
		
		// Return the ending variable.
		return end;
	}
	
	/**
	 * A method for returning the order with the modid and unlocalized name.
	 * @param modid The modid of the order wanted.
	 * @param unlocName The unlocalized name of the order wanted.
	 * @return The order gotten from the table that has the specified unlocalized name 
	 * and is registered under the mod that has that mod id.
	 */
	public static OrderBase getOrder(String modid, String unlocName)
	{
		// Make sure the modid actually has orders associated with it.
		// If not, return null.
		if (!orders.containsKey(modid))
		{
			return null;
		}
		
		// Iterate through the orders that are in the given mod.
		for (Object obj : getOrderList(modid))
		{
			// If the current object is the correct one, return it.
			if (((OrderBase) obj).getUnlocName() == unlocName) return (OrderBase) obj;
		}
		
		// If we haven't returned already, return null.
		return null;
	}
	
	/**
	 * A method for returning the order from the mod and the index of
	 * the order in the list of orders associated with that mod/modid.
	 * @param modid The mod id of the order to be gotten.
	 * @param index The index of the wanted order in the list of the mod.
	 * @return The order gotten from the modid and index.
	 */
	public static OrderBase getOrder(String modid, int index)
	{
		// Make sure the modid actually has orders associated with it.
		// If not, return null.
		if (!orders.containsKey(modid)) return null;
		
		// Make a counter variable.
		int i = 0;
		
		// Iterate through the orders that are in the given mod.
		for (@SuppressWarnings("unused") Object obj : getOrderList(modid))
		{
			// Increment the counter variable to determine how many
			// Orders there are in that list.
			i++;
		}
		
		// Make sure the modid actually has orders with indexes equal to
		// or higher than the parameter. If not, return null.
		if (i > index) return null;
		
		// Return the order associated order with the correct modid and
		// index in the list.
		return (OrderBase) getOrderList(modid).get(index);
	}
	
	/**
	 * A method that removes an order from the list identified with the modid and
	 * the order instance object itself.
	 * @param modid The mod id of the order to be removed.
	 * @param order The order instance to be removed.
	 * @return The successfulness of the procedure.
	 */
	public static boolean removeOrder(String modid, OrderBase order)
	{
		// Safely,
		try
		{
			// Make sure the mod's list of orders actually contains the
			// order to be deleted.
			assert !orders.get(modid).contains(order) : "AMC or one of its addons has tried to d" +
					"elete an order that doesn't exist!";
			
			// Create a new list variable.
			ArrayList<Object> newList = orders.get(modid);
			
			// Remove it from the new list.
			newList.remove(order);
			
			// Replace the old list with the new list in the orders hashtable.
			orders.remove(modid);
			orders.put(modid, newList);
			
			// If the list's size is 0, then delete it.
			if (orders.get(modid).size() == 0) orders.remove(modid);
		}
		// If an error occurred,
		catch(Exception ex)
		{
			// Log in the AMC error log (Mostly for debug purposes)
			AMCLogger.log(Level.ERROR, "Caught an exception during an AMC" +
				" or addon order deleting!");
			
			// Return false.
			return false;
		}
		
		// If no errors were detected, return true and a successful delete.
		return true;
	}
	
	/**
	 * A method for removing an order using its class and a modid. If you can help it, 
	 * try not to use this, as it can cause many errors.
	 * @param modid The mod id of the order to be removed.
	 * @param order The class of the order to be removed.
	 * @return The successfulness of the procedure.
	 */
	public static boolean removeOrder(String modid, Class<? extends OrderBase> order)
	{
		// Safely,
		try {
			// Create a new instance of the class given.
			OrderBase newOrder = order.newInstance();
			
			// Remove the order.
			return removeOrder(modid, newOrder);
		} catch (InstantiationException e) {
			
			// Throw an error.
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			
			// Throw an error.
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * A method for removing an order from the specified mod's list of
	 * registered orders using the unlocalized name of the order to be
	 * removed.
	 * @param modid The mod id of the order to be removed.
	 * @param unlocName The unlocalized name of the order to be removed.
	 * @return The successfulness of the procedure.
	 */
	public static boolean removeOrder(String modid, String unlocName)	{
		
		// Safely,
		try	{
		
			// Get the order for the modid and unlocalized name.
			OrderBase order = getOrder(modid, unlocName);
			
			// Remove the order using the other method and return its result.
			return removeOrder(modid, order);
		}
		catch (Exception ex)
		{
			// Log in the AMC error log (Mostly for debug purposes)
			AMCLogger.log(Level.ERROR, "Caught an exception during an AMC" +
				" or addon order deleting!");
			
			// Return false.
			return false;
		}
	}
	
	/**
	 * A method for getting a list of ALL methods that are registered.
	 * @return
	 */
	public static ArrayList<Object> getOrders()
	{
		// Create a new ending variable.
		ArrayList<Object> end = new ArrayList<Object>();
		
		// Enumerate through all of the mods.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Create a new String to store the modid.
			String modid = (String) e.nextElement();
			
			// Iterate through the orders.
			for (Object obj : orders.get(modid))
			{
				// Add the current order to the list of all orders.
				end.add(obj);
			}
		}
		
		// At the end, return the ending variable.
		return end;
	}
	
	/**
	 * A method that changes all orders in the registered orders hashtable that
	 * have the unlocalized name specified by the parameter to the given new order.
	 * @param unlocName The unlocalized name of the order to change.
	 * @param newOrder The new order to replace the old one with.
	 */
	public static void setOrder(String unlocName, Object newOrder)
	{
		// Enumerate through all of the mods.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Create a new String to store the modid.
			String modidCurrent = (String) e.nextElement();
			
			// Iterate through the orders.
			for (int i = 0; i < orders.get(modidCurrent).size(); i++)
			{
				// If the current order's unlocalized name is the same as
				// the one being set, then set it.
				if (((OrderBase)orders.get(modidCurrent).get(i)).getUnlocName() == unlocName)
				{
					// Create a new variable for the order list.
					ArrayList<Object> var = orders.get(modidCurrent);
					
					// Change the old one to the new one.
					var.set(i, newOrder);
					
					// Replace the orders list for the current modid to the modified list.
					orders.remove(modidCurrent);
					orders.put(modidCurrent, var);
				}
			}
		}
	}
	
	/**
	 * A method to set the order in a list from the index of the place to put
	 * the order inside of the mod's list of registered orders.
	 * @param modid The modid of the list to look in and the place to set the new order.
	 * @param index The index of the place to replace the new order in.
	 * @param newOrder The value of the replacement.
	 */
	public static void setOrder(String modid, int index, Object newOrder)
	{
		// Safely,
		try
		{
			// Create a new ArrayList<Object> variable and set it to the list of orders
			// in the hashtable.
			ArrayList<Object> var = orders.get(modid);
			
			// Replace the old order with the new one.
			var.set(index, newOrder);
			
			// Replace the modified list with the old one.
			orders.remove(modid);
			orders.put(modid, var);
		}
		// If an error occurs,
		catch (Exception ex)
		{
			// Log in the AMC error log (Mostly for debug purposes)
			AMCLogger.log(Level.ERROR, "Caught an exception during an AMC" +
				" or addon order setting!");
		}
	}
	
	/**
	 * A method to replace a mod's entire order list.
	 * @param modid The mod id of the list to replace.
	 * @param newList The new list to replace the old one.
	 */
	public static void setOrderList(String modid, ArrayList<Object> newList)
	{
		// Replace the old list with the new one.
		orders.remove(modid);
		orders.put(modid, newList);
	}
	
	/**
	 * A method to get the whole orders variable.
	 * @return The global variable 'orders'.
	 */
	public static Hashtable<String, ArrayList<Object>> getOrderList()
	{
		// Return the main orders hashtable.
		return orders;
	}
	
	/**
	 * A method to set everything in the orders variable. Note: This could crash
	 * AMC and its addons. Use carefully. For addons makers: make sure this won't affect AMC
	 * before using.
	 * @param newOrders The orders hashtable to replace it with.
	 */
	public static void setAllOrders(Hashtable<String, ArrayList<Object>> newOrders)
	{
		// Replace the main order hashtable.
		orders = newOrders;
	}
	
	/**
	 * A method for determining if the collection contains a specified order.
	 * @param obj The object to search for.
	 * @return If it was found.
	 */
	public static boolean containsOrder(Object obj)
	{
		// Enumerate through the different mod ids with at least one order.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Create a new String to store the modid.
			String modid = (String) e.nextElement();
			
			// If the current modid's list contains it, return true.
			if (orders.get(modid).contains(obj)) return true;
		}
		
		// If no modid's list contained it than nothing contains it. Return false.
		return false;
	}
	
	/**
	 * A method for determining if the collection contains an order with the 
	 * specified unlocalized name.
	 * @param str The unlocalized name of the order seared for.
	 * @return If it was found.
	 */
	public static boolean containsUnlocName(String unlocName)
	{
		// If the getOrder from the unlocalized name returns null, then return false.
		if (getOrder(unlocName) == null) return false;
		// If there is one order with that unlocalized name,, return true.
		else return true;
	}
	
	/**
	 * A method for determining if the modid has any registered orders.
	 * @param modid The modid to search for.
	 * @return If it was found.
	 */
	public static boolean containsModId(String modid)
	{
		// Return if the hashtable contains a key with that value.
		return orders.containsKey(modid);
	}
	
	/**
	 * A method for getting the modid of a specified order if its in the list.
	 * If not, this method returns blank ("").
	 * @param order The order to look for.
	 * @return The modid that the order is in, if any.
	 */
	public static String getModId(Object order)
	{
		// Iterate through all of the ids.
		// Enumerate through all of the mods.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Create a new String to store the modid.
			String modid = (String) e.nextElement();
			
			// If the current modid's list contains the object in question, return the modid.
			if (orders.get(modid).contains(order)) return modid;
		}
		
		// If we haven't returned anything at all, return blank;
		return "";
	}
	
	/**
	 * A method for finding out all of the different mods that have orders registered with AMC.
	 * @return The list of different mod ids.
	 */
	public static ArrayList<String> getModIds()
	{
		// Create an ending variable.
		ArrayList<String> end = new ArrayList<String>();
		
		// Enumerate through all of the mods.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Create a new String to store the modid.
			String modid = (String) e.nextElement();
					
			// Add the current modid to the list.
			end.add(modid);
		}
		
		// Return the ending variable.
		return end;
	}
	
	/**
	 * @param id The id of the order to get.
	 * @return The gotten order.
	 */
	public static OrderBase getOrder(int id) {
		// Create a OrderBase variable that will eventually be returned.
		OrderBase end = null;
		
		// Enumerate through the different mod ids with at least one order.
		for (Enumeration<String> e = getModIdsEnumerator(); e.hasMoreElements();)
		{
			// Iterate through the objects.
			for (Object obj : orders.get(e.nextElement()))
			{
				// If the current order/s id
				// is equal to the one wanted, return it.
				if (((OrderBase) obj).getId() == id)
				{
					// Set it to the ending variable.
					end = (OrderBase) obj;
				}
			}
		}
		
		// Return the ending variable.
		return end;
	}
}
