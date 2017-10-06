package GameObjects;

import Engine.Engine;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by Nick van Endhoven on 11-9-2017.
 */
public class Led extends GameObject {

    private boolean on;

    public Led(Point2D location) {
        super(location);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        float size = 50f;

        if(on)
            g.setPaint(Color.GREEN);
        else
            g.setPaint(Color.RED);

        g.fill(new Ellipse2D.Double((int)(location.getX() - (size /2)), (int)(location.getY() - (size /2)),size,size));
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
