package joazlazer.mods.amc.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import joazlazer.mods.amc.handlers.ConfigurationHandler;
import joazlazer.mods.amc.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class ModGuiConfig extends GuiConfig {

    public ModGuiConfig(GuiScreen parentScreen) {
        super(parentScreen,
                new ConfigElement(ConfigurationHandler.config.getCategory(net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MOD_ID, false, false,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }
}
