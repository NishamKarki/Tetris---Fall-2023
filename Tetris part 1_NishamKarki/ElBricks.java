/**
 * This is a subclass that creates El brick
 * Nisham Karki
 * 10/16/23
 */

public class ElBricks extends TetrisBrick{
    
    public ElBricks(int wellCenterCol){
        super(wellCenterCol);
        color = color.RED;
        colorNum = 0;
        
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 1;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 1;
        
        position[2][0] = 2;
        position[2][1] = wellCenterCol + 1;
        
        position[3][0] = 2;
        position[3][1] = wellCenterCol + 2;
        
        return position;
    }
}
