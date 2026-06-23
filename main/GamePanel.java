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
    private final GameFrame frame;
    private final Snake snake;
    private final List<FallingObject> fallingObjects;
    private final Random random;
    private final Timer timer;
    private final HighScoreManager highScoreManager;

    private int score;
    private int frameCounter;
    private double fallSpeed;
    private boolean savedCurrentScore;

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        this.snake = new Snake();
        this.fallingObjects = new ArrayList<>();
        this.random = new Random();
        this.highScoreManager = new HighScoreManager();
        this.timer = new Timer(GameConfig.TIMER_DELAY_MS, event -> gameLoop());

        setFocusable(true);
        setBackground(new Color(245, 247, 252));
        addKeyListener(new KeyHandler());
    }

    public void startNewGame() {
        snake.reset();
        fallingObjects.clear();
        score = 0;
        frameCounter = 0;
        fallSpeed = GameConfig.INITIAL_FALL_SPEED;
        savedCurrentScore = false;
        timer.start();
        requestFocusInWindow();
    }

    public void stopGame() {
        timer.stop();
    }

    private void gameLoop() {
        snake.update();
        updateDifficulty();
        spawnObjects();
        updateFallingObjects();
        checkCollisions();
        repaint();
    }

    private void updateDifficulty() {
        int difficultyLevel = score / GameConfig.DIFFICULTY_STEP_SCORE;
        fallSpeed = GameConfig.INITIAL_FALL_SPEED + difficultyLevel * GameConfig.SPEED_INCREMENT;
    }

    private void spawnObjects() {
        frameCounter++;
        if (frameCounter % Math.max(20, GameConfig.BLOCK_SPAWN_INTERVAL_FRAMES - score / 120) == 0) {
            spawnBlockRow();
        }
        if (frameCounter % GameConfig.FOOD_SPAWN_INTERVAL_FRAMES == 0) {
            spawnFood();
        }
    }

    private void spawnBlockRow() {
        int columns = GameConfig.WIDTH / GameConfig.BLOCK_SIZE;
        int blocksInRow = 2 + random.nextInt(3);
        List<Integer> usedColumns = new ArrayList<>();

        for (int i = 0; i < blocksInRow; i++) {
            int column;
            do {
                column = random.nextInt(columns);
            } while (usedColumns.contains(column));
            usedColumns.add(column);

            int value = 1 + random.nextInt(20);
            int x = column * GameConfig.BLOCK_SIZE + 4;
            fallingObjects.add(new Block(x, -GameConfig.BLOCK_SIZE, value, fallSpeed));
        }
    }

    private void spawnFood() {
        int x = random.nextInt(GameConfig.WIDTH - GameConfig.FOOD_SIZE);
        fallingObjects.add(new FoodItem(x, -GameConfig.FOOD_SIZE, fallSpeed + 0.3));
    }

    private void updateFallingObjects() {
        Iterator<FallingObject> iterator = fallingObjects.iterator();
        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            object.update();

            if (object.reachedBottom()) {
                if (object instanceof Block) {
                    snake.loseSegment();
                    if (snake.isDead()) {
                        endGame();
                    }
                }
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        Rectangle headBounds = snake.getHeadBounds();
        Iterator<FallingObject> iterator = fallingObjects.iterator();

        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            if (!headBounds.intersects(object.getBounds())) {
                continue;
            }

            if (object instanceof FoodItem) {
                snake.grow();
                iterator.remove();
            } else if (object instanceof Block) {
            // Fazemos o "Casting": explicamos para o Java que esse 'object' genérico na verdade é um 'Block'
            Block block = (Block) object; 
            
            block.hit();
            if (block.isDestroyed()) {
                score += block.getOriginalValue();
                iterator.remove();
            }
}
                }
            }

    private void endGame() {
        timer.stop();
        if (!savedCurrentScore) {
            highScoreManager.saveScore(score);
            savedCurrentScore = true;
        }
        frame.showGameOver(score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);
        for (FallingObject object : fallingObjects) {
            object.draw(g2d);
        }
        snake.draw(g2d);
        drawHud(g2d);

        g2d.dispose();
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(new Color(232, 237, 247));
        for (int x = 0; x < GameConfig.WIDTH; x += GameConfig.BLOCK_SIZE) {
            g2d.drawLine(x, 0, x, GameConfig.HEIGHT);
        }

        g2d.setColor(new Color(210, 218, 235));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(0, GameConfig.SNAKE_Y_START - 12, GameConfig.WIDTH, GameConfig.SNAKE_Y_START - 12);
    }

    private void drawHud(Graphics2D g2d) {
        g2d.setColor(new Color(30, 40, 60, 210));
        g2d.fillRoundRect(12, 12, 220, 58, 18, 18);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Score: " + score, 28, 36);
        g2d.drawString("Snake length: " + snake.getLength(), 28, 58);

        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        String controls = "← → steer   P pause   ESC menu";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(new Color(30, 40, 60));
        g2d.drawString(controls, GameConfig.WIDTH - fm.stringWidth(controls) - 12, 30);
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                snake.setDirectionX(-1);
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.setDirectionX(1);
            } else if (event.getKeyCode() == KeyEvent.VK_P) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                stopGame();
                frame.showMenu();
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.setDirectionX(0);
            }
        }
    }
}
