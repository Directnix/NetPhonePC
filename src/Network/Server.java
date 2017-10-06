package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nick van Endhoven on 11-9-2017.
 */

public class Server {

    private ServerSocket server;
    ServerDataReceiveListener listener;

    public Server(ServerDataReceiveListener listener) {
        this.listener = listener;

        try {
            server = new ServerSocket(8080);
            System.out.println(InetAddress.getLocalHost());
            searchForConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchForConnection(){
        new Thread(() -> {
            try {
                while(true) {
                    Socket socket = server.accept();

                    Player p = new Player(socket, listener);
                    listener.onPlayerJoined(p);
                    new Thread(p).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
