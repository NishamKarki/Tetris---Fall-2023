/**
 * This is a subclass that creates square brick
 * Nisham Karki
 * Modified Date: 11/18/23
 */

public class SquareBrick extends TetrisBrick{
    
    public SquareBrick(int wellCenter){
        super(wellCenter);
        
        color = color.ORANGE;
        colorNum = 5;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        int[][] brickSegments = {{0, 0}, {1, 1}, {0, 1}, {1, 0}};
        int rowSegment = 0, colSegment = 1;

        for (int brickSeg = 0; brickSeg < numSegments; brickSeg++) {
            position[brickSeg][rowSegment] = 
                    brickSegments[brickSeg][rowSegment];
            position[brickSeg][colSegment] = wellCenterCol 
                    + brickSegments[brickSeg][colSegment];
        }
        
        return position;
    }
    
    @Override
    public void rotate() {  
        
    }
    
    @Override
    public void unrotate() {  
        
    }
}
