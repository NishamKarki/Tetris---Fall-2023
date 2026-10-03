/**
 * This is a leaderboard class that saves player's high scores
 * Nisham Karki
 * 11/28/23
 */

import java.awt.BorderLayout;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Leaderboard {

    private List<PlayerScore> topScores;
    private static final String FILE_NAME = "top_score_tetris.txt";

    public Leaderboard() {
        topScores = new ArrayList<>();
        loadScores();
    }

    public void leaderboardPanel() {
        int windowWidth = 250, windowHeight = 300;
        
        JFrame leaderboardFrame = new JFrame("Tetris Game         Nisham Karki");
        leaderboardFrame.setSize(windowWidth, windowHeight);
        leaderboardFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea scoresTextArea = new JTextArea();

        scoresTextArea.setEditable(false);
        scoresTextArea.setText(getTopScores());
        mainPanel.add(scoresTextArea, BorderLayout.CENTER);

        leaderboardFrame.add(mainPanel);
        leaderboardFrame.setVisible(true);
    }

    public void addScore(String name, int score) {
        topScores.add(new PlayerScore(name, score));
        updateScores();
    }

    public void clearScores() {
        topScores.clear();
        updateScores();
    }

    public String getTopScores() {
        StringBuilder scoresString = new StringBuilder();
        for (PlayerScore entry : topScores) {
            scoresString.append("Name: ").append(entry.getName())
                    .append(", Score: ").append(entry.getScore())
                    .append("\n");
        }
        return scoresString.toString();
    }

    public boolean newHighScore(int score) {
        if (topScores.size() < 10) {
            return true;
        } else {
            PlayerScore tenthScore = topScores.get(9);
            return score > tenthScore.getScore();
        }
    }

    private void updateScores() {
        topScores.sort(Comparator.comparingInt(PlayerScore::getScore).reversed());
        
        int size = Math.min(10, topScores.size());
        List<PlayerScore> updatedScores = 
                new ArrayList<>(topScores.subList(0, size));
        
        saveScores(updatedScores);
    }

    private void saveScores(List<PlayerScore> scoresToSave) {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scoresToSave);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScores() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fileInput = new FileInputStream(file);
                if (fileInput.available() > 0) {
                    ObjectInputStream scoreInput = 
                            new ObjectInputStream(fileInput);
                    topScores = (List<PlayerScore>) scoreInput.readObject();
                    scoreInput.close();
                }
                fileInput.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class PlayerScore implements Serializable {

    private String name;
    private int score;

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
