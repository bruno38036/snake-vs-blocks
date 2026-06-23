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
    private final File scoreFile;

    public HighScoreManager() {
        this.scoreFile = new File(GameConfig.HIGH_SCORE_FILE);
    }

    public List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        if (!scoreFile.exists()) {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException ignored) {
                    // Ignore invalid lines to keep persistence robust.
                }
            }
        } catch (IOException ignored) {
            // If loading fails, the game still works with an empty ranking.
        }

        sortAndTrim(scores);
        return scores;
    }

    public void saveScore(int score) {
        List<Integer> scores = loadScores();
        scores.add(score);
        sortAndTrim(scores);
        saveScores(scores);
    }

    private void saveScores(List<Integer> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            for (Integer score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException ignored) {
            // Persistence failure should not crash the game.
        }
    }

    private void sortAndTrim(List<Integer> scores) {
        scores.sort(Collections.reverseOrder());
        while (scores.size() > 5) {
            scores.remove(scores.size() - 1);
        }
    }
}
