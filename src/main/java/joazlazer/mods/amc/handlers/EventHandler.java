package joazlazer.mods.amc.handlers;

import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {

    public static void initHandlers() {

    }

    public static void registerHandlers() {
        registerForgeEvents();
        registerFMLEvents();
    }

    public static void registerFMLEvents() {

    }

    public static void registerForgeEvents() {
        EventBus e = MinecraftForge.EVENT_BUS;

    }

    public static void initEventHandlers() {
        initHandlers();
        registerHandlers();
    }
}
