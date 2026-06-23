package main;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    private final CardLayout cardLayout;           // layout pra trocar telas
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final HighScoresPanel highScoresPanel;
    private GameOverPanel gameOverPanel;           // pode ser recriado

    public GameFrame() {
        super("SCC0504 - Snake vs. Blocks");
        this.cardLayout = new CardLayout();
        setLayout(cardLayout); // usa CardLayout pra trocar painéis

        // Cria todos os painéis
        menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        highScoresPanel = new HighScoresPanel(this);
        gameOverPanel = new GameOverPanel(this, 0);

        // Adiciona cada painel com um "nome" (GameState)
        add(menuPanel, GameState.MENU.name());
        add(gamePanel, GameState.PLAYING.name());
        add(highScoresPanel, GameState.HIGH_SCORES.name());
        add(gameOverPanel, GameState.GAME_OVER.name());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setResizable(false); // não deixa redimensionar
        pack();
        setLocationRelativeTo(null); // centraliza na tela
        showMenu(); // começa no menu
    }

    public void startGame() {
        cardLayout.show(getContentPane(), GameState.PLAYING.name());
        gamePanel.startNewGame();
    }

    public void showMenu() {
        gamePanel.stopGame(); // para o jogo se tava rodando
        cardLayout.show(getContentPane(), GameState.MENU.name());
        menuPanel.requestFocusInWindow();
    }

    public void showHighScores() {
        highScoresPanel.refreshScores(); // atualiza a lista
        cardLayout.show(getContentPane(), GameState.HIGH_SCORES.name());
        highScoresPanel.requestFocusInWindow();
    }

    public void showGameOver(int finalScore) {
        // Remove o GameOverPanel antigo e cria um novo com o score novo
        remove(gameOverPanel);
        gameOverPanel = new GameOverPanel(this, finalScore);
        add(gameOverPanel, GameState.GAME_OVER.name());
        cardLayout.show(getContentPane(), GameState.GAME_OVER.name());
        gameOverPanel.requestFocusInWindow();
        revalidate();
        repaint();
    }
}
