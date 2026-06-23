package main;

import java.awt.Rectangle;

public class Segment {
    // Cada "pedaço" da cobra tem uma posição
    private int x;
    private int y;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    // Atualiza posição (o resto da cobra segue a cabeça)
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Retorna um "box" invisível pra checar colisão
    public Rectangle getBounds() {
        return new Rectangle(x, y, GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
    }
}
