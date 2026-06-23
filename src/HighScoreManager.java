package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {
    // Arquivo onde grava os high scores (no diretório do projeto)
    private final File scoreFile;

    public HighScoreManager() {
        this.scoreFile = new File(GameConfig.HIGH_SCORE_FILE);
    }

    // Carrega os scores do arquivo
    public List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        // Se o arquivo não existe ainda, retorna lista vazia
        if (!scoreFile.exists()) {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException ignored) {
                    // Se uma linha tá bugada, só ignora e continua
                    // melhor do que bugar o jogo todo
                }
            }
        } catch (IOException ignored) {
            // Se não conseguir ler, jogo continua normal
            // (tipo um fallback bem silencioso)
        }

        sortAndTrim(scores); // organiza e pega só top 5
        return scores;
    }

    // Salva um novo score
    public void saveScore(int score) {
        List<Integer> scores = loadScores(); // pega os antigos
        scores.add(score);                    // adiciona o novo
        sortAndTrim(scores);                  // organiza
        saveScores(scores);                   // grava tudo
    }

    // Grava a lista de scores no arquivo
    private void saveScores(List<Integer> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            for (Integer score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException ignored) {
            // Falha de escrita não devia crashar o jogo, ngl
        }
    }

    // Ordena decrescente e tira tudo depois do 5º lugar
    private void sortAndTrim(List<Integer> scores) {
        scores.sort(Collections.reverseOrder()); // maior primeiro
        while (scores.size() > 5) {
            scores.remove(scores.size() - 1); // remove o último
        }
    }
}
