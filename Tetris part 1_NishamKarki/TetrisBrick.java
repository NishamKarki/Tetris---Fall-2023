/**
 * This is an abstract superclass that implements all seven types of bricks 
 * in Tetris
 * Nisham Karki
 * 10/16/23
 */

import java.awt.Color;

public abstract class TetrisBrick {
    protected int numSegments = 4;
    protected int[][] position = new int[numSegments][2];
    protected int colorNum;
    protected Color color;
    
    public TetrisBrick(int centerColNum){
        initPosition(centerColNum);
      
    }
    
    public void moveDown(){
        for (int wellRow = 0; wellRow < numSegments; wellRow++) {
            position[wellRow][0]++;
        }
    }
    
    public void moveUp(){
        for (int wellRow = 0; wellRow < numSegments; wellRow++) {
            position[wellRow][0]--;
        }
    }
    
    public int getColorNumber(){
        return colorNum;
    }
    
    public abstract int[][] initPosition(int wellCenterCol);
}
