package main;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private final GameFrame frame;              // referência pra trocar de tela
    private final Snake snake;                  // o player, basicamente
    private final List<FallingObject> fallingObjects; // blocos e comida caindo
    private final Random random;                // pra gerar números aleatórios
    private final Timer timer;                  // timer que roda o game loop
    private final HighScoreManager highScoreManager; // pra salvar scores

    private int score;                          // pontos atuais
    private int frameCounter;                   // conta quantos frames se passaram
    private double fallSpeed;                   // velocidade de queda (aumenta com dificuldade)
    private boolean savedCurrentScore;          // flag pra não salvar score duas vezes

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        this.snake = new Snake();
        this.fallingObjects = new ArrayList<>();
        this.random = new Random();
        this.highScoreManager = new HighScoreManager();
        // Timer que chama gameLoop a cada 16ms (~60 FPS)
        this.timer = new Timer(GameConfig.TIMER_DELAY_MS, event -> gameLoop());

        setFocusable(true); // recebe input de teclado
        setBackground(new Color(245, 247, 252)); // fundo claro, vibe clean
        addKeyListener(new KeyHandler()); // listeners de tecla
    }

    public void startNewGame() {
        snake.reset();              // cobra volta pro começo
        fallingObjects.clear();     // remove todos os blocos/comida
        score = 0;                  // zera score
        frameCounter = 0;           // zera contador de frames
        fallSpeed = GameConfig.INITIAL_FALL_SPEED; // dificuldade pro mínimo
        savedCurrentScore = false;  // ainda não salvou
        timer.start();              // começa o timer
        requestFocusInWindow();      // foca na janela pra pegar input
    }

    public void stopGame() {
        timer.stop(); // para o timer (pausa o jogo)
    }

    // O CORAÇÃO DO JOGO: game loop que roda a cada frame
    private void gameLoop() {
        snake.update();           // move a cobra
        updateDifficulty();       // aumenta velocidade se necessário
        spawnObjects();           // cria novos blocos e comida
        updateFallingObjects();   // move tudo que cai, remove se chegou no fim
        checkCollisions();        // verifica se cobra pegou algo
        repaint();                // desenha tudo na tela
    }

    // Quanto mais pontos, mais rápido os objetos caem
    private void updateDifficulty() {
        int difficultyLevel = score / GameConfig.DIFFICULTY_STEP_SCORE; // a cada 200 pontos
        fallSpeed = GameConfig.INITIAL_FALL_SPEED + difficultyLevel * GameConfig.SPEED_INCREMENT;
    }

    // Controla quando spawnar blocos e comida
    private void spawnObjects() {
        frameCounter++;
        // Blocos spawnam mais frequentemente conforme a dificuldade aumenta
        // A fórmula diminui o intervalo conforme os pontos aumentam
        if (frameCounter % Math.max(20, GameConfig.BLOCK_SPAWN_INTERVAL_FRAMES - score / 120) == 0) {
            spawnBlockRow();
        }
        // Comida tem intervalo fixo
        if (frameCounter % GameConfig.FOOD_SPAWN_INTERVAL_FRAMES == 0) {
            spawnFood();
        }
    }

    // Cria uma "linha" de blocos (2 a 4 blocos em posições aleatórias)
    private void spawnBlockRow() {
        int columns = GameConfig.WIDTH / GameConfig.BLOCK_SIZE; // quantas colunas cabem?
        int blocksInRow = 2 + random.nextInt(3); // 2, 3 ou 4 blocos
        List<Integer> usedColumns = new ArrayList<>(); // tracking pra não duplicar

        for (int i = 0; i < blocksInRow; i++) {
            int column;
            do {
                column = random.nextInt(columns); // sorteia coluna
            } while (usedColumns.contains(column)); // evita repetir
            usedColumns.add(column);

            int value = 1 + random.nextInt(20); // valor de 1 a 20
            int x = column * GameConfig.BLOCK_SIZE + 4; // posição x
            // Spawna acima da tela pra cair elegantemente
            fallingObjects.add(new Block(x, -GameConfig.BLOCK_SIZE, value, fallSpeed));
        }
    }

    // Spawna uma comida em posição aleatória
    private void spawnFood() {
        int x = random.nextInt(GameConfig.WIDTH - GameConfig.FOOD_SIZE);
        // Comida cai um pouco mais rápido que blocos (legal pra pegar)
        fallingObjects.add(new FoodItem(x, -GameConfig.FOOD_SIZE, fallSpeed + 0.3));
    }

    // Atualiza posição de tudo que tá caindo
    private void updateFallingObjects() {
        Iterator<FallingObject> iterator = fallingObjects.iterator();
        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            object.update(); // move mais pra baixo

            // Se chegou ao final da tela, remove
            if (object.reachedBottom()) {
                // Se era bloco que não foi destruído, cobra perde um segmento
                if (object instanceof Block) {
                    snake.loseSegment();
                    // Verifica se cobra morreu
                    if (snake.isDead()) {
                        endGame();
                    }
                }
                iterator.remove(); // tira da lista
            }
        }
    }

    // Verifica colisão da cabeça da cobra com os objetos
    private void checkCollisions() {
        Rectangle headBounds = snake.getHeadBounds();
        Iterator<FallingObject> iterator = fallingObjects.iterator();

        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            // Se não tá colidindo, pula pra próxima iteração
            if (!headBounds.intersects(object.getBounds())) {
                continue;
            }

            // Se pegou comida, cobra cresce
            if (object instanceof FoodItem) {
                snake.grow();
                iterator.remove();
            }
            // Se colidiu com bloco
            else if (object instanceof Block) {
                // Casting: fala pro Java que esse object é um Block mesmo
                Block block = (Block) object;

                block.hit();              // diminui a vida do bloco
                if (block.isDestroyed()) { // se morreu
                    // Adiciona pontos baseado no valor original
                    score += block.getOriginalValue();
                    iterator.remove(); // remove da lista
                }
            }
        }
    }

    // Quando cobra morre, encerra o jogo
    private void endGame() {
        timer.stop(); // para tudo
        // Salva score só uma vez
        if (!savedCurrentScore) {
            highScoreManager.saveScore(score);
            savedCurrentScore = true;
        }
        frame.showGameOver(score); // muda pra tela de game over
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);              // grid de fundo
        for (FallingObject object : fallingObjects) {
            object.draw(g2d);             // desenha cada bloco/comida
        }
        snake.draw(g2d);                  // desenha a cobra
        drawHud(g2d);                     // desenha score e controles

        g2d.dispose(); // libera recursos gráficos
    }

    // Desenha o fundo com grid
    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(new Color(232, 237, 247));
        // Linhas verticais (grid)
        for (int x = 0; x < GameConfig.WIDTH; x += GameConfig.BLOCK_SIZE) {
            g2d.drawLine(x, 0, x, GameConfig.HEIGHT);
        }

        // Linha separadora entre área de jogo e área da cobra
        g2d.setColor(new Color(210, 218, 235));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(0, GameConfig.SNAKE_Y_START - 12, GameConfig.WIDTH, GameConfig.SNAKE_Y_START - 12);
    }

    // Desenha HUD: score, tamanho da cobra, controles
    private void drawHud(Graphics2D g2d) {
        // Caixa semi-transparente pra mostrar score
        g2d.setColor(new Color(30, 40, 60, 210));
        g2d.fillRoundRect(12, 12, 220, 58, 18, 18);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Score: " + score, 28, 36);
        g2d.drawString("Snake length: " + snake.getLength(), 28, 58);

        // Controles (direita da tela)
        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        String controls = "\u2190 \u2192 steer   P pause   ESC menu";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(new Color(30, 40, 60));
        g2d.drawString(controls, GameConfig.WIDTH - fm.stringWidth(controls) - 12, 30);
    }

    // Listener pra teclado
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                snake.setDirectionX(-1); // esquerda
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.setDirectionX(1);  // direita
            } else if (event.getKeyCode() == KeyEvent.VK_P) {
                // Pausar/resumir
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // Volta ao menu
                stopGame();
                frame.showMenu();
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            // Para a cobra quando solta a tecla de direção
            if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.setDirectionX(0);
            }
        }
    }
}
