/**
 * This is a subclass that creates Ess brick
 * Nisham Karki
 * 10/16/23
 */

public class EssBrick extends TetrisBrick{
    
    public EssBrick(int wellCenterCol) {
        super(wellCenterCol);
        
        color = color.BLUE;
        colorNum = 1;
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
}
