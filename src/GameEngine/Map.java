// yeyeyeyeye
package GameEngine;

import Building.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    Field[][] fields;
    int[][] pathMap;
    ArrayList<Building> entertainers;
    ArrayList<Building> restaurants; // restaurants + buffets
    ArrayList<Building> toilets;
    ArrayList<Building> trashcans;
    ArrayList<Building> decorations;
    ArrayList<Building> roads;
    
    public Field[][] getFields() {
        return fields;
    }
    
    public int[][] getPathMap() {
        return pathMap;
    }
    
    public Building getBuildingOnCoords(Coordinate pos) {
        return fields[pos.x][pos.y].getBuilding();
    }
    
    public Field getFieldAtCoords(Coordinate pos) {
        return fields[pos.x][pos.y];
    }
    
    public void placeEntrance(Coordinate pos) {
        Building entrance = new Building();
        for(int i = pos.x; i < pos.x + 3; ++i) {
            for(int j = pos.y; j < pos.y + 1; ++j) {
                fields[i][j].setBuilding(entrance);
            }
        }
    }
    
    public Map(int n, int m) {
        fields = new Field[n][m];
        pathMap = new int[n][m];
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < m; ++j) {
                fields[i][j] = new Field(new Coordinate(i, j));
                pathMap[i][j] = 0;
            }
        }
        pathMap[23][0] = 1;
        pathMap[24][0] = 1;
        pathMap[25][0] = 1;
        
        entertainers = new ArrayList<>();
        restaurants = new ArrayList<>();
        toilets = new ArrayList<>();
        trashcans = new ArrayList<>();
        decorations = new ArrayList<>();
        roads = new ArrayList<>();
//        System.out.println("\n\n\n");
//        for(int i = 0; i < pathMap[i].length ; i++){
//            System.out.print("[");
//            for(int j = 0; j < pathMap.length; j++){
//                System.out.print("["+pathMap[j][i] + "]");
//            }
//            System.out.print("]\n");
//        }
    }
    
    void placeBuilding(Coordinate pos, Building building) {
        Coordinate size = building.getSize();
        building.setLocation(fields[pos.x][pos.y]);
        for(int i = pos.x; i < pos.x + size.x; ++i) {
            for(int j = pos.y; j < pos.y + size.y; ++j) {
                fields[i][j].setBuilding(building);
                if(building instanceof Road || building instanceof Entertainer || building instanceof Utility)
                    pathMap[i][j] = 1;
            }
        }
        
        if(building instanceof Entertainer) {
            entertainers.add(building);
        } else if(building instanceof Restaurant || building instanceof Buffet) {
            restaurants.add(building);
        } else if(building instanceof Toilet) {
            toilets.add(building);
        } else if(building instanceof Trashcan) {
            trashcans.add(building);
        } else if(building instanceof Decoration) {
            decorations.add(building);
        } else if(building instanceof Road) {
            roads.add(building);
        }
    }
    
    void freeUp(Coordinate pos) {
        Building building = fields[pos.x][pos.y].getBuilding();
        Coordinate size = building.getSize();
        building.setLocation(null);
        for(int i = pos.x; i < pos.x + size.x; ++i) {
            for(int j = pos.y; j < pos.y + size.y; ++j) {
                fields[i][j].freeUp();
//                if(building instanceof Road)
                    pathMap[i][j] = 0;
            }
        }
        
        if(building instanceof Entertainer) {
            entertainers.remove(building);
        } else if(building instanceof Restaurant || building instanceof Buffet) {
            restaurants.remove(building);
        } else if(building instanceof Toilet) {
            toilets.remove(building);
        } else if(building instanceof Trashcan) {
            trashcans.remove(building);
        } else if(building instanceof Decoration) {
            decorations.remove(building);
        } else if(building instanceof Road) {
            roads.remove(building);
        }
    }
    
    void howerOver(Coordinate pos, Building building) {
        Coordinate size = building.getSize();
        for(int i = pos.x; i < pos.x + size.x; ++i) {
            for(int j = pos.y; j < pos.y + size.y; ++j) {
                fields[i][j].highlight();
            }
        }
    }
    
    public boolean checkIfFree(Coordinate pos, Building building) {
        Coordinate size = building.getSize();
        boolean free = true;
        //System.out.println("checkIfFree running; Starting coordinate: (" + pos.x + "," + pos.y + "); Bulding size: " + building.getSize().x);
        for(int i = pos.x; i < pos.x + size.x; ++i) {
            for(int j = pos.y; j < pos.y + size.y; ++j) {
                //System.out.println("Checking if field (" + i + "," + j + ") is free");
                if(fields[i][j].isTaken) {
                    free = false;
                }
            }
        }
        return free;
    }
    
    public boolean isPlaced(Building building) {
        if(entertainers.contains(building))
            return true;
        if(restaurants.contains(building))
            return true;
        if(toilets.contains(building))
            return true;
        if(trashcans.contains(building))
            return true;
        if(decorations.contains(building))
            return true;
        if(roads.contains(building))
            return true;
        return false;
    }

    public ArrayList<Building> getEntertainers() {
        return entertainers;
    }

    public ArrayList<Building> getRestaurants() {
        return restaurants;
    }

    public ArrayList<Building> getToilets() {
        return toilets;
    }

    public ArrayList<Building> getTrashcans() {
        return trashcans;
    }

    public ArrayList<Building> getDecorations() {
        return decorations;
    }

    public ArrayList<Building> getRoads() {
        return roads;
    }
    
}
