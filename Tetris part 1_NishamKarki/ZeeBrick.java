/**
 * This is a subclass that creates Zee brick
 * Nisham Karki
 * 10/16/23
 */

public class ZeeBrick extends TetrisBrick{
    
    public ZeeBrick(int wellCenterCol){
        super(wellCenterCol);
        
        color = color.PINK;
        colorNum = 6;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 1;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 1;
        
        position[2][0] = 0;
        position[2][1] = wellCenterCol;
        
        position[3][0] = 1;
        position[3][1] = wellCenterCol + 2;
        
        return position;
    }
}
