
/**
 * This is the executable class that holds the game display
 * Nisham Karki
 * Modified Date: 11/28/23
 */

import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class TetrisWindow extends JFrame {

    private TetrisGame game;
    private TetrisDisplay display;
    private Leaderboard leaderboard = new Leaderboard();

    private int win_width = 520;
    private int win_height = 700;
    private int game_rows = 20;
    private int game_cols = 12;

    public TetrisWindow() {
        JFrame myFrame = new JFrame("Tetris Game         Nisham Karki");
        myFrame.setSize(win_width, win_height);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);
        myFrame.add(display);

        initMenus(myFrame);

        myFrame.setVisible(true);
    }

    public void initMenus(JFrame myFrame) {
        JMenuBar menuBar = new JMenuBar();
        myFrame.setJMenuBar(menuBar);

        JMenu setupGame = new JMenu("Game");
        menuBar.add(setupGame);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                game.newGame();
            }
        });
        setupGame.add(newGame);
        setupGame.addSeparator();

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.saveToFile();
            }
        });
        setupGame.add(saveGame);
        setupGame.addSeparator();

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.retrieveFromFile();
            }
        });
        setupGame.add(loadGame);

        JMenu setupLeaderBoard = new JMenu("LeaderBoard");
        menuBar.add(setupLeaderBoard);

        JMenuItem displayLeaderBoard = new JMenuItem("See Leaderboard");
        displayLeaderBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                leaderboard.leaderboardPanel();
            }
        });
        setupLeaderBoard.add(displayLeaderBoard);
        setupLeaderBoard.addSeparator();

        JMenuItem clearHighScore = new JMenuItem("Clear High Score");
        clearHighScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                leaderboard.clearScores();
            }
        });
        setupLeaderBoard.add(clearHighScore);
    }

    public static void main(String[] args) {
        TetrisWindow testWindow = new TetrisWindow();
    }
}
