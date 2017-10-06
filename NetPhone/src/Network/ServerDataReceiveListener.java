package Network;

/**
 * Created by Nick van Endhoven on 11-9-2017.
 */
public interface ServerDataReceiveListener {
        void onDataReceived(Datapacket data);
        void onPlayerJoined(Player player);
        void onPlayerDisconnect(Player player);
}
