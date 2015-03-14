package joazlazer.mods.amc.api.spell;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.api.order.OrderRegistry;
import joazlazer.mods.amc.handlers.ConfigurationHandler;
import joazlazer.mods.amc.util.LogHelper;
import java.util.*;

public class SpellRegistry {
    public static HashMap<String, SpellBase> stringSpellMap = new HashMap<String, SpellBase>();

    public static void registerSpell(SpellBase spellToRegister, String modid, String unlocName) {
        // Get the order with the specified unlocalized name and mod id.
        Object order = OrderRegistry.getOrder(modid, unlocName);

        // Print debug text.
        if (ConfigurationHandler.debugMode) {
            LogHelper.info("The Spell API is registering a secific spell, "
                    + spellToRegister.getUnlocName() +
                    " for order " + unlocName +
                    " in " + modid + ".");
        }

        // Create a new array list for storing the new spell list.
        ArrayList<SpellBase> newSpells = new ArrayList<SpellBase>();

        if (((OrderBase) order).getSpells() != null) {
            // Set the new list to have all of the old spells.
            newSpells = ((OrderBase) order).getSpells();
        }

        newSpells.add(spellToRegister);

        // Add the spell to it.
        ((OrderBase) order).setSpells(newSpells);
        if (ConfigurationHandler.debugMode) {
            LogHelper.info("The Spell API has just registered spell " +
                    ((SpellBase) spellToRegister).getUnlocName() + " for " +
                    ((OrderBase) order).getUnlocName() + ".");
        }

        // Add the spell to the general list.
        stringSpellMap.put(spellToRegister.getUnlocName(), spellToRegister);
    }

    public static void registerGenericSpell(SpellBase spellToRegister) {
        // Create a new variable to store the order hashmap.
        HashMap<String, ArrayList<Object>> newList = OrderRegistry.getOrderList();

        // Iterate through all of the different orders.
        for (Iterator<String> iterator = OrderRegistry.getModIdsIterator(); iterator.hasNext(); ) {
            String modid = (String) iterator.next();

            // Create an array list for the different orders registered with that mod id.
            ArrayList<Object> orders = OrderRegistry.getOrderList().get(modid);

            // Iterate through that.
            for (int i = 0; i < orders.size(); i++) {
                // Change the order to have that spell.
                ((OrderBase) newList.get(modid).get(i)).getSpells().add(spellToRegister);
            }
        }

        // Replace the main order list.
        OrderRegistry.setAllOrders(newList);

        if (ConfigurationHandler.debugMode) {
            LogHelper.info("The Spell API has just registered generic spell " +
                    ((SpellBase) spellToRegister).getUnlocName());
        }

        // Add the spell to the general list.
        stringSpellMap.put(spellToRegister.getUnlocName(), spellToRegister);
    }

    public static void initSpecialties() {
        // Create a new variable to store the order hashmap.
        HashMap<String, ArrayList<Object>> newList = OrderRegistry.getOrderList();

        // Iterate through all of the different orders.
        for (Iterator<String> iterator = OrderRegistry.getModIdsIterator(); iterator.hasNext(); ) {
            String modid = (String) iterator.next();

            // Create an array list for the different orders registered with that mod id.
            ArrayList<Object> orders = OrderRegistry.getOrderList().get(modid);

            // Iterate through that.
            for (int i = 0; i < orders.size(); i++) {
                // Initialize the specialties.
                ((OrderBase) orders.get(i)).addSpecialties();
            }
        }

        // Replace the main order list.
        OrderRegistry.setAllOrders(newList);
    }

    public static SpellBase getSpell(String unlocName) {
        // Get wrapper
        return stringSpellMap.get(unlocName);

    }
}
