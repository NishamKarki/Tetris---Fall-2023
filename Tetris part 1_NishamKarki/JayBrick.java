/**
 * This is a subclass that creates Jaybrick
 * Nisham Karki
 * 10/16/23
 */

public class JayBrick extends TetrisBrick{
    
    public JayBrick(int wellCenterCol) {
       super(wellCenterCol);
       
       color = color.GREEN;
       colorNum = 2;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 2;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 2;
        
        position[2][0] = 2;
        position[2][1] = wellCenterCol + 2;
        
        position[3][0] = 2;
        position[3][1] = wellCenterCol + 1;
        
        return position;
    }
}
