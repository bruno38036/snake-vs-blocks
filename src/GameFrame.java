package main;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    private final CardLayout cardLayout;
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final HighScoresPanel highScoresPanel;
    private GameOverPanel gameOverPanel;

    public GameFrame() {
        super("SCC0504 - Snake vs. Blocks");
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        highScoresPanel = new HighScoresPanel(this);
        gameOverPanel = new GameOverPanel(this, 0);

        add(menuPanel, GameState.MENU.name());
        add(gamePanel, GameState.PLAYING.name());
        add(highScoresPanel, GameState.HIGH_SCORES.name());
        add(gameOverPanel, GameState.GAME_OVER.name());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        showMenu();
    }

    public void startGame() {
        cardLayout.show(getContentPane(), GameState.PLAYING.name());
        gamePanel.startNewGame();
    }

    public void showMenu() {
        gamePanel.stopGame();
        cardLayout.show(getContentPane(), GameState.MENU.name());
        menuPanel.requestFocusInWindow();
    }

    public void showHighScores() {
        highScoresPanel.refreshScores();
        cardLayout.show(getContentPane(), GameState.HIGH_SCORES.name());
        highScoresPanel.requestFocusInWindow();
    }

    public void showGameOver(int finalScore) {
        remove(gameOverPanel);
        gameOverPanel = new GameOverPanel(this, finalScore);
        add(gameOverPanel, GameState.GAME_OVER.name());
        cardLayout.show(getContentPane(), GameState.GAME_OVER.name());
        gameOverPanel.requestFocusInWindow();
        revalidate();
        repaint();
    }
}
