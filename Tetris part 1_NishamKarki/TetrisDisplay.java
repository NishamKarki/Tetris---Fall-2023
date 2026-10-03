/**
 * This is a class that creates graphic display of Tetris
 * Nisham Karki
 * 10/16/23
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TetrisDisplay extends JPanel{
    private TetrisGame game;
    
    private int start_x = 75;
    private int start_y = 60;
    private int cell_size = 15;
    private int speed = 300;
    
    private Timer timer;
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, 
        Color.ORANGE, Color.MAGENTA, Color.PINK};
    
    public TetrisDisplay(TetrisGame TetrisGame) {
        this.game = TetrisGame;

        timer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
                repaint();
            }
        });
        timer.start();
    }

    
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        
        drawWell(g);
        drawFallingBrick(g);
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
            g.drawRect(brick_x , brick_y, cell_size, cell_size );
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
        g.setColor(Color.WHITE);
        g.fillRect(start_x + outlineWidth, start_y - 1, rectWidth - 
                outlineWidth, rectHeight - outlineWidth + 1);
    }
    
    private void cycleMove() {
        game.makeMove('D');
    }
}
