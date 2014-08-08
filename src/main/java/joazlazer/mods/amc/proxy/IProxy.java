package joazlazer.mods.amc.proxy;

public interface IProxy {
    public void registerCustomRenders();
    public void registerNetworkHandlers();
    public void registerKeyHandlers();
    public void initializeRenderHandler();
}
