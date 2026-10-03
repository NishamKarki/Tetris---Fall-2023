/**
 * This is a subclass that creates Long brick
 * Nisham Karki
 * 10/16/23
 */

public class LongBrick extends TetrisBrick{
    
    public LongBrick(int wellCenterCol){
        super(wellCenterCol);
        
        color = color.BLACK;
        colorNum = 3;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol;
        
        position[1][0] = 0;
        position[1][1] = wellCenterCol + 1;
        
        position[2][0] = 0;
        position[2][1] = wellCenterCol + 2;
        
        position[3][0] = 0;
        position[3][1] = wellCenterCol + 3;
        
        return position;
    }
}
