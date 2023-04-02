package Building;

import GameEngine.Coordinate;
import GameEngine.Field;
import GameEngine.GameEngine;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class Building {
    
    protected Scanner scanner;
    protected String buildingSpecs[];
            
    String type;
    int funnes; //entertainment_meter
    int buildingPrice;
    int upgradePrice;
    int level;
    Field location;
    Coordinate size;
    BufferedImage img;

    //BufferedImage image;
    
    public Building(String type) {
        boolean l = false;
        try {
            scanner = new Scanner(new File("src/GFX/buildingStats.txt"));
            while(scanner.hasNextLine() && !l){
                String line = scanner.nextLine();

                if(line.split("\t")[0].equals(type)){
                    l = true;
                    buildingSpecs = new String[line.split("\t").length];
                    for(int i = 0; i < line.split("\t").length; i++){
                        buildingSpecs[i] = line.split("\t")[i];
                    }
                }
            }
        }catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        //read from file
        this.type = buildingSpecs[0];
        buildingPrice = Integer.parseInt(buildingSpecs[1]);
        upgradePrice = Integer.parseInt(buildingSpecs[2]);
        level = 1;
        location = null;
        funnes = Integer.parseInt(buildingSpecs[3]);
        size = new Coordinate(Integer.parseInt(buildingSpecs[4]),Integer.parseInt(buildingSpecs[5]));
        try{
            img = ImageIO.read(new File("src/GFX/" + this.getType() + ".png"));
        }catch(Exception e){

        }
    }

    public Building(){
        this.type = "Entrance";
        size = new Coordinate(1,1);
        location = new Field(24,0);
        try{
            img = ImageIO.read(new File("src/GFX/" + this.getType() + ".png"));
        }catch(Exception e){

        }
    }
    
    public int getPrice() {
        return buildingPrice;
    }
    
    public String getType(){
        return type;
    }

    public Coordinate getSize() {
        return size;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }
    
    public void setSize(Coordinate size){
        this.size = size;
    }
    
    public void setLocation(Field field) {
        location = field;
    }
    
    public Field getLocation() {
        return location;
    }
    
    public Coordinate getLocationAsCoords() {
        return location.getPos();
    }

    public int getLevel() {
        return level;
    }

    public int getFunnes() {
        return funnes;
    }
    
    public void upgrade(){
        buildingPrice+=upgradePrice;
        switch (level){
                case 0: 
                    //no upgrade avaliable
                break;
                case 1:
                        level+=1;
                        upgradePrice=(int)(upgradePrice*1.5);
                        funnes+=2;
                    
                    break;
                case 2:
                        level+=1;
                        upgradePrice=0;
                        funnes+=3;
                    break;
                default:
                    break;
        }
    }
    
    public BufferedImage getImg(){
        return img;
    }
 
}
