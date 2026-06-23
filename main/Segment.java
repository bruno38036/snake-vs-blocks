package main;

import java.awt.Rectangle;

public class Segment {
    private int x;
    private int y;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
    }
}
