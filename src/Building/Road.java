package Building;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Road extends Building {
    int maxDirtiness;
    int dirtiness;
    BufferedImage maxDirtyImg;
    BufferedImage midDirtyImg;
    BufferedImage minDirtyImg;
    BufferedImage cleanImg;
    int imgGroupNum;
    
    
    public Road(String type) {
        super(type);
        maxDirtiness = Integer.parseInt(buildingSpecs[6]); // read from file, always initialised as 0 xd 
        dirtiness = 0;
        level = 0;
        Random rand = new Random();
        imgGroupNum = rand.nextInt(6) + 1;
    }
    
    public void increaseDirtiness() {
        dirtiness = Math.min(dirtiness + 1, maxDirtiness);
        
        if(dirtiness > 40){
            if(maxDirtyImg == null){
                try {
                    maxDirtyImg = ImageIO.read(new File("src/GFX/" + this.getType() + "MaxDirty" + imgGroupNum + ".png"));
                } catch (IOException ex) {
                }
                img = maxDirtyImg;
            }else{
                img = maxDirtyImg;
            }
        }else if(dirtiness > 30){
            if(midDirtyImg == null){
                try {
                    midDirtyImg = ImageIO.read(new File("src/GFX/" + this.getType() + "MidDirty" + imgGroupNum + ".png"));
                } catch (IOException ex) {
                }
                img = midDirtyImg;
            }else{
                img = midDirtyImg;
            }
        }else if(dirtiness > 15){
            if(minDirtyImg == null){
                try {
                    minDirtyImg = ImageIO.read(new File("src/GFX/" + this.getType() + "MinDirty" + imgGroupNum + ".png"));
                } catch (IOException ex) {
                }
                img = minDirtyImg;
            }else{
                img = minDirtyImg;
            }
        }
    }
    
    public void clean() {
        dirtiness = 0;
        
        if(cleanImg == null){
                try {
                    cleanImg = ImageIO.read(new File("src/GFX/" + this.getType() + ".png"));
                } catch (IOException ex) {
                }
                img = cleanImg;
            }else{
                img = cleanImg;
            }
    }
    
    public int getDirtiness(){
        return dirtiness;
    }

    public int getMaxDirtiness() {
        return maxDirtiness;
    }
    
}
