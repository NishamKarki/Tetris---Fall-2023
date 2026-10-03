
/**
 * This is a class that contains the game logic of Tetris
 * Nisham Karki
 * Modified Date: 11/28/23
 */

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class TetrisGame implements SaveableGame {

    private TetrisBrick fallingBrick;
    private Leaderboard leaderboard = new Leaderboard();

    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int state;
    private int score;

    private Random randomGen;

    public TetrisGame(int row, int col) {
        rows = row;
        cols = col;
        randomGen = new Random();

        initBoard();
        spawnBrick();
    }

    public String toString() {
        String boardString = "" + background.length + " " + background[0].length
                + "\n";
        for (int row = 0; row < background.length; row++) {
            for (int col = 0; col < background[0].length; col++) {
                boardString += background[row][col] + " ";
            }
            boardString += "\n";
        }
        boardString = boardString.substring(0, boardString.length() - 1);

        return boardString;
    }

    public void initBoard() {
        state = 1;
        score = 0;
        background = new int[rows][cols];
    }

    public void newGame() {
        initBoard();
        spawnBrick();
    }

    public int fetchBoardPosition(int row, int col) {
        return background[row][col];
    }

    private void transferColor() {
        for (int brickSegs = 0; brickSegs < getNumberSegs(); brickSegs++) {
            int segmentRow = getSegRows(brickSegs);
            int segmentCol = getSegCols(brickSegs);

            background[segmentRow][segmentCol] = getFallingBrickColor();
        }
    }

    private TetrisBrick spawnBrick() {
        int randBrick = randomGen.nextInt(numBrickTypes);
        int wellCenter = (cols / 2) - 1;

        switch (randBrick) {
            case 0:
                fallingBrick = new ElBricks(wellCenter);
                break;
            case 1:
                fallingBrick = new EssBrick(wellCenter);
                break;
            case 2:
                fallingBrick = new JayBrick(wellCenter);
                break;
            case 3:
                fallingBrick = new LongBrick(wellCenter);
                break;
            case 4:
                fallingBrick = new SquareBrick(wellCenter);
                break;
            case 5:
                fallingBrick = new StackBrick(wellCenter);
                break;
            case 6:
                fallingBrick = new ZeeBrick(wellCenter);
                break;
        }
        return fallingBrick;
    }

    public void makeMove(char moveCode) {
        if (state == 1) {
            switch (moveCode) {
                case 'D':
                    fallingBrick.moveDown();
                    if (!validateMove()) {
                        fallingBrick.moveUp();
                        transferColor();
                        detectAndRemoveFullRows();

                        if (!hasSpawnSpace()) {
                            state = 0;
                        } else {
                            spawnBrick();
                        }
                    }
                    break;
                case 'U':
                    fallingBrick.rotate();
                    if (!validateMove()) {
                        fallingBrick.unrotate();
                    }
                    break;
                case 'R':
                    fallingBrick.moveRight();
                    if (!validateMove()) {
                        fallingBrick.moveLeft();
                    }
                    break;
                case 'L':
                    fallingBrick.moveLeft();
                    if (!validateMove()) {
                        fallingBrick.moveRight();
                    }
                    break;
            }
        }
    }

    private boolean validateMove() {
        try {
            for (int brickSegment = 0; brickSegment < fallingBrick.numSegments;
                    brickSegment++) {
                int brickRow = getSegRows(brickSegment);
                int brickCol = getSegCols(brickSegment);

                // Check if brick is on the floor or on the wall
                if (brickRow > rows || brickCol > cols || brickCol < 0) {
                    return false;
                }

                // Check for brick below or on the sides
                if (background[brickRow][brickCol] > 0) {
                    return false;
                }
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getFallingBrickColor() {
        return fallingBrick.getColorNumber();
    }

    public int getNumberSegs() {
        return fallingBrick.numSegments;
    }

    public int getSegRows(int segmentNumber) {
        return fallingBrick.position[segmentNumber][0];
    }

    public int getSegCols(int segmentNumber) {
        return fallingBrick.position[segmentNumber][1];
    }

    public int getScore() {
        return score;
    }
    
    public void saveScoreToLeaderboard() {
        if (!hasSpawnSpace()) {
            if (getScore() != 0 && leaderboard.newHighScore(getScore())) {
                String playerName = JOptionPane.showInputDialog(null, "You made"
                        + "a high score. Enter your username to save your score", 
                        "Save your score        Nisham Karki", 0);
                
                if (playerName != null || !playerName.isBlank()) {
                    leaderboard.addScore(playerName, getScore());
                }
            }
            newGame();
        }
    }
    
    public boolean isFirstThreeRowsEmpty() {
        for (int gameRow = 0; gameRow < 3; gameRow++) {
            for (int gameCol = 0; gameCol < background[gameRow].length;
                    gameCol++) {
                if (background[gameRow][gameCol] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasSpawnSpace() {
        for (int brickSegment = 0; brickSegment < getNumberSegs();
                brickSegment++) {
            int segmentRow = getSegRows(brickSegment);
            int segmentCol = getSegCols(brickSegment);

            if (segmentRow < 1 && background[segmentRow][segmentCol] != 0) {
                return false;
            }
        }

        if ((fallingBrick instanceof JayBrick
                || fallingBrick instanceof ElBricks)
                && !isFirstThreeRowsEmpty()) {
            return false;
        }

        return true;
    }

    private int detectAndRemoveFullRows() {
        int linesRemoved = 0;
        int row = getMinimumRow(); // Start from the lowest row of the last placed brick

        while (row <= getMaximumRow()) {
            if (!rowHasSpace(row)) {
                removeRow(row);
                linesRemoved++;
            } else {
                row++;
            }
        }

        updateScore(linesRemoved);
        return linesRemoved;
    }

    private void removeRow(int row) {
        int bgRow = 0;

        for (int rowIndex = row; rowIndex > 0; rowIndex--) {
            int previousRowIndex = rowIndex - 1;

            System.arraycopy(background[previousRowIndex], 0,
                    background[rowIndex], 0, cols);
        }

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            background[bgRow][colIndex] = 0;
        }
    }

    private boolean rowHasSpace(int rowNumber) {
        for (int col = 0; col < cols; col++) {
            if (background[rowNumber][col] == 0) {
                return true;
            }
        }
        
        return false;
    }

    private int getMinimumRow() {
        int minRow = rows;
        for (int segment = 0; segment < getNumberSegs(); segment++) {
            int segmentRow = getSegRows(segment);
            minRow = Math.min(minRow, segmentRow);
        }
        return minRow;
    }

    private int getMaximumRow() {
        int maxRow = 0; // Start from the top row
        for (int segment = 0; segment < getNumberSegs(); segment++) {
            int segmentRow = getSegRows(segment);
            maxRow = Math.max(maxRow, segmentRow);
        }
        return maxRow;
    }

    private void updateScore(int linesRemoved) {
        switch (linesRemoved) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
            default:
                break;
        }
    }

    @Override
    public void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (FileWriter outWriter = new FileWriter(selectedFile)) {
                outWriter.write(this.toString());
            } catch (IOException ioe) {
                System.err.println("Error saving to file: " + ioe.getMessage());
            }
        }
    }

    @Override
    public void retrieveFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                Scanner inScann = new Scanner(selectedFile);
                rows = inScann.nextInt();
                cols = inScann.nextInt();
                background = new int[rows][cols];

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        background[row][col] = inScann.nextInt();
                    }
                }
            } catch (Exception e) {
                System.err.print("Error occurred during retrieve from file ");
            }
        }
    }
}
