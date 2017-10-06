package GameObjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Nick van Endhoven on 26-9-2017.
 */
public class Car extends GameObject3D {

    /**
     * Only accepts a spritesheet of square sprites
     *
     * @param location
     * @param scale
     * @param spritesheet
     */
    public Car(Point2D location, float scale, BufferedImage spritesheet) {
        super(location, scale, spritesheet);
        angle = (float) Math.random();
    }

    @Override
    public void update() {
        location = new Point2D.Double(location.getX() + (Math.cos(angle) * speed), location.getY() + (Math.sin(angle) * speed));
    }
}
