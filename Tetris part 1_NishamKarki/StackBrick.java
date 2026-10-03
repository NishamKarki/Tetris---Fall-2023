/**
 * This is a subclass that creates Stack brick
 * Nisham Karki
 * 10/16/23
 */

public class StackBrick extends TetrisBrick{
    
    public StackBrick(int wellCenterCol){
        super(wellCenterCol);
        
        color = color.MAGENTA;
        colorNum = 5;
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
}
