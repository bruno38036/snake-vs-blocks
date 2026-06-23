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
import java.util.List;

public class HighScoresPanel extends JPanel {
    private final GameFrame frame;
    private final HighScoreManager manager;
    private final JPanel scoreListPanel; // painel que vai ter a lista de scores

    public HighScoresPanel(GameFrame frame) {
        this.frame = frame;
        this.manager = new HighScoreManager();
        this.scoreListPanel = new JPanel();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252));

        // Título
        JLabel title = new JLabel("Top 5 High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(30, 40, 65));
        title.setBorder(BorderFactory.createEmptyBorder(90, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Painel com a lista (será preenchido no refreshScores)
        scoreListPanel.setOpaque(false);
        scoreListPanel.setLayout(new BoxLayout(scoreListPanel, BoxLayout.Y_AXIS));
        add(scoreListPanel, BorderLayout.CENTER);

        // Botão "Back to Menu"
        JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Arial", Font.BOLD, 16));
        back.setFocusPainted(false);
        back.addActionListener(event -> frame.showMenu());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(20, 0, 55, 0));
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);
    }

    // Chamado toda vez que a gente quer atualizar a lista
    public void refreshScores() {
        scoreListPanel.removeAll(); // limpa tudo
        List<Integer> scores = manager.loadScores(); // carrega scores

        if (scores.isEmpty()) {
            // Se não tem scores ainda, mostra msg
            JLabel empty = scoreLabel("No scores yet. Start a new game!", 0);
            scoreListPanel.add(empty);
        } else {
            // Mostra cada score com número da posição
            for (int i = 0; i < scores.size(); i++) {
                scoreListPanel.add(scoreLabel((i + 1) + ". " + scores.get(i) + " points", i + 1));
                scoreListPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }
        revalidate(); // refaz o layout
        repaint();    // redesenha
    }

    // Helper pra criar um label de score com estilo
    private JLabel scoreLabel(String text, int position) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        // 1º lugar fica em negrito, resto normal
        label.setFont(new Font("Arial", position == 1 ? Font.BOLD : Font.PLAIN, 24));
        label.setForeground(new Color(35, 45, 70));
        label.setPreferredSize(new Dimension(GameConfig.WIDTH, 40));
        label.setMaximumSize(new Dimension(GameConfig.WIDTH, 40));
        return label;
    }
}
