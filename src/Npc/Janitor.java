package Npc;

import Building.Buffet;
import Building.Building;
import Building.Decoration;
import Building.Entertainer;
import Building.Restaurant;
import Building.Toilet;
import Building.Utility;
import GameEngine.Coordinate;
import GameEngine.GameEngine.Player;
import GameEngine.Map;
import View.BuildingMenu;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class Janitor extends Npc {
    String targetType;
    Building target;
    BuildingMenu menu;
    
    public Janitor(Map map, Player player) throws IOException {
        super(map, player);
        this.position = (new Coordinate(24*40,40));
        this.target = null;
    }
    
    private boolean thereIsPathToTask(Building b){
        if(isTherePathTo(b)){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean hasTarget() {
        // gameEngine addresses task to idle NPC-s
        return this.target != null;
    }
    
    @Override
    public void setTarget(Building target) {
        this.target = target;
    }

    private void goHome(){
        if(target != null && target.getType().equals("Entrance")){
            goToTarget();

        }else{
            target = new Building();
            findPathTo(target);
        }
    }
    
    public void repairTarget(){
        if(hasTarget() && thereIsPathToTask(target)){
            if(path.isEmpty()){
               findPathTo(target);
            }
            this.goToTarget();
        }else{
            System.out.println("Can't do!");
        }
    }
    
    @Override
    public void goToTarget() {
        Building lastBuilding = target;
        if(path.isEmpty() && !(position.x == target.getLocation().getPos().x*40 &&  position.y == target.getLocation().getPos().y*40)) {
            // no available path to destination
            //System.out.println(npc_id+". Say: Bro I cant go to: " + target);
            target = null;
        } else {
            // go to dest
            //System.out.println(npc_id+". Say: I gotta go to " + target);
            if(!(position.x == target.getLocation().getPos().x*40 &&  position.y == target.getLocation().getPos().y*40)) {
                position = path.get(0);
                path.remove(0);
                inBuilding = false;
                //System.out.println("");
                
            } else {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            if(lastBuilding instanceof Entertainer)((Entertainer)lastBuilding).decreaseSlots();
                            if(lastBuilding instanceof Utility)((Utility)lastBuilding).decreaseSlots();
                        } catch (InterruptedException ex) {
                        }
                        if(target instanceof Entertainer){
                            ((Entertainer) target).repair();
                            
                            menu.removeRepairLabel();
                        }
                        goHome();
                        inBuilding = false;
                    }
                };
                if(target.getType().equals("Entrance")){
                    target = null;
                    inBuilding = true;
                }else{
                    thread.start();
                }
            }
        }
    }

    public void setMenu(BuildingMenu menu) {
        this.menu = menu;
    }
}