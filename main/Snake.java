package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake {
    private final List<Segment> segments = new ArrayList<>();
    private int directionX = 0;

    public Snake() {
        reset();
    }

    public void reset() {
        segments.clear();
        for (int i = 0; i < GameConfig.INITIAL_SNAKE_LENGTH; i++) {
            int x = GameConfig.SNAKE_X_START;
            int y = GameConfig.SNAKE_Y_START + i * GameConfig.SNAKE_SEGMENT_SIZE;
            segments.add(new Segment(x, y));
        }
        directionX = 0;
    }

    public void update() {
        if (segments.isEmpty()) {
            return;
        }

        for (int i = segments.size() - 1; i > 0; i--) {
            Segment previous = segments.get(i - 1);
            segments.get(i).setPosition(previous.getX(), previous.getY());
        }

        Segment head = segments.get(0);
        int newX = head.getX() + directionX * GameConfig.SNAKE_SPEED;
        newX = Math.max(0, Math.min(GameConfig.WIDTH - GameConfig.SNAKE_SEGMENT_SIZE, newX));
        head.setPosition(newX, GameConfig.SNAKE_Y_START);
    }

    public void setDirectionX(int directionX) {
        this.directionX = Integer.compare(directionX, 0);
    }

    public void grow() {
        if (segments.isEmpty()) {
            return;
        }
        Segment tail = segments.get(segments.size() - 1);
        segments.add(new Segment(tail.getX(), tail.getY() + GameConfig.SNAKE_SEGMENT_SIZE));
    }

    public void loseSegment() {
        if (!segments.isEmpty()) {
            segments.remove(segments.size() - 1);
        }
    }

    public boolean isDead() {
        return segments.isEmpty();
    }

    public Rectangle getHeadBounds() {
        if (segments.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }
        return segments.get(0).getBounds();
    }

    public int getLength() {
        return segments.size();
    }

    public List<Segment> getSegments() {
        return Collections.unmodifiableList(segments);
    }

    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = segments.size() - 1; i >= 0; i--) {
            Segment segment = segments.get(i);
            if (i == 0) {
                g2d.setColor(new Color(60, 140, 250));
            } else {
                g2d.setColor(new Color(70, 170, 235));
            }
            g2d.fillOval(segment.getX(), segment.getY(), GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
            g2d.setColor(new Color(25, 75, 140));
            g2d.drawOval(segment.getX(), segment.getY(), GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
        }
    }
}
