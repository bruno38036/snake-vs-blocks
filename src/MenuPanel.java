package main;

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

public class MenuPanel extends JPanel {
    public MenuPanel(GameFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252)); // fundo limpo

        // Título top
        JLabel title = new JLabel("Snake vs. Blocks", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 44));
        title.setForeground(new Color(30, 40, 65));
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(110, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Painel com os botões (centrado)
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // vertical

        JButton newGame = createButton("New Game");
        JButton highScores = createButton("High Scores");
        JButton exit = createButton("Exit");

        // Callbacks dos botões
        newGame.addActionListener(event -> frame.startGame());
        highScores.addActionListener(event -> frame.showHighScores());
        exit.addActionListener(event -> System.exit(0));

        // Adiciona com espaço entre eles
        buttons.add(newGame);
        buttons.add(Box.createRigidArea(new Dimension(0, 18)));
        buttons.add(highScores);
        buttons.add(Box.createRigidArea(new Dimension(0, 18)));
        buttons.add(exit);

        // Wrapper pra centralizar
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.add(buttons);
        add(wrapper, BorderLayout.CENTER);

        // Footer pequeno lá embaixo
        JLabel footer = new JLabel("SCC0504 Substitute Project \u2022 Java Swing", SwingConstants.CENTER);
        footer.setForeground(new Color(75, 85, 110));
        footer.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 35, 0));
        add(footer, BorderLayout.SOUTH);
    }

    // Helper pra criar botões com estilo consistente
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(210, 48));
        button.setMaximumSize(new Dimension(210, 48));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false); // remove o outline padrão
        return button;
    }
}
