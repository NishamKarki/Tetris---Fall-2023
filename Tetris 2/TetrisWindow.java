/**
 * This is the executable class that holds the game display
 * Nisham Karki
 * Modified Date: 11/06/23
 */

import javax.swing.JFrame;

public class TetrisWindow extends JFrame{
    private TetrisGame game;
    private TetrisDisplay display;
    
    private int win_width = 520;
    private int win_height = 700;
    private int game_rows = 32;
    private int game_cols = 22;
    
    public TetrisWindow(){
        JFrame myFrame = new JFrame("Tetris Game         Nisham Karki");
        myFrame.setSize(win_width, win_height);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game); 
        myFrame.add(display);
        
        myFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        TetrisWindow testWindow = new TetrisWindow();
    }
}
