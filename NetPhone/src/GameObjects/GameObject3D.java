package GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Nick van Endhoven on 26-9-2017.
 */
public abstract class GameObject3D extends GameObject {

    BufferedImage spritesheet;
    BufferedImage[] layers;

    private int width;

    /**
     * Only accepts a spritesheet of square sprites
     */
    public GameObject3D(Point2D location, float scale, BufferedImage spritesheet) {
        super(location);
        this.scale = scale;
        this.angle = 0;
        this.spritesheet = spritesheet;

        this.width = spritesheet.getHeight();
        this.layers = new BufferedImage[spritesheet.getWidth() / width];
        for(int i = 0; i < layers.length; i++)
            layers[i] = spritesheet.getSubimage(i * width, 0, width, width);

    }

    @Override
    public abstract void update();

    @Override
    public void draw(Graphics2D g) {
        AffineTransform tx = new AffineTransform();

        tx.translate(location.getX(), location.getY());
        tx.scale(scale, scale);

        for(BufferedImage layer: layers){
            tx.rotate(angle);
            tx.translate(-width/2,-width/2);
            g.drawImage(layer, tx, null);
            tx.translate(width/2,width/2);
            tx.rotate(-angle);
            tx.translate(0,-1);
        }
    }
}
