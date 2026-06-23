package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class FallingObject {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed;

    protected FallingObject(double x, double y, int width, int height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public boolean reachedBottom() {
        return y > GameConfig.HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) Math.round(x), (int) Math.round(y), width, height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public abstract void draw(Graphics2D g2d);
}
