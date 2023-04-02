package Building;

import GameEngine.*;

public class SimpleRoad extends Road{
    
    public SimpleRoad() {
        super("SimpleRoad");
//        buildingPrice = 10;
//        dirtiness = 0;
//        funnes = 0;
//        level = 0;
//        location = null;
//        Coordinate _size = new Coordinate(1,1);
//        size = _size;
//        type = "SimpleRoad";
//        upgradePrice = 0;
    }
    
    public SimpleRoad(Field location){
        super("SimpleRoad");
        this.location = location;   //-ez kell
        
        
        
//        buildingPrice = 10;
//        dirtiness = 0;
//        funnes = 0;
//        level = 0;
//        Coordinate _size = new Coordinate(1,1);
//        size = _size;
//        type = "SimpleRoad";
//        upgradePrice = 0;
    }
}
