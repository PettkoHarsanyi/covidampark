package GameEngine;

import Building.*;
import Npc.*;
import View.BuildingMenu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEngine {
    Player player;
    Map map;
    int parkRating;
    ArrayList<Building> buildings; // includes roads too, used for rendering objects
    ArrayList<Visitor> visitors;
    Building selectedBuilding; // When purchasing from hud, this gets the value form the "name" of the exact panel.
                               // When clicking on map after purchasing, this becomes null
    ArrayList<Building> interactiveBuildings;
    ArrayList<Janitor> janitors;
    ArrayList<Cleaner> cleaners;
    
    // push test
    boolean parkOpen;
    
    public GameEngine() {
        player = new Player(10000);
        map = new Map(48, 27);
        buildings = new ArrayList<>();
        interactiveBuildings = new ArrayList<>();
        visitors = new ArrayList<>();
        janitors = new ArrayList<>();
        cleaners = new ArrayList<>();
    }

    public void setParkOpen(boolean parkOpen) {
        this.parkOpen = parkOpen;        
//                for(Building b : buildings){
//                    System.out.println("MY SELECTED BUILDING POSX: " + b.getLocation().getPos().x);
//                }
//        if(parkOpen){
//            createVisitor();
//        }
    }
    
    public boolean isOpen(){
        return this.parkOpen;
    }
    
    public void createVisitor() throws IOException{
        visitors.add(new Visitor(map, player));
        
    }
    
    public void createJanitor() throws IOException{
        janitors.add(new Janitor(map, player));
    }
    
    public void janitorAddTask(Building target,BuildingMenu menu){
        for(Janitor j : janitors){
            if(!j.hasTarget()){
                j.setMenu(menu);
                j.setTarget(target);
                break;
            }else{
                System.out.println(j+" is buisy!");
            }
        }
    }
    
    public int getJanitorsSize(){
    return janitors.size();
    }

    public boolean isParkOpen() {
        return parkOpen;
    }

    public void setSelectedBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }
    
    public Building getSelectedBuilding(){
        return selectedBuilding;
    }

    public Map getMap() {
        return map;
    }
    
    public Player getPlayer(){
        return player;
    }

    public ArrayList<Cleaner> getCleaners() {
        return cleaners;
    }

    public ArrayList<Janitor> getJanitors() {
        return janitors;
    }
    
    public String playerMoney(){
        return String.valueOf(player.getMoney());
    }
    
    public int getPlayerMoney(){
        return player.getMoney();
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }
    
    public void decreasePlayerMoney(int tmp){
        player.decreaseMoney(tmp);
    }
    
    public ArrayList<Building> getBuildings(){
        return buildings;
    }

    public ArrayList<Building> getInteractiveBuildings() {
        return interactiveBuildings;
    }

    public void addInteractiveBuildings(Building building) {
        this.interactiveBuildings.add(building);
    }
    
    
    public void addBuilding(Building b){
        this.buildings.add(b);
    }
    
    public void selectBuildingType(String text) {
        switch(text) {
            case "RollerCoaster":
                if(player.getMoney() >= new RollerCoaster().getPrice()) {
                    //System.out.println("Roller Coaster purchased");
                    selectedBuilding = new RollerCoaster();
                } else {
                    //System.out.println("Not enough money");
                }
                break;
            case "SlowCoaster":
                if(player.getMoney() >= new SlowCoaster().getPrice())
                    selectedBuilding = new SlowCoaster();
                else {
                    
                }
                break;
            case "SmolCoaster":
                if(player.getMoney() >= new SmolCoaster().getPrice())
                    selectedBuilding = new SmolCoaster();
                else {
                    
                }
                break;
            case "KidsCoaster":
                if(player.getMoney() >= new KidsCoaster().getPrice())
                    selectedBuilding = new KidsCoaster();
                else {
                    
                }
                break;
            case "DropTower":
                if(player.getMoney() >= new DropTower().getPrice())
                    selectedBuilding = new DropTower();
                else {
                    
                }
                break;
            case "GoCart":
                if(player.getMoney() >= new GoCart().getPrice())
                    selectedBuilding = new GoCart();
                    
                else {
                    
                }
                break;
            case "PirateShip":
                if(player.getMoney() >= new PirateShip().getPrice())
                    selectedBuilding = new PirateShip();
                else {
                    
                }
                break;
            case "SlidePark":
                if(player.getMoney() >= new SlidePark().getPrice())
                    selectedBuilding = new SlidePark();
                else {
                    
                }
                break;
            case "FerisWheel":
                if(player.getMoney() >= new FerisWheel().getPrice())
                    selectedBuilding = new FerisWheel();
                else {
                    
                }
                break;
            case "DuckHunt":
                if(player.getMoney() >= new DuckHunt().getPrice())
                    selectedBuilding = new DuckHunt();
                else {
                    
                }
                break;
            case "Dodgem":
                if(player.getMoney() >= new Dodgem().getPrice())
                    selectedBuilding = new Dodgem();
                else {
                    
                }
                break;
            case "Carousel":
                if(player.getMoney() >= new Carousel().getPrice())
                    selectedBuilding = new Carousel();
                else {
                    
                }
                break;
            case "HauntedCastle":
                if(player.getMoney() >= new HauntedCastle().getPrice())
                    selectedBuilding = new HauntedCastle();
                else {
                    
                }
                break;
            case "Toilet":
                if(player.getMoney() >= new Toilet().getPrice())
                    selectedBuilding = new Toilet();
                else {
                    
                }
                break;
            case "Buffet":
                if(player.getMoney() >= new Buffet().getPrice())
                    selectedBuilding = new Buffet();
                else {
                    
                }
                break;
            case "Restaurant":
                if(player.getMoney() >= new Restaurant().getPrice())
                    selectedBuilding = new Restaurant();
                else {
                    
                }
                break;
            case "BallPit":
                if(player.getMoney() >= new BallPit().getPrice())
                    selectedBuilding = new BallPit();
                else {
                    
                }
                break;
            case "LargeBush":
                if(player.money >= new LargeBush().getPrice())
                    selectedBuilding = new LargeBush();
                else {
                    
                }
                break;
            case "Flowers":
                if(player.getMoney() >= new Flowers().getPrice())
                    selectedBuilding = new Flowers();
                else {
                    
                }
                break;
            case "BigRock":
                if(player.getMoney() >= new BigRock().getPrice())
                    selectedBuilding = new BigRock();
                else {
                    
                }
                break;
            case "SimpleRoad":
                if(player.getMoney() >= new SimpleRoad().getPrice())
                    selectedBuilding = new SimpleRoad();
                else {
                    
                }
                break;
            case "FancyRoad":
                if(player.getMoney() >= new FancyRoad().getPrice())
                    selectedBuilding = new FancyRoad();
                else {
                    
                }
                break;
            case "NiceTree":
                if(player.getMoney() >= new NiceTree().getPrice())
                    selectedBuilding = new NiceTree();
                else {
                    
                }
                break;
            case "Trashcan":
                if(player.getMoney() >= new Trashcan().getPrice())
                    selectedBuilding = new Trashcan();
                else {
                    
                }
                break;
            case "SmallBush":
                if(player.getMoney() >= new SmallBush().getPrice())
                    selectedBuilding = new SmallBush();
                else {
                    
                }
                break;
            case "FunnyStatue":
                if(player.getMoney() >= new FunnyStatue().getPrice())
                    selectedBuilding = new FunnyStatue();
                else {
                    
                }
                break;
            default:
                // throw exception
                break;
        }
    }
    
    public void placeBuilding(Coordinate pos) { // called on clicking on a field
        if(map.checkIfFree(pos, selectedBuilding)) {
            map.placeBuilding(pos, selectedBuilding);
            player.decreaseMoney(selectedBuilding.getPrice());
            buildings.add(selectedBuilding);
            if(selectedBuilding instanceof Entertainer || selectedBuilding instanceof Utility || selectedBuilding instanceof Decoration){
               interactiveBuildings.add(selectedBuilding);
            }
            selectedBuilding = null;  
        }
    }
    
    public void placeRoad(Road road) { // called on dragging over a field
        if(map.checkIfFree(road.getLocation().getPos(), road) && player.getMoney() >= road.getPrice()) {
            buildings.add(road);
            map.placeBuilding(road.getLocation().getPos(), road);
            player.decreaseMoney(road.getPrice());
        }
    }
    
    public void sellBuilding(Coordinate pos) {
        if(map.getFieldAtCoords(pos).isTaken){
        selectedBuilding = map.getFieldAtCoords(pos).getBuilding();
        int k = (int)((selectedBuilding.getPrice())*(60/100.0f));
        player.increaseMoney(k);
        
        map.freeUp(selectedBuilding.getLocation().getPos());
        buildings.remove(selectedBuilding);
        if(interactiveBuildings.contains(selectedBuilding)){
            interactiveBuildings.remove(selectedBuilding);
        }
        selectedBuilding = null;
        }
    }
    
    public void upgradeBuilding(Building building) {
        if(player.getMoney() >= building.getUpgradePrice() && building.getLevel() < 3){
            player.decreaseMoney(building.getUpgradePrice());
            building.upgrade();
        }
    }
    
    public class Player {
        
        private int money;
        
        public Player(int money) {
            this.money = money;
        }
        
        public int getMoney(){
            return this.money;
        }
        
        public void setMoney(int money){
            this.money = money;
        }
        
        public void decreaseMoney(int amount) {
            money -= amount;
        }
        
        public void increaseMoney(int amount) {
            money += amount;
        }
    }
    
    public void hoverOn(int x, int y){
        if(selectedBuilding != null){
            selectedBuilding.setLocation(new Field(x,y));
            //System.out.println("i've set location to " + x + "," + y);
        }
    }
    
    public boolean hasSelected(){
        return selectedBuilding != null;
    }
    
    public void ratePark(){
        
        int numOfEntertainer=0;
        int numOfUtility=0;
        int numOfDecoration=0;
        
        if(buildings.isEmpty()){
            parkRating=0;
        }else{
            for(Building b : buildings){
                if(b instanceof Entertainer){
                    numOfEntertainer += 1;
                }else if(b instanceof Utility){
                    numOfUtility += 1;
                }else if(b instanceof Decoration){
                    numOfDecoration += 1;
                }
            }
            
            if(numOfEntertainer > 16  && numOfUtility > 10 && numOfDecoration > 12){
                parkRating = 2;
            }else if(numOfEntertainer > 8 && numOfUtility > 5 && numOfDecoration > 8){
                parkRating = 4;
            }else if(numOfEntertainer > 4 && numOfUtility > 3 && numOfDecoration > 0){
                parkRating = 6;
            }else if(numOfEntertainer > 0 && numOfUtility > 0){
                parkRating = 10;
            }
        
            System.out.println("Park Rated: " + parkRating);

        }
    }

    public int getParkRating() {
        return parkRating;
    }
}

