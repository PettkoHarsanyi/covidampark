/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import Building.Building;
import Building.Road;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Felhasznalo
 */
public class GameEngineTest {

    static GameEngine ge;
    
    @BeforeClass
    public static void gameEngineConstruct(){
        ge = new GameEngine();
    }
    
    @Test
    public void IsNotNull(){
        assertNotNull(ge);
    }

    @Test
    public void testIsSetParkOpen() {
        System.out.println("setParkOpen");
        boolean result = ge.isParkOpen();
        assertEquals("parkOpen should be false", false, result);
        boolean parkOpen = true;
        ge.setParkOpen(true);
        result = ge.isParkOpen();
        assertEquals("parkOpen should be true", true, result);
    }

    @Test
    public void testGetSelectedBuilding() {
        System.out.println("getSelectedBuilding");
        GameEngine instance = new GameEngine();
        Building expResult = null;
        Building result = instance.getSelectedBuilding();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetPlayerMoney() {
        System.out.println("getPlayerMoney");
        GameEngine instance = new GameEngine();
        int expResult = 10000;
        int result = instance.getPlayerMoney();
        assertEquals(expResult, result);
    }

    @Test
    public void testDecreasePlayerMoney() {
        System.out.println("decreasePlayerMoney");
        int tmp = 0;
        GameEngine instance = new GameEngine();
        instance.decreasePlayerMoney(tmp);
    }

    @Test
    public void testAddBuilding() {
        System.out.println("addBuilding");
        Building b = null;
        GameEngine instance = new GameEngine();
        instance.addBuilding(b);
    }

    @Test
    public void testSelectBuildingType() {
        System.out.println("selectBuildingType");
        String text = "";
        GameEngine instance = new GameEngine();
        instance.selectBuildingType(text);
    }





    /**
     * Test of hoverOn method, of class GameEngine.
     */
    @Test
    public void testHoverOn() {
        System.out.println("hoverOn");
        int x = 0;
        int y = 0;
        GameEngine instance = new GameEngine();
        instance.hoverOn(x, y);
    }

    /**
     * Test of hasSelected method, of class GameEngine.
     */
    @Test
    public void testHasSelected() {
        System.out.println("hasSelected");
        GameEngine instance = new GameEngine();
        boolean expResult = false;
        boolean result = instance.hasSelected();
        assertEquals(expResult, result);
    }
}
