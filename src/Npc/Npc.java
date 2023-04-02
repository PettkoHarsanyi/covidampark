package Npc;

import Building.Building;
import Building.Entertainer;
import Building.Utility;
import GameEngine.Coordinate;
import GameEngine.GameEngine.Player;
import GameEngine.Map;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Npc {
    
    String type;
    boolean visible;
    Coordinate position;
    int randomOffset;
    Map map;
    ArrayList<Coordinate> path;
    Building target;
    BufferedImage img;
    Player player;
    boolean inBuilding;
    boolean isLeaving;
    boolean isAlive;
    boolean moving;
    
    public Npc(Map map, Player player) throws IOException {
        this.map = map;
        this.path = new ArrayList<>();
        this.player = player;
        this.isAlive = true;
        this.visible = true;
        this.target = null;

        Random rand = new Random();
        randomOffset = rand.nextInt(22);
        
        int ind = rand.nextInt(2);

        if(this instanceof Janitor){
            this.img = ImageIO.read(new File("src/GFX/maintainer.png"));
        } else if(this instanceof Cleaner) {
            this.img = ImageIO.read(new File("src/GFX/CleanerModel.png"));
        } else {
            if(ind == 0){
                this.img = ImageIO.read(new File("src/GFX/boy.png"));
            }else{
                this.img = ImageIO.read(new File("src/GFX/girl.png"));
            }
        }
    }

    public BufferedImage getImage() {
        return img;
    }    

    public Coordinate getPosition() {
        return position;
    }
    
      public Building getTarget() {
        return target;
    }

    public ArrayList<Coordinate> getPath() {
        return path;
    }

    public boolean isVisible() {
        return !inBuilding;
    }
    
    public int getOffset() {
        return randomOffset;
    }
    
    
    protected boolean isTherePathTo(Building b) {
        ArrayList<Coordinate> path2 = new ArrayList<>();
        
        int[][] pathMap = new int[map.getPathMap().length][map.getPathMap()[0].length];
        for(int i = 0; i < map.getPathMap().length; ++i) {
            for(int j = 0; j < map.getPathMap()[i].length; ++j) {
                pathMap[i][j] = map.getPathMap()[i][j];
            }
        }
        
        Coordinate targetCoord = b.getLocationAsCoords();
        Coordinate targetSize = b.getSize();
        
        for(int i = 0; i < targetSize.x; ++i) {
            for(int j = 0; j < targetSize.y; ++j) {
                pathMap[targetCoord.x+i][targetCoord.y+j] = 1;
            }
        }
        
        Node startNode = new Node(position.x/40, position.y/40, null);
        Node endNode = new Node(b.getLocationAsCoords().x, b.getLocationAsCoords().y, null);
        Node current = startNode;

        
        while(!current.equals(endNode)) {
            pathMap[current.x][current.y] = 2;
            
            
            if(current.x < pathMap.length-1 && pathMap[current.x+1][current.y] == 1) {                          // JOBBRA
                current = new Node(current.x+1, current.y, current);
            } else if(current.y < pathMap[current.x].length-1 && pathMap[current.x][current.y+1] == 1) {        // LE
                current = new Node(current.x, current.y+1, current);
            } else if(current.x > 0 && pathMap[current.x-1][current.y] == 1) {                                  // BALRA
                current = new Node(current.x-1, current.y, current);
            } else if(current.y > 0 && pathMap[current.x][current.y-1] == 1) {                                  // FEL
                current = new Node(current.x, current.y-1, current);
            } else {
                if(current.equals(startNode)) {
                    break;
                } else {
                    current = current.prev;


                }
            }
        }
        
        while(!current.equals(startNode)) {
            path2.add(new Coordinate(current.x*40, current.y*40));
            current = current.prev;
        }
        

        path2.add(new Coordinate(current.x*40, current.y*40));
        Collections.reverse(path2);
        
        boolean pathFound = path2.size() != 1 || (b.getLocation().getPos().x == position.x/40 && b.getLocation().getPos().y == position.y/40);
        if(path2.size() == 1 && !(b.getLocation().getPos().x == position.x/40 && b.getLocation().getPos().y == position.y/40))
            pathFound = false;
        
        return pathFound;
    }
    
    public void findPathTo(Building b){
//        System.out.println("\n\n\n");
//        for(int i = 0; i < map[i].length ; i++){
//            System.out.print("[");
//            for(int j = 0; j < map.length; j++){
//                System.out.print("["+map[j][i] + "]");
//            }
//            System.out.print("]\n");
//        }
        //System.out.println("TARGET: " + b.getType());
        path.clear();
        int[][] pathMap = new int[map.getPathMap().length][map.getPathMap()[0].length];
        for(int i = 0; i < map.getPathMap().length; ++i) {
            for(int j = 0; j < map.getPathMap()[i].length; ++j) {
                pathMap[i][j] = map.getPathMap()[i][j];
            }
        }
        
        Coordinate targetCoord = b.getLocationAsCoords();
        Coordinate targetSize = b.getSize();
        
        for(int i = 0; i < targetSize.x; ++i) {
            for(int j = 0; j < targetSize.y; ++j) {
                pathMap[targetCoord.x+i][targetCoord.y+j] = 1;
            }
        }
        
        Node startNode = new Node(position.x/40, position.y/40, null);
        Node endNode = new Node(b.getLocation().getPos().x, b.getLocation().getPos().y, null);
        Node current = startNode;

        
        
        
        while(!current.equals(endNode)) {
            pathMap[current.x][current.y] = 2;
            
            if(endNode.x < current.x && endNode.y < current.y){
                if(current.y > 0 && pathMap[current.x][current.y-1] == 1) {                                  // FEL
                    current = new Node(current.x, current.y-1, current);
                } else if(current.x > 0 && pathMap[current.x-1][current.y] == 1) {                                  // BALRA
                    current = new Node(current.x-1, current.y, current);
                } else if(current.x < pathMap.length-1 && pathMap[current.x+1][current.y] == 1) {                          // JOBBRA
                    current = new Node(current.x+1, current.y, current);
                }else if(current.y < pathMap[current.x].length-1 && pathMap[current.x][current.y+1] == 1) {        // LE
                    current = new Node(current.x, current.y+1, current);
                } else {
                    if(current.equals(startNode)) {
                        break;
                    } else {
                        current = current.prev;


                    }
                }
            }else if(current.x < endNode.x && endNode.y < current.y){
                if(current.y > 0 && pathMap[current.x][current.y-1] == 1) {                                  // FEL
                    current = new Node(current.x, current.y-1, current);
                } else if(current.x < pathMap.length-1 && pathMap[current.x+1][current.y] == 1) {                          // JOBBRA
                    current = new Node(current.x+1, current.y, current);
                }else if(current.x > 0 && pathMap[current.x-1][current.y] == 1) {                                  // BALRA
                    current = new Node(current.x-1, current.y, current);
                }else if(current.y < pathMap[current.x].length-1 && pathMap[current.x][current.y+1] == 1) {        // LE
                    current = new Node(current.x, current.y+1, current);
                } else {
                    if(current.equals(startNode)) {
                        break;
                    } else {
                        current = current.prev;


                    }
                }
            }else if(current.x < endNode.x && current.y < endNode.y){
                if(current.y < pathMap[current.x].length-1 && pathMap[current.x][current.y+1] == 1) {        // LE
                    current = new Node(current.x, current.y+1, current);
                } else if(current.x < pathMap.length-1 && pathMap[current.x+1][current.y] == 1) {                          // JOBBRA
                    current = new Node(current.x+1, current.y, current);
                } else if(current.x > 0 && pathMap[current.x-1][current.y] == 1) {                                  // BALRA
                    current = new Node(current.x-1, current.y, current);
                } else if(current.y > 0 && pathMap[current.x][current.y-1] == 1) {                                  // FEL
                    current = new Node(current.x, current.y-1, current);
                } else {
                    if(current.equals(startNode)) {
                        break;
                    } else {
                        current = current.prev;


                    }
                }
            }else{
                if(current.y < pathMap[current.x].length-1 && pathMap[current.x][current.y+1] == 1) {        // LE
                    current = new Node(current.x, current.y+1, current);
                } else if(current.x > 0 && pathMap[current.x-1][current.y] == 1) {                                  // BALRA
                    current = new Node(current.x-1, current.y, current);
                } else if(current.x < pathMap.length-1 && pathMap[current.x+1][current.y] == 1) {                          // JOBBRA
                    current = new Node(current.x+1, current.y, current);
                }else if(current.y > 0 && pathMap[current.x][current.y-1] == 1) {                                  // FEL
                    current = new Node(current.x, current.y-1, current);
                } else {
                    if(current.equals(startNode)) {
                        break;
                    } else {
                        current = current.prev;


                    }
                }
            }
        }

        while(!current.equals(startNode)) {
            path.add(new Coordinate(current.x*40, current.y*40));
            current = current.prev;
        }

        path.add(new Coordinate(current.x*40, current.y*40));
        Collections.reverse(path);
    }
    
    public void goToTarget() {}
    
    public void interactWith(Building building) {}
    
    protected int getLinearDistance(Building building) {
        Coordinate buildingCoords = building.getLocationAsCoords();
        Coordinate myCoords = new Coordinate((int)position.x/40, (int)position.y/40);
        return (int) ( Math.sqrt( sqr( (buildingCoords.x-myCoords.x) ) + sqr( (buildingCoords.y-myCoords.y) ) ) );
    }
    
    private int sqr(int number) {
        return number*number;
    }
    
    void getDecrease(Building p) {
        if(p instanceof Entertainer) ((Entertainer)p).decreaseCondition();
    }
    
    public boolean getEntertainerUnderMaintenece(Building p) {
        if(p instanceof Entertainer) return ((Entertainer)p).isUnderMaintenece();
        else return false;
    }
    
    public boolean hasTarget() {
        // gameEngine addresses task to idle NPC-s
        return target != null;
    }

    public void setTarget(Building target) {
        this.target = target;
    }
    
    class Node {

        public int x;
        public int y;

        public Node prev;

        public Node(int x, int y, Node prev) {
            this.x = x;
            this.y = y;
            this.prev = prev;
        }

        public boolean equals(Node other) {
            return this.x == other.x && this.y == other.y;
        }

    }
    
}
