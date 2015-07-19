package joazlazer.mods.amccore;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;

@IFMLLoadingPlugin.Name(value = "AMCCore")
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions(value = "joazlazer.mods.amccore.")
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class AMCCLoadingPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{AMCCCVisitorTransformerWrapper.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return AMCCDummyContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
