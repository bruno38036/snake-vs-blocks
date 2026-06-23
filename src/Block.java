package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Block extends FallingObject {
    private int value;              // quantas vezes precisa bater nele
    private final int originalValue; // guarda o valor original (pra contar pontos)

    public Block(double x, double y, int value, double speed) {
        super(x, y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE, speed);
        this.value = value;
        this.originalValue = value;
    }

    public int getValue() {
        return value;
    }

    public int getOriginalValue() {
        return originalValue;
    }

    // Recebe um "hit" (cobra colidiu), diminui a vida
    public void hit() {
        value--;
    }

    // Se value chegou a 0 ou menos, tá destruído, boy
    public boolean isDestroyed() {
        return value <= 0;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cor muda conforme vida: vermelho mais escuro = mais vida
        // Fórmula: quanto mais vida, menos verde tem (tipo um gradiente legal)
        float ratio = Math.min(1f, value / 20f); // normaliza de 0 a 1
        Color blockColor = new Color(220, 70 + (int) (80 * (1 - ratio)), 70);
        g2d.setColor(blockColor);
        g2d.fillRoundRect((int) x, (int) y, width, height, 16, 16); // quadrado com cantos arredondados

        // Borda mais escura pra dar 3D
        g2d.setColor(new Color(120, 25, 25));
        g2d.drawRoundRect((int) x, (int) y, width, height, 16, 16);

        // Desenha o número de vidas no meio do bloco
        String text = String.valueOf(value);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        // Centraliza o texto
        int tx = (int) x + (width - fm.stringWidth(text)) / 2;
        int ty = (int) y + ((height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, tx, ty);
    }
}
