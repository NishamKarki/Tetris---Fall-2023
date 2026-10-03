/**
 * This is a subclass that creates square brick
 * Nisham Karki
 * Modified Date: 11/06/23
 */

public class SquareBrick extends TetrisBrick{
    
    public SquareBrick(int wellCenter){
        super(wellCenter);
        
        color = color.ORANGE;
        colorNum = 5;
    }
    
    @Override
    public int[][] initPosition(int wellCenterCol) {
        position[0][0] = 0;
        position[0][1] = wellCenterCol + 1;
        
        position[1][0] = 1;
        position[1][1] = wellCenterCol + 2;
        
        position[2][0] = 0;
        position[2][1] = wellCenterCol + 2;
        
        position[3][0] = 1;
        position[3][1] = wellCenterCol + 1;
        
        return position;
    }
    
    @Override
    public void rotate() {  
        
    }
    
    @Override
    public void unrotate() {  
        
    }
}
