package Npc;
import Building.*;
import GameEngine.GameEngine.Player;
import GameEngine.Map;
import java.io.IOException;
import java.util.ArrayList;

public class Employee extends Npc {
    int salary;
    Building workingAt;
    
    public Employee(Map map, Player player) throws IOException{
        super(map, player);
    }
}
