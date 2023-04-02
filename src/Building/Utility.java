package Building;

public abstract class Utility extends Building{
    int slots;
    int userSlots;
    int userPrice;
    
    //what kind of need it satisfies
    
    public Utility(String type) {
        super(type);
        // read from file
        slots = Integer.parseInt(buildingSpecs[6]);
        userPrice = Integer.parseInt(buildingSpecs[7]); 
        userSlots = 0;
    }

    public void decreaseSlots(){
        if(userSlots > 0){
            this.userSlots--;
        }
    }
    
    public void increaseSlots(){
        if(userSlots < slots){
            this.userSlots++;   
        }

    }

    public int getUserSlots() {
        return userSlots;
    }
}