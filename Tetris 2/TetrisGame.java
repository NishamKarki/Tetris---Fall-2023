/**
 * This is a class that contains the game logic of Tetris
 * Nisham Karki
 * Modified Date: 11/06/23
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
        if (moveCode == 'U') {
            if (validateMove(moveCode)){
                fallingBrick.rotate();  
            }
            if (!validateMove(moveCode)){
                fallingBrick.unrotate();  
            }
        } else if (validateMove(moveCode)) {
            if (moveCode == 'R') {
                fallingBrick.moveRight();
            }else if (moveCode == 'L') {
                fallingBrick.moveLeft();
            }
            if (moveCode == 'D') {
                fallingBrick.moveDown();
            } 
        } else if (moveCode == 'D') {
            transferColor();
            spawnBrick();
        }
    }
    
    private boolean validateMove(char moveCode) {
        for (int brickSegment = 0; brickSegment < fallingBrick.numSegments; 
                brickSegment++) {
            int brickRow = getSegRows(brickSegment);
            int brickCol = getSegCols(brickSegment);

            // Check if brick is on the floor or on top of a brick
            if (moveCode == 'D') {
                if (brickRow >= rows - 2 
                        || background[brickRow + 1][brickCol] > 0) {
                    return false;
                }
            }
            // Check brick is on the right wall or has a brick on the right side
            if (moveCode == 'R') {
                if (brickCol >= cols - 1) {
                    return false;
                }
                if (background[brickRow][brickCol + 1] > 0) {
                    return false;
                }
            }
            // Check brick is on the left wall or has a brick on the left side
            if (moveCode == 'L') {
                if (brickCol <= 1) {
                    return false;
                }
                if (background[brickRow][brickCol - 1] > 0) {
                    return false;
                }
            }
            // Check if rotation is valid
            if (moveCode == 'U') {
                int centerRow = getSegRows(1);
                int centerCol = getSegCols(1);
                boolean canRotate = true;

                // Check if the next rotation would result in an out-of-bounds
                // exception or overlap with other bricks
                for (int segNumber = 0; segNumber < fallingBrick.numSegments; segNumber++) {
                    if (segNumber != 1) {
                        int relativeRow = getSegRows(segNumber) - centerRow;
                        int relativeCol = getSegCols(segNumber) - centerCol;
                        int newRow = centerRow + relativeCol;
                        int newCol = centerCol - relativeRow;
                        
                        // Check if rotation is valid when near a wall or bricks
                        if (newCol < 1 || newCol >= cols || newRow >= rows 
                                || background[newRow][newCol] > 0 
                                || background[newRow + 1][newCol] > 0) {
                            canRotate = false;
                            break;
                        }
                    }
                }

                if (!canRotate) {
                    // Adjust position and perform rotation when Ess Brick is 
                    // near a wall or a brick
                    if (fallingBrick instanceof ElBricks 
                            || fallingBrick instanceof EssBrick
                            || fallingBrick instanceof JayBrick
                            || fallingBrick instanceof StackBrick 
                            || fallingBrick instanceof ZeeBrick) {
                        if (brickCol == cols - 1 
                                || background[brickRow][brickCol + 1] > 0) {
                            fallingBrick.moveLeft();
                        } else if (brickCol == 2 
                                || background[brickRow][brickCol - 1] > 0) {
                            fallingBrick.moveRight();
                        }  
                        
                        fallingBrick.unrotate();
                    } else if (fallingBrick instanceof LongBrick) {
                        if (brickCol == cols - 1 
                                || background[brickRow][brickCol + 1] > 0) {
                            fallingBrick.moveLeft();
                        } else if (brickCol == 1 
                                || background[brickRow][brickCol - 1] > 0) {
                            fallingBrick.moveRight();
                        }  
                        
                        fallingBrick.unrotate();
                    }
                    else if (brickCol == cols - 1) {
                            fallingBrick.moveLeft();
                        }
                        fallingBrick.unrotate();
                        if (brickCol == 1) {
                            fallingBrick.moveRight();
                        }
                } else {
                    return true;
                }
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
