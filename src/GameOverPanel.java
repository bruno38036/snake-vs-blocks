package main;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class GameOverPanel extends JPanel {
    public GameOverPanel(GameFrame frame, int finalScore) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252));

        // Título grande "Game Over"
        JLabel title = new JLabel("Game Over", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 46));
        title.setForeground(new Color(190, 55, 55)); // vermelho pra dar drama
        title.setBorder(BorderFactory.createEmptyBorder(120, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        // Painel central com score final e botões
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Score final em destaque
        JLabel score = new JLabel("Final score: " + finalScore, SwingConstants.CENTER);
        score.setAlignmentX(CENTER_ALIGNMENT);
        score.setFont(new Font("Arial", Font.BOLD, 26));
        score.setForeground(new Color(30, 40, 65));
        score.setPreferredSize(new Dimension(GameConfig.WIDTH, 48));
        score.setMaximumSize(new Dimension(GameConfig.WIDTH, 48));

        // Botões
        JButton restart = createButton("Restart");
        JButton highScores = createButton("High Scores");
        JButton exit = createButton("Exit");

        // Callbacks
        restart.addActionListener(event -> frame.startGame());
        highScores.addActionListener(event -> frame.showHighScores());
        exit.addActionListener(event -> System.exit(0));

        // Monta o layout
        center.add(score);
        center.add(Box.createRigidArea(new Dimension(0, 35)));
        center.add(restart);
        center.add(Box.createRigidArea(new Dimension(0, 16)));
        center.add(highScores);
        center.add(Box.createRigidArea(new Dimension(0, 16)));
        center.add(exit);

        add(center, BorderLayout.CENTER);
    }

    // Helper pra criar botões padronizados
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(210, 48));
        button.setMaximumSize(new Dimension(210, 48));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        return button;
    }
}
