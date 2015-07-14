package joazlazer.mods.amc.reference;

public class Messages {
    public static class Client {
        public static final int PLAYER_TRACKER_UPDATE_DISC = 2;
        public static final int CAST_DATA_REPLY_DISC = 3;
        public static final int SERVER_EVENT_DISC = 5;
        public static final int CAST_UPDATE = 7;
    }

    public static class Server {
        public static final int PLAYER_TRACKER_UPDATE_DISC = 0;
        public static final int CASTING_CONTROL_DISC = 1;
        public static final int CAST_DATA_REQUEST_DISC = 4;
        public static final int PLAYER_RESPAWN_DISC = 6;
    }
}
