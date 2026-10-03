/**
 * This is a subclass that creates Ess brick
 * Nisham Karki
 * Modified Date: 11/06/23
 */

public class EssBrick extends TetrisBrick{
    
    public EssBrick(int wellCenterCol) {
        super(wellCenterCol);
        
        color = color.BLUE;
        colorNum = 2;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 1;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 1;
        
        position[2][0] = 0;
        position[2][1] = wellCenterCol + 2;
        
        position[3][0] = 1;
        position[3][1] = wellCenterCol;
        
        return position;
    }
    
    @Override
    public void rotate() {
        int centerRow = position[1][0];
        int centerCol = position[1][1];
        int newRow, newCol;

        for (int brickSeg = 0; brickSeg < numSegments; brickSeg++) {
            if (brickSeg != 1) {
                int relativeRow = position[brickSeg][0] - centerRow;
                int relativeCol = position[brickSeg][1] - centerCol;

                // Rotate 90 degrees clockwise
                newRow = centerRow + relativeCol;
                newCol = centerCol - relativeRow;

                position[brickSeg][0] = newRow;
                position[brickSeg][1] = newCol;
            }
        }
    }
    
    @Override
    public void unrotate() {
        int centerRow = position[1][0];
        int centerCol = position[1][1];
        int newRow, newCol;

        for (int brickSeg = 0; brickSeg < numSegments; brickSeg++) {
            if (brickSeg != 1) {
                int relativeRow = position[brickSeg][0] - centerRow;
                int relativeCol = position[brickSeg][1] - centerCol;

                // Rotate 90 degrees anti-clockwise
                newRow = centerRow - relativeCol;
                newCol = centerCol + relativeRow;

                position[brickSeg][0] = newRow;
                position[brickSeg][1] = newCol;
            }
        }
    }
}
