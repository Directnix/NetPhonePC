package Network;

import GameObjects.GameObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Nick van Endhoven on 25-9-2017.
 */
public class Player implements Runnable {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    boolean running = true;

    ServerDataReceiveListener listener;

    String playerid;
    String playerName = "???";
    int playerIndex = 0;
    GameObject object;

    Player(Socket socket, ServerDataReceiveListener listener){
        this.playerIndex = playerIndex;
        this.socket = socket;
        this.listener = listener;
        this.object = object;

        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.flush();
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        if(socket.isClosed())
            return;
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (running) {
                if(socket.isClosed() || !running)
                    return;

                try {
                    listener.onDataReceived(new Datapacket(in.readUTF()));
                } catch (IOException e) {
                    disconnect();
                }
            }
        }).start();
    }

    public void disconnect() {
        try {
            socket.close();
            running = false;
            listener.onPlayerDisconnect(this);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public GameObject getObject() {
        return object;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
