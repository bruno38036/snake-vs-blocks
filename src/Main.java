package main;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Roda o código gráfico na Event Dispatch Thread (padrão Swing)
        // se não fazer isso dá problema de thread safety
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true); // mostra a janela
        });
    }
}
