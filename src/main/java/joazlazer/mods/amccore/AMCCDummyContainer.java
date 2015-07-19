package joazlazer.mods.amccore;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.util.Arrays;

public class AMCCDummyContainer extends DummyModContainer {
    public AMCCDummyContainer() {

        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "AMCCore";
        meta.name = "Auric Magick Craft Core";
        meta.version = "1.7.10-1.0";
        meta.authorList = Arrays.asList("joazlazer");
        meta.credits = "Much thanks to diesieben07 for helping me through ASM and letting me use his awesome API.";
        meta.description = "The core mod providing tweaks for Auric Magick Craft.";
        meta.url = "";
        meta.updateUrl = "";
        meta.screenshots = new String[0];
        meta.logoFile = "";

    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void modConstruction(FMLConstructionEvent evt){

    }

    @Subscribe
    public void init(FMLInitializationEvent evt) {

    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent evt) {

    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent evt) {

    }
}
