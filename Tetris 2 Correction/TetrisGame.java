/**
 * This is a class that contains the game logic of Tetris
 * Nisham Karki
 * Modified Date: 11/18/23
 */

import java.util.Random;

public class TetrisGame {
    private TetrisBrick fallingBrick;
    
    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    
    private Random randomGen;
    
    public TetrisGame(int row, int col){
        rows = row;
        cols = col;
        randomGen = new Random();
        
        initBoard();
        spawnBrick();
    }
    
    public void initBoard() {
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

    private TetrisBrick spawnBrick(){      
        int randBrick = randomGen.nextInt(numBrickTypes);
        int wellCenter = (cols / 2) - 1;
        
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
        switch (moveCode) {
            case 'D':
                fallingBrick.moveDown();
                if (!validateMove()) {
                    fallingBrick.moveUp();
                    transferColor();
                    spawnBrick();
                }
                break;
            case 'U':
                if (fallingBrick instanceof LongBrick) {
                    fallingBrick.rotate();
                    // Correct rotation on left wall
                    if (!validateMove()) {
                        fallingBrick.moveRight();
                        fallingBrick.moveRight();
                        
                        // Correct rotation on right wall
                        if (!validateMove()) {
                            fallingBrick.moveLeft();
                            fallingBrick.moveLeft();
                            
                            // If still stuck on right wall make further 
                            // correction
                            if (!validateMove()) {
                                fallingBrick.moveLeft();
                                fallingBrick.moveLeft();
                                fallingBrick.unrotate();
                                fallingBrick.unrotate();
                                
                                // When rotating near the floor, prevent 
                                // overlapping against the floor boundry
                                if (!validateMove()) {
                                    fallingBrick.moveUp();
                                    if (!validateMove()) {
                                        fallingBrick.moveUp();
                                    }
                                }
                            }
                        }
                    }
                }
                if (!(fallingBrick instanceof LongBrick)) {
                    fallingBrick.rotate();
                    
                    // If near the left wall, move right to allow rotation 
                    // without boundry overlapping
                    if (!validateMove()) {
                        fallingBrick.moveRight();
                        
                        // If near the rigth wall, move left to allow rotation
                        // without boundry overlapping                       
                        if (!validateMove()) {
                            fallingBrick.moveLeft();
                            
                            // If still near the rigth wall, make more 
                            // adjustments to prevent boundry overlapping
                            if (!validateMove()) {
                                fallingBrick.moveLeft();
                                fallingBrick.unrotate();
                                fallingBrick.unrotate();
                            }
                        }
                    }
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
    
    private boolean validateMove() {
        try {
            int wellFloor = rows - 1;

            for (int brickSegment = 0; brickSegment < fallingBrick.numSegments; brickSegment++) {
                int brickRow = getSegRows(brickSegment);
                int brickCol = getSegCols(brickSegment);

                // Check if brick is on the floor or on the wall
                if (brickRow >= wellFloor || brickCol >= cols || brickCol < 1) {
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
