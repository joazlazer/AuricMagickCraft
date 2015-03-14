package joazlazer.mods.amc.client.gui;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;
import joazlazer.mods.amc.handlers.ConfigurationHandler;
import joazlazer.mods.amc.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.gui.ForgeGuiFactory;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ModGuiConfig extends GuiConfig {

    public ModGuiConfig(GuiScreen parentScreen) {
        super(parentScreen,
                getGuiElements(),
                Reference.MOD_ID, false, false,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    private static List<IConfigElement> getGuiElements() {
        List<IConfigElement> list = (new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL))).getChildElements();
        list.add(new DummyConfigElement.DummyCategoryElement(StatCollector.translateToLocal("config.ctg.amc:keys.name"), "config.ctg.amc:keys.name", KeysEntry.class));
        return list;
    }

    public static class KeysEntry extends GuiConfigEntries.CategoryEntry
    {
        public KeysEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
        {
            super(owningScreen, owningEntryList, prop);
        }

        @Override
        protected GuiScreen buildChildScreen()
        {
            // This GuiConfig object specifies the configID of the object and as such will force-save when it is closed. The parent
            // GuiConfig object's entryList will also be refreshed to reflect the changes.
            return new GuiConfig(this.owningScreen,
                    (new ConfigElement(ConfigurationHandler.config.getCategory("keys"))).getChildElements(),
                    this.owningScreen.modID, "keys", false,
                    false,
                    GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
        }
    }
}
