package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class FoodItem extends FallingObject {
    public FoodItem(double x, double y, double speed) {
        super(x, y, GameConfig.FOOD_SIZE, GameConfig.FOOD_SIZE, speed);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Bolinha verde, tipo uma maçã ou algo assim
        g2d.setColor(new Color(70, 190, 95));
        g2d.fillOval((int) x, (int) y, width, height);
        // Borda mais escura pra definir
        g2d.setColor(new Color(30, 110, 55));
        g2d.drawOval((int) x, (int) y, width, height);
    }
}
