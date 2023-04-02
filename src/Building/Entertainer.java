package Building;

import GameEngine.Coordinate;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Entertainer extends Building{
    int condition;
    int durability;
    Boolean underMaintenece;
    int slots;
    public int userSlots;
    int userPrice;
    
    public Entertainer(String type) {
        super(type);
        // read from file
        condition = 100;
        durability = Integer.parseInt(buildingSpecs[6]);
        underMaintenece = false;
        slots = Integer.parseInt(buildingSpecs[7]);
        userPrice = Integer.parseInt(buildingSpecs[8]);
        userSlots = 0;
    }
    
    synchronized public void decreaseCondition(){
        int tmp = (int)((float)(10 - durability)/2);
        
        //System.out.println(type + "- Condition: " + condition);
        
        if(condition > 0){
            if((condition - tmp) <= 0){
                condition = 0;
                underMaintenece = true;
            }else{
                condition -= tmp;
                underMaintenece = false;
            }
        }
        if(condition == 0){
            underMaintenece = true;
            try {
                img = ImageIO.read(new File("src/GFX/" + this.getType() + "Const.png"));
            } catch (IOException ex) {
            }

        }
    }
    
    synchronized public void decreaseSlots(){
        if(userSlots > 0){
            userSlots--;
        }
    }
    
    synchronized public void increaseSlots(){
        if(userSlots < slots){
            userSlots++;   
        }

    }

    synchronized public int getUserSlots() {
        return userSlots;
    }
    
    public void repair(){
    this.condition = 100;
    underMaintenece = false;
        try {
            img = ImageIO.read(new File("src/GFX/" + this.getType() + ".png"));
        } catch (IOException ex) {
        }

    }

    public int getSlots() {
        return slots;
    }
    
    
    
    public int getUserPrice() {
        return userPrice;
    }

    public int getCondition() {
        return condition;
    }
    
    

    public Boolean isUnderMaintenece() {
        return underMaintenece;
    }

}
