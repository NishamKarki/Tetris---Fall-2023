
/**
 * This is a class that creates graphic display of Tetris
 * Nisham Karki
 * Modified Date: 11/28/23
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisDisplay extends JPanel {

    private TetrisGame game;

    private int start_x = 130;
    private int start_y = 100;
    private int cell_size = 20;
    private int speed = 300;
    private boolean pause = false;
    private boolean showGameOverMessage = false;

    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN,
        Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.PINK};

    public TetrisDisplay(TetrisGame game) {
        this.game = game;

        timer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
            }
        });
        timer.start();

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                translateKey(ke);
            }
        });

        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawWell(g);
        drawBackground(g);
        drawFallingBrick(g);
        displayGameScore(g);
        drawGameOver(g);
    }

    private void drawFallingBrick(Graphics g) {
        int brickColorNum = game.getFallingBrickColor();

        for (int wellRow = 0; wellRow < game.getNumberSegs(); wellRow++) {
            int brick_x = start_x + (game.getSegCols(wellRow) * cell_size);
            int brick_y = start_y + (game.getSegRows(wellRow) * cell_size);

            // set brick colors
            g.setColor(colors[brickColorNum]);
            g.fillRect(brick_x, brick_y, cell_size, cell_size);
            // set brick boarder segments color
            g.setColor(Color.BLACK);
            g.drawRect(brick_x, brick_y, cell_size, cell_size);
        }
    }

    private void drawWell(Graphics g) {
        int rectWidth = game.getCols() * cell_size;
        int rectHeight = game.getRows() * cell_size;

        // Left rectangle
        g.fillRect(start_x - cell_size, start_y, cell_size, rectHeight);
        // Right rectangle
        g.fillRect(start_x + rectWidth, start_y, cell_size,
                rectHeight);
        // Bottom rectangle

        g.fillRect(start_x - cell_size, start_y + rectHeight,
                rectWidth + 2 * cell_size, cell_size);
        // Well background color
        g.setColor(Color.WHITE);
        g.fillRect(start_x, start_y, rectWidth, rectHeight);
    }

    private void drawBackground(Graphics g) {
        for (int boardRow = 0; boardRow < game.getRows(); boardRow++) {
            for (int boardCol = 0; boardCol < game.getCols(); boardCol++) {
                int colorNum = game.fetchBoardPosition(boardRow, boardCol);

                if (colorNum > 0) {
                    // To set the color based on the color number
                    g.setColor(colors[colorNum]);
                    int xPosition = start_x + boardCol * cell_size;
                    int yPosition = start_y + boardRow * cell_size;

                    // to draw the colored cells
                    g.fillRect(xPosition, yPosition, cell_size, cell_size);

                    // to draw an outline around the cell
                    g.setColor(Color.BLACK);
                    g.drawRect(xPosition, yPosition, cell_size, cell_size);
                }
            }
        }
    }

    private void displayGameScore(Graphics g) {
        int xCoordinate = 0;
        int yCoordinate = 20;
        int xShift = 13;
        int yShift = 5;
        int rectWidth = 150;

        int fontSize = 20;

        String scoreText = "Score: " + game.getScore();

        // Set up the color and draw the rectangle
        g.setColor(Color.BLACK);
        g.fillRect(xCoordinate, yCoordinate - getHeight(),
                rectWidth, getHeight() + cell_size);

        // Draw the score text on top of the rectangle
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString(scoreText, xCoordinate + xShift, yCoordinate + yShift);
    }

    private void drawGameOver(Graphics g) {
        if (!game.hasSpawnSpace()) {
            int fontSize = 36;
            int smallerFontSize = 20;

            int rectWidthShift = 155;
            int rectHeightShift = 60;

            int textShiftX = 15;
            int textShiftY = 10;
            int textYShift = smallerFontSize;

            String gameOverText = "Game Over";
            String saveScore = "Press Enter to Continue";

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));

            int textWidth = gameOverText.length() * (fontSize / 2);
            int textHeight = fontSize;
            int textCoordinateX = (getWidth() - textWidth) / 2;
            int textCoordinateY = (getHeight() + textHeight) / 2;

            int rectWidth = textWidth + rectWidthShift;
            int rectHeight = textHeight + rectHeightShift;
            int rectX = (getWidth() - rectWidth) / 2;
            int rectY = (getHeight() - rectHeight) / 2;

            g.fillRect(rectX, rectY, rectWidth, rectHeight);
            g.setColor(Color.WHITE);

            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString(gameOverText, textCoordinateX - textShiftX,
                    textCoordinateY - textShiftY);

            g.setFont(new Font("Arial", Font.PLAIN, smallerFontSize));
            int saveScoreWidth = saveScore.length() * (smallerFontSize / 2);
            int saveScoreX = (getWidth() - saveScoreWidth) / 2;
            g.drawString(saveScore, saveScoreX + cell_size, textCoordinateY
                    + textYShift);

            showGameOverMessage = true;
        }
    }

    private void translateKey(KeyEvent ke) {
        int keyCode = ke.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                game.makeMove('U');
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove('R');
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('L');
                break;
            case KeyEvent.VK_SPACE:
                pause = !pause;
                if (pause) {
                    timer.stop();
                } else {
                    timer.start();
                }
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;
            case KeyEvent.VK_ENTER:
                if (showGameOverMessage) {
                    game.saveScoreToLeaderboard();
                }
        }
        repaint();
    }

    private void cycleMove() {
        game.makeMove('D');
        repaint();
    }
}
