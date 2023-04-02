package Building;

public class Decoration extends Building {
    int range;
    
    public Decoration(String type) {
        super(type);
        range = Integer.parseInt(buildingSpecs[6]);; // read from file
        level = 0;
    }

    public int getRange() {
        return range;
    }
    
}
