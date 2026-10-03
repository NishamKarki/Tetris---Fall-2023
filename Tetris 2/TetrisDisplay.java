/**
 * This is a class that creates graphic display of Tetris
 * Nisham Karki
 * Modified Date: 11/06/23
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisDisplay extends JPanel{
    private TetrisGame game;
    
    private int start_x = 80;
    private int start_y = 100;
    private int cell_size = 15;
    private int speed = 200;
    private boolean pause = false;
    
    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, 
        Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.PINK};
    
    public TetrisDisplay(TetrisGame TetrisGame) {
        this.game = TetrisGame;

        timer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
                repaint();
            }
        });
        timer.start();

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed (KeyEvent ke) {
                translateKey(ke);
                repaint();
            }
        });
        
       this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        
        drawWell(g);
        drawFallingBrick(g);
        drawBackground(g);
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
            g.setColor(Color.GRAY);
            g.drawRect(brick_x, brick_y, cell_size, cell_size );
        }
    }

    private void drawWell(Graphics g) {
        int rectWidth = game.getCols() * cell_size;
        int rectHeight = game.getRows() * cell_size;
        int outlineWidth = cell_size; 
        
        // Left rectangle
        g.fillRect(start_x, start_y - 1 , outlineWidth, rectHeight + 1);
        // Right rectangle
        g.fillRect(start_x + rectWidth, start_y - 1, outlineWidth, 
                rectHeight + 1);
        // Bottom rectangle
        g.fillRect(start_x + outlineWidth, start_y + rectHeight - outlineWidth,
                rectWidth, outlineWidth);
        // Well background color
        g.setColor(colors[0]);
        g.fillRect(start_x + outlineWidth, start_y - 1, rectWidth - 
                outlineWidth, rectHeight - outlineWidth + 1);
    }
    
    public void drawBackground(Graphics g) {
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
                    g.setColor(Color.GRAY);
                    g.drawRect(xPosition, yPosition, cell_size, cell_size);
                }
            }
        }
    }

    private void translateKey(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        
        switch(keyCode) {
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
                if (pause){
                    timer.stop();
                }
                else {
                    timer.start();
                }
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;
        }
    }
    
    private void cycleMove() {
        game.makeMove('D');
    }
}
