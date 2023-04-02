package Building;

import GameEngine.*;

public class FancyRoad extends Road{
    
    public FancyRoad() {
        super("FancyRoad");
//        buildingPrice = 40;
//        dirtiness = 0;
//        funnes = 1;
//        level = 0;
//        location = null;
//        Coordinate _size = new Coordinate(1,1);
//        size = _size;
//        type = "FancyRoad";
//        upgradePrice = 0;
    }
    
    public FancyRoad(Field location){
        super("FancyRoad");
        this.location = location;       //-ez kell
//        buildingPrice = 40;
//        dirtiness = 0;
//        funnes = 1;
//        level = 0;
//        Coordinate _size = new Coordinate(1,1);
//        size = _size;
//        type = "FancyRoad";
//        upgradePrice = 0;
    }
}
