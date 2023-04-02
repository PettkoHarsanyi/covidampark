package GameEngine;

import Building.*;

public class Field {
    
    final Coordinate pos;
    boolean isTaken;
    Building takenBy;
    
    public Field() { // for testing only
        this.isTaken = false;
        this.takenBy = null;
        pos = new Coordinate(0, 0);
    } 
    
    public Field(Coordinate pos) {
        this.isTaken = false;
        this.takenBy = null;
        this.pos = pos;
    }
    
    public Field(int x, int y) {
        this.isTaken = false;
        this.takenBy = null;
        this.pos = new Coordinate(x, y);
    }
    
    public boolean isTaken() {
        return isTaken;
    }
    
    public Coordinate getPos(){
        return pos;
    }
    
    public void setBuilding(Building building) {
        if(!isTaken) {
            isTaken = true;
            takenBy = building;
        }
    }
    
    public Building getBuilding() {
        return takenBy;
    }
    
    public void freeUp() {
        isTaken = false;
        takenBy = null;
    }
    
    
    public void highlight() {
        if(!isTaken) {
            
        } else {
            
        }
    }
    
}
