/**
 * This is an abstract superclass that implements all seven types of bricks 
 * in Tetris
 * Nisham Karki
 * Modified Date: 11/28/23
 */

import java.awt.Color;
import java.util.Arrays;

public abstract class TetrisBrick {
    protected int numSegments = 4;
    protected int[][] position = new int[numSegments][2];
    protected int colorNum;
    protected Color color;
    
    public TetrisBrick(int centerColNum){
        initPosition(centerColNum);
    }
    
    public String toString() {
        String brickInfo = "Tetris Brick Info:\n" 
                + "Number of segments: " 
                + numSegments + "\nColor Number: " + colorNum + "\nColor: " 
                + color + "Position: " + Arrays.deepToString(position);
        
        return brickInfo;
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
    
    public void moveLeft() {
       for (int wellRow = 0; wellRow < numSegments; wellRow++) {
            position[wellRow][1]--;
        } 
    }
    
    public void moveRight() {
       for (int wellRow = 0; wellRow < numSegments; wellRow++) {
            position[wellRow][1]++;
        } 
    }
    
    public int getColorNumber(){
        return colorNum;
    }
    
    public abstract void rotate();
    
    public abstract void unrotate();
    
    public abstract int[][] initPosition(int wellCenterCol);
}
