/**
 * This is a class that contains the game logic of Tetris
 * Nisham Karki
 * 10/16/23
 */

import java.util.Random;

public class TetrisGame {
    private TetrisBrick fallingBrick;
    
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    
    private Random randomgen;
    
    public TetrisGame(int row, int col){
        rows = row;
        cols = col;
        randomgen = new Random();
        
        spawnBrick();
    }

    private TetrisBrick spawnBrick(){      
        int randBrick = randomgen.nextInt(numBrickTypes);
        int wellCenter = (cols /2) - 1;
        
        switch(randBrick) {
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
    
    
    public void makeMove(char moveCode){
        if (moveCode == 'D'){
            if (validateMove()) {
                fallingBrick.moveDown();
            }
            else{
                fallingBrick.moveUp();
                spawnBrick();
            }
        }
    }
    
    private boolean validateMove() {
        for (int brickSegment = 0; brickSegment < fallingBrick.numSegments; brickSegment++) {
            if (getSegRows(brickSegment) >= rows - 2) {
                return false;
            }
        }
        return true;
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
    
    public int getNumberSegs(){
        return fallingBrick.numSegments;
    }
    
    public int getSegRows(int segmentNumber) {
        return fallingBrick.position[segmentNumber][0];
    }

    public int getSegCols(int segmentNumber) {
        return fallingBrick.position[segmentNumber][1];
    }
}
