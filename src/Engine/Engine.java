package Engine;

import GameObjects.Car;
import GameObjects.GameObject;
import Network.Datapacket;
import Network.Player;
import Network.Server;
import Network.ServerDataReceiveListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Nick van Endhoven on 11-9-2017.
 */
public class Engine extends JPanel implements ActionListener, ServerDataReceiveListener {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1600,900);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(Engine.getEngine());
        frame.setVisible(true);
    }

    ArrayList<GameObject> objects = new ArrayList<>();
    List<Player> players = new ArrayList<>();

    private static Engine instance;

    public static Engine getEngine(){
        if(instance == null)
            instance = new Engine();
        return instance;
    }

    private Engine() {
        new Server(this);
        new Timer(15, this).start();
    }

    public void addGameObjects(GameObject... objects) {
        Collections.addAll(this.objects, objects);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < players.size(); i++)
            g2d.drawString(players.get(i).getPlayerName(), 10, 20 + (20 * i));

        for (GameObject o : objects)
            o.draw(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GameObject o : objects)
            o.update();
        repaint();
    }

    private Player getPlayerFromId(String playerid){
        for(Player p: players)
            if(p.getPlayerid().equals(playerid))
                return p;
        return null;
    }

    @Override
    public void onDataReceived(Datapacket data) {
        if(data.getType().equals("connect")){
            players.get(players.size() - 1).setPlayerid(data.getPlayerid());
            return;
        }

        Player player = getPlayerFromId(data.getPlayerid());
        if(player == null)
            System.err.println("No player with that id found");

        switch (data.getType()) {
            case "username":
                player.setPlayerName(data.getContent());
                player.send("index|1|" + player.getPlayerIndex());
                break;
            case "message":
                System.out.println(player.getPlayerName() + " " + data.getContent());
                break;
            case "steer":
                float amount = ((float)(Integer.parseInt(data.getContent()) - 100) / 2000) * 2;
                player.getObject().angle = player.getObject().angle += (amount/ 1.5);
                break;
            case "speed":
                player.getObject().speed = Float.parseFloat(data.getContent());
                break;
            default:
                System.err.println("Incorrect data type");
        }
    }

    @Override
    public void onPlayerJoined(Player player) {
        if(players.size() == 3) {
            System.out.println("Room is full");
            player.disconnect();
            return;
        }

        if(players.isEmpty()) {
            player.setPlayerIndex(0);
        }else {
            for (int i = 0; i < 4; i++) {
                boolean found = false;
                for (Player p : players) {
                    if(p.getPlayerIndex() == i){
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    player.setPlayerIndex(i);
                    break;
                }
            }
        }

        BufferedImage sprite;
        switch (player.getPlayerIndex()){
            case 0: sprite = imageFromResource("car_blue.png");
                break;
            case 1: sprite = imageFromResource("car_red.png");
                break;
            case 2: sprite = imageFromResource("car_green.png");
                break;
            case 3: sprite = imageFromResource("car_yellow.png");
                break;
            default: sprite = imageFromResource("car_blue.png");
        }

        players.add(player);
        player.setObject(
                new Car(new Point2D.Double(100 + (Math.random() * (getWidth()- 200)),100 + (Math.random() * (getHeight()- 200))), 4, sprite)
        );

    }

    @Override
    public void onPlayerDisconnect(Player player) {
        players.remove(player);
        objects.remove(player.getObject());
    }

    BufferedImage imageFromResource(String img){
        try {
            return ImageIO.read(this.getClass().getResource("/" + img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
