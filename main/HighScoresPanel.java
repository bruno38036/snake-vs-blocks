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
    private final JPanel scoreListPanel;

    public HighScoresPanel(GameFrame frame) {
        this.frame = frame;
        this.manager = new HighScoreManager();
        this.scoreListPanel = new JPanel();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252));

        JLabel title = new JLabel("Top 5 High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(30, 40, 65));
        title.setBorder(BorderFactory.createEmptyBorder(90, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        scoreListPanel.setOpaque(false);
        scoreListPanel.setLayout(new BoxLayout(scoreListPanel, BoxLayout.Y_AXIS));
        add(scoreListPanel, BorderLayout.CENTER);

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

    public void refreshScores() {
        scoreListPanel.removeAll();
        List<Integer> scores = manager.loadScores();
        if (scores.isEmpty()) {
            JLabel empty = scoreLabel("No scores yet. Start a new game!", 0);
            scoreListPanel.add(empty);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                scoreListPanel.add(scoreLabel((i + 1) + ". " + scores.get(i) + " points", i + 1));
                scoreListPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }
        revalidate();
        repaint();
    }

    private JLabel scoreLabel(String text, int position) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", position == 1 ? Font.BOLD : Font.PLAIN, 24));
        label.setForeground(new Color(35, 45, 70));
        label.setPreferredSize(new Dimension(GameConfig.WIDTH, 40));
        label.setMaximumSize(new Dimension(GameConfig.WIDTH, 40));
        return label;
    }
}
