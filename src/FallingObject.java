package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

// Classe abstrata pra qualquer objeto que cai (blocos, comida)
public abstract class FallingObject {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed; // velocidade de queda em pixels por frame

    protected FallingObject(double x, double y, int width, int height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // Atualiza posição (cai mais um pouco)
    public void update() {
        y += speed;
    }

    // Verifica se passou do final da tela
    public boolean reachedBottom() {
        return y > GameConfig.HEIGHT;
    }

    // Retorna hitbox pra colisão
    public Rectangle getBounds() {
        return new Rectangle((int) Math.round(x), (int) Math.round(y), width, height);
    }

    // Getters simples (não tem setter de y pq quem controla é o update)
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    // Cada tipo desenha de um jeito (polimorfismo, basicamente)
    public abstract void draw(Graphics2D g2d);
}
