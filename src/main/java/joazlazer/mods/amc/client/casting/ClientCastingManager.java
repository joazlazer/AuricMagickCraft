package joazlazer.mods.amc.client.casting;

public class ClientCastingManager {
    private boolean isCasting;
    public void startCasting() {
        isCasting = true;
    }
    public void stopCasting() {
        isCasting = false;
    }
    public boolean isCasting() {
        return isCasting;
    }

    private ClientCastingManager(){}

    private static class SingletonHelper{
        private static final ClientCastingManager INSTANCE = new ClientCastingManager();
    }

    public static ClientCastingManager getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
