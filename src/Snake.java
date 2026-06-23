package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake {
    private final List<Segment> segments = new ArrayList<>(); // lista de pedaços
    private int directionX = 0; // -1 = esq, 0 = parado, 1 = dir

    public Snake() {
        reset(); // começa zerado
    }

    public void reset() {
        segments.clear(); // limpa tudo
        // Cria a cobra inicial com 8 pedaços, um embaixo do outro
        for (int i = 0; i < GameConfig.INITIAL_SNAKE_LENGTH; i++) {
            int x = GameConfig.SNAKE_X_START;
            int y = GameConfig.SNAKE_Y_START + i * GameConfig.SNAKE_SEGMENT_SIZE;
            segments.add(new Segment(x, y));
        }
        directionX = 0;
    }

    public void update() {
        // Se tá vazia, não faz nada (cobra morreu)
        if (segments.isEmpty()) {
            return;
        }

        // Movimento da cobra: cada pedaço vai pra posição do pedaço da frente
        // tipo um efeito de "seguindo a cabeça"
        for (int i = segments.size() - 1; i > 0; i--) {
            Segment previous = segments.get(i - 1);
            segments.get(i).setPosition(previous.getX(), previous.getY());
        }

        // A cabeça se move baseado na direção
        Segment head = segments.get(0);
        int newX = head.getX() + directionX * GameConfig.SNAKE_SPEED;
        // Garante que não sai da tela (bate na parede e fica lá)
        newX = Math.max(0, Math.min(GameConfig.WIDTH - GameConfig.SNAKE_SEGMENT_SIZE, newX));
        // Y fica sempre na mesma linha pra pegar os objetos
        head.setPosition(newX, GameConfig.SNAKE_Y_START);
    }

    // Define pra qual lado vai (Integer.compare normaliza pra -1, 0, 1)
    public void setDirectionX(int directionX) {
        this.directionX = Integer.compare(directionX, 0);
    }

    // Cobra cresce quando pega comida (adiciona um pedaço no final)
    public void grow() {
        if (segments.isEmpty()) {
            return;
        }
        Segment tail = segments.get(segments.size() - 1);
        segments.add(new Segment(tail.getX(), tail.getY() + GameConfig.SNAKE_SEGMENT_SIZE));
    }

    // Perde um pedaço quando um bloco passa
    public void loseSegment() {
        if (!segments.isEmpty()) {
            segments.remove(segments.size() - 1);
        }
    }

    // Verifica se a cobra morreu (tá vazia)
    public boolean isDead() {
        return segments.isEmpty();
    }

    // Retorna a hitbox da cabeça pra checar colisão
    public Rectangle getHeadBounds() {
        if (segments.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }
        return segments.get(0).getBounds();
    }

    public int getLength() {
        return segments.size();
    }

    // Retorna cópia da lista (não deixa mexer de fora)
    public List<Segment> getSegments() {
        return Collections.unmodifiableList(segments);
    }

    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Desenha de trás pra frente (cauda primeiro)
        for (int i = segments.size() - 1; i >= 0; i--) {
            Segment segment = segments.get(i);
            // Cabeça é azul mais escuro, resto é azul claro
            if (i == 0) {
                g2d.setColor(new Color(60, 140, 250));  // cabeça, tipo "eu sou o chefe"
            } else {
                g2d.setColor(new Color(70, 170, 235));  // corpo, mais claro
            }
            g2d.fillOval(segment.getX(), segment.getY(), GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
            // Borda escura pra definir
            g2d.setColor(new Color(25, 75, 140));
            g2d.drawOval(segment.getX(), segment.getY(), GameConfig.SNAKE_SEGMENT_SIZE, GameConfig.SNAKE_SEGMENT_SIZE);
        }
    }
}
