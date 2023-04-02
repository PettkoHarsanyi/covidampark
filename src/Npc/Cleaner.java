package Npc;

import Building.Building;
import Building.Road;
import GameEngine.Coordinate;
import GameEngine.GameEngine.Player;
import GameEngine.Map;
import java.io.IOException;
import java.util.Random;

public class Cleaner extends Npc {
    
    private boolean idle;
    
    public Cleaner(Map map, Player player) throws IOException {
        super(map, player);
        position = (new Coordinate(24*40,40));
        target = null;
        idle = true;
    }
    
    public void doYourThing() {
        if(idle) {
            for(Building b : map.getRoads()) {
                if(((Road)b).getDirtiness() >  15) {
                    chooseTarget();
                }
            }   
        }
        
        if(target != null && map.isPlaced(target)) {
            if(isTherePathTo(target)) {
                if(path.isEmpty())
                    findPathTo(target);
                goToTarget();
            } else {
                target = null;
            }
        } else {
            chooseTarget();
        }
    }
    
    private void chooseTarget() {
        boolean dirtyRoadExists = false;
        int closestDistance = 100000;
        Building tempTarget = null;
        for(Building b : map.getRoads()) {
            if(getLinearDistance(b) < closestDistance && ((Road)b).getDirtiness() > 15) {
                
                tempTarget = b;
                closestDistance = getLinearDistance(tempTarget);
            }
        }
        target = tempTarget;
        
        if(target!=null){
            dirtyRoadExists = true;
            idle = false;
        }
        
        if(!dirtyRoadExists) {
            Random rand = new Random();
            if(map.getRoads().size()>0){
                int number = rand.nextInt(map.getRoads().size());
                target = map.getRoads().get(number);
            }
            idle = true;
        }
    }
    
    @Override
    public void goToTarget() {
        Building lastTarget = target;
        if(path.isEmpty() && !(position.x == target.getLocation().getPos().x*40 &&  position.y == target.getLocation().getPos().y*40)) {
            // no available path to destination
            target = null;
        } else {
            if(!(position.x == target.getLocation().getPos().x*40 &&  position.y == target.getLocation().getPos().y*40)) {
                position = path.get(0);
                path.remove(0);
            } else {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {}
                        if(lastTarget != null && map.isPlaced(lastTarget))
                            cleanRoad(lastTarget);
                    }
                };
                thread.start();
            }
        }
    }
    
    private void cleanRoad(Building building) {
        ((Road)building).clean();
        target = null;
        idle = true;
    }
    
}
