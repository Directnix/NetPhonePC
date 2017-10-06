package GameObjects;

import Engine.Engine;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven on 11-9-2017.
 */
public abstract class GameObject {

    public Point2D location;
    public float scale;
    public float angle;
    public float speed;

    public GameObject(Point2D location) {
        this.location = location;
        Engine.getEngine().addGameObjects(this);
    }

    abstract public void update();
    abstract public void draw(Graphics2D g);
}
