/**
 * This is a subclass that creates Stack brick
 * Nisham Karki
 * Modified Date: 11/18/23
 */

public class StackBrick extends TetrisBrick{
    
    public StackBrick(int wellCenterCol){
        super(wellCenterCol);
        
        color = color.MAGENTA;
        colorNum = 6;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 1;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 1;
        
        position[2][0] = 1;
        position[2][1] = wellCenterCol + 2;
        
        position[3][0] = 1;
        position[3][1] = wellCenterCol;
        
        return position;
    }
    
    @Override
    public void rotate() {
        int rowSegment = 0, colSegment = 1;
        int rotatingSegment = 1;
        
        int centerRow = position[rotatingSegment][rowSegment];
        int centerCol = position[rotatingSegment][colSegment];
        int newRow, newCol;

        for (int brickSeg = 0; brickSeg < numSegments; brickSeg++) {
            if (brickSeg != rotatingSegment) {
                int relativeRow = position[brickSeg][rowSegment] - centerRow;
                int relativeCol = position[brickSeg][colSegment] - centerCol;

                // Rotate 90 degrees clockwise
                newRow = centerRow + relativeCol;
                newCol = centerCol - relativeRow;

                position[brickSeg][rowSegment] = newRow;
                position[brickSeg][colSegment] = newCol;
            }
        }
    }
    
    @Override
    public void unrotate() {
        int rowSegment = 0, colSegment = 1;
        int rotatingSegment = 1;
        
        int centerRow = position[rotatingSegment][rowSegment];
        int centerCol = position[rotatingSegment][colSegment];
        int newRow, newCol;

        for (int brickSeg = 0; brickSeg < numSegments; brickSeg++) {
            if (brickSeg != rotatingSegment) {
                int relativeRow = position[brickSeg][rowSegment] - centerRow;
                int relativeCol = position[brickSeg][colSegment] - centerCol;

                // Rotate 90 degrees anti-clockwise
                newRow = centerRow - relativeCol;
                newCol = centerCol + relativeRow;

                position[brickSeg][rowSegment] = newRow;
                position[brickSeg][colSegment] = newCol;
            }
        }
    }
}
