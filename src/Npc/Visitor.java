package Npc;

import Building.Buffet;
import Building.Building;
import Building.Decoration;
import Building.Entertainer;
import Building.Restaurant;
import Building.Toilet;
import Building.Utility;
import Building.Road;
import GameEngine.Coordinate;
import GameEngine.GameEngine.Player;
import GameEngine.Map;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class Visitor extends Npc {
    int happiness; // 0-100
    int bladder; // 0-100
    int saturation; // opposite of hunger, 0-100
    int decorEffect; // 0-15
    Map map;
    ArrayList<Building> targetBuildings;
    String targetType;
    
    public Visitor(Map map, Player player) throws IOException {
        super(map, player);
        this.happiness = 80;
        this.bladder = 80;
        this.saturation = 80;
        this.decorEffect = 0;
        this.position = (new Coordinate(24*40,40)); // might bite us in the ass later
        this.map = map;
        this.isLeaving = false;
        this.targetBuildings = new ArrayList<>();
        player.increaseMoney(15);
    }
    
    public boolean hasTarget() {
        // gameEngine addresses task to idle NPC-s
        return target != null;
    }
    
    public void refreshStats() {
//        calculateDecorEffect();
//        if(decorEffect <= 8) {
//            happiness = Math.max(happiness - 1, 0);
//        }
        calculateDecorEffect();
        if(decorEffect < 10)
            happiness = Math.max(happiness - 1, 0);
        bladder = Math.max(bladder - 1, 0);
        saturation = Math.max(saturation - 1, 0);
        
        Random rand = new Random();
        if(rand.nextInt(10) > 7) {
            produceTrash();
        } 
        
        //System.out.println("[STATS: decorEffect: " + decorEffect + ", bladder: " + bladder + ", saturation: " + saturation + ", happiness: " + happiness + "]");
        /**
         * decrease happiness, bladder and saturation by given amount
         * decrease decor effect
         * increase happiness according to decorations in range
         * overall it should decrease even if surrounded by the best decorations
         * might have to replace int with double
         */
    }
    
    private void calculateDecorEffect() {
        Coordinate myCoords = new Coordinate((int)position.x/40, (int)position.y/40);
        if(map.getBuildingOnCoords(myCoords) instanceof Road && map.getBuildingOnCoords(myCoords) != null) {
            Road mySpot = ((Road)map.getBuildingOnCoords(myCoords));
            if(mySpot.getDirtiness() > mySpot.getMaxDirtiness() - 10) {
                decorEffect = Math.max(decorEffect - 2, 0);
            } else {
                boolean inRange = false;
                for(Building b : map.getDecorations()) {
                    if(getLinearDistance(b) <= ((Decoration)b).getRange()) {
                        decorEffect =  Math.min(decorEffect + b.getFunnes(), 15);
                        inRange = true;
                    }
                }
                if(!inRange) decorEffect = Math.max(decorEffect - 1, 0);
            } 
        }
    }
    
    private void produceTrash() {
        boolean inRange = false;
        for(Building b : map.getTrashcans()) {
            if(getLinearDistance(b) <= 5) {
                inRange = true;
                break;
            }
        }
        if(!inRange) {
            Coordinate myCoords = new Coordinate((int)position.x/40, (int)position.y/40);
            if(map.getBuildingOnCoords(myCoords) instanceof Road && map.getBuildingOnCoords(myCoords) != null) {
                ((Road)map.getBuildingOnCoords(myCoords)).increaseDirtiness();
            }
        }
    }
    
    private boolean thereIsPathToFun(){
        for(Building b: map.getEntertainers()){
            if(b instanceof Entertainer && isTherePathTo(b)){
                return true;
            }
        }        
        return false;
    }

    public boolean IsAlive() {
        return isAlive;
    }
    
    public void doYourThing() {
//        if(happiness < 10 || bladder < 10 || saturation < 10 || noMoreFunHere()) {
//            leavePark();
//        } else {
        if(!inBuilding){                                                                                // HA NINCS ÉPÜLETBEN, AKKOR TÖRTÉNIK AKÁRMI
            refreshStats();                                                                             //  pl: refreshstats
            if(happiness > 10 && bladder > 10 && saturation > 10){                                      //  HA KISEBB A HAPPINESS MINT 10 VAGY NINCS ENTERTAINER AKKOR HAZAMEGY KÜLÖNBEN: 
                if(bladder > 30 && saturation > 30){                                                    //      HA TÖBB A HOLYAG ÉS A TELITETTSÉG MINT 30


                    if(target != null && map.isPlaced(target)){                                   //          ÉS VAN TARGETJE ÉS AZ MÉG LÉTEZIK
                        goToTarget();                                                                   //              AKKOR ODAMEGY
                    }else{                                                                              //          KÜLÖNBEN
                        targetBuildings.clear();                                        
                        if(isThereEntertainer() && thereIsPathToFun()){
                            for(Building b : map.getEntertainers()){                                    
                                if(b instanceof Entertainer && !getEntertainerUnderMaintenece(b)/* && isFree(b)*/ ){
                                    targetBuildings.add(b);                                                 //              TARGETBUILDINGSBE RAKJA A SZÓBA JÖHETŐ ÉPÜLETEKET
                                }
                            }
                            chooseTarget(targetBuildings);
                        }else{
                            goHome();
                        }
                    }
                }else{                                                                                  //      HA KEVESEBB A HOLYAG ÉS A TELITETTSÉG MINT 50
                    if(target != null && map.isPlaced(target) && target instanceof Utility /*&& isFree(target)*/){      //          HA VAN TARGETJE ÉS AZ MÉG LÉTEZIK ÉS AZ UTILITY
                        goToTarget();                                                                   //              AKKOR ODAMEGY
                    }else{                                                                              //          KÜLÖNBEN UJAT VÁLASZT
                        targetBuildings.clear();

                        if(bladder <= saturation){                                                      //          HA PISILNIE KELL ÉS ÉHES IS AKKOR ELŐSZÖR PISI
                            if(!map.getToilets().isEmpty()){
                                for(Building b : map.getToilets()){
                                    if(b.getType().equals("Toilet")){
                                        targetBuildings.add(b);
                                    }
                                }
                                chooseTarget(targetBuildings);
                            }else{
                                goHome();
                            }
                        }else{
                            if(!map.getRestaurants().isEmpty()){
                                for(Building b : map.getRestaurants()){
                                    if(b.getType().equals("Buffet") || b.getType().equals("Restaurant")){
                                        targetBuildings.add(b);
                                    }
                                }
                                chooseTarget(targetBuildings);
                            }else{
                                goHome();
                            }
                            
                        }
                    }
                }
            }else{
                goHome();
            }
        }
//        System.out.println("[Bladder: " + this.bladder + " | Saturation: " + this.saturation + "]");
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
            } else {
                if(target instanceof Entertainer){
                    getDecrease(target);
                    inBuilding = true;
                    ((Entertainer)target).increaseSlots();

                    interactWith(target);
                    player.setMoney(player.getMoney()+(int)(target.getPrice()*0.01));
                }else{
                    inBuilding = true;
                    interactWith(target);
                    if(target instanceof Utility) ((Utility)target).increaseSlots();
                    player.setMoney(player.getMoney()+(int)(target.getPrice()*0.01));

                }
//                if(isLeaving){
//                    isAlive = false;
//                    visible = false;
//                }else{
//                    interactWith(target);
//                }

                
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            
                            Thread.sleep(2000);
                            if(lastBuilding instanceof Entertainer)((Entertainer)lastBuilding).decreaseSlots();
                            if(lastBuilding instanceof Utility)((Utility)lastBuilding).decreaseSlots();
                            
                        } catch (InterruptedException ex) {
                        }
                        
                      
                        target = null;
                        inBuilding = false;
                        
                    }
                    
                };
                if(target.getType().equals("Entrance")){
                    target = null;
                    inBuilding = true;
                    isAlive = false;
                }else{
                    thread.start();
                    
                }
            }
        }
    }
    
    public boolean isFree(Building b) {
        if(((Entertainer)b).getUserSlots() < ((Entertainer)b).getSlots()) {
            return true;
        }else if(((Utility)b).getUserSlots() < ((Entertainer)b).getSlots()){
            return true;
        }else return false;
        
    }
    
    private void goHome(){
        if(target != null && target.getType().equals("Entrance")){
            goToTarget();

        }else{
            target = new Building();
            findPathTo(target);
        }
    }
    
    private boolean isThereEntertainer(){
        if(map.getEntertainers().isEmpty()) {
            return false;
        } else {
            for(Building b : map.getEntertainers()) {
                if(!getEntertainerUnderMaintenece(b)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public void chooseTarget(ArrayList<Building> buildings) {
        
        
//        do{
            Random rand = new Random();
            int ind = rand.nextInt(buildings.size());
               
            target = buildings.get(ind);
            findPathTo(target);
//        }while(!isTherePathTo(target, roads));
        
        
        // choose type of target
//        path.clear();
//        if(!buildings.isEmpty()) {
//            Random rand = new Random();
//            if(bladder < 35 && isThereAToiletHere()) {
//                while(target == null) {
//                    int ind = rand.nextInt(buildings.size());
//                    if(buildings.get(ind).getType().equals("Toilet")) {
//                        target = buildings.get(ind);
//                        findPathTo(target.getLocation().getPos(), roads);
//                    }
//                }
//            } else if(saturation < 35 && isThereARestaurantHere()) {
//                 while(target == null) {
//                    int ind = rand.nextInt(buildings.size());
//                    if(buildings.get(ind).getType().equals("Restaurant") || buildings.get(ind).getType().equals("Buffet")) {
//                        target = buildings.get(ind);
//                        findPathTo(target.getLocation().getPos(), roads);
//                    }
//                }
//            } else {
//                boolean isThereEntertainer = false;
//                int i = 0;
//                while(!isThereEntertainer && i < buildings.size()){
//                    if(buildings.get(i) instanceof Entertainer){
//                        isThereEntertainer = true;
//                    }
//                    i++;
//                }
//                                
//                if(isThereEntertainer){
//                    while(target == null) {
//                       int ind = rand.nextInt(buildings.size());
//                       if(buildings.get(ind) instanceof Entertainer) {
//                           target = buildings.get(ind);
//                           findPathTo(target.getLocation().getPos(), roads);
//                       }
//                    }
//                }else{
//                    target = null;
//                }
//                // should choose between short and long duration games based on happiness in relation to other metrics
//            }
//        }
    }
    
    @Override
    public void interactWith(Building building) {
        if(target.getType().equals("Toilet")){
            bladder = 100;
        }else if(target.getType().equals("Restaurant")){
            saturation = 100;
        }else if(target.getType().equals("Buffet")){
            saturation+= 60;
        }

    }
    
    
    
    
//    public void leavePark(){
//        if(isLeaving == false){
//            isLeaving = true;
//            target = new Building();
//            buildings.add(target);
//            findPathTo(target.getLocation().getPos(), roads);
//        }
//    }

}
