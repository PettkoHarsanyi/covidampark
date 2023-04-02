/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Building.Building;
import Building.Entertainer;
import Building.FancyRoad;
import Building.SimpleRoad;
import GameEngine.*;
import Npc.Cleaner;
import Npc.Npc;
import Npc.Visitor;
import Npc.Janitor;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Felhasznalo
 */
public class GameFrame extends javax.swing.JFrame{
    static private int HUDWIDTH;
    static private int HUDHEIGHT;
    private int WIDTH;
    private int HEIGHT;
    
    
    private GameEngine gameEngine;
    private HUD hud;
    private BackgroundMusic music;
    private BackgroundMusic build_efx;
    private GamePanel gamePanel;
    boolean gridOn;
    private Dimension screenSize;
    private InfoFactory infoFactory;
    private BuildingMenu buildingMenu;
    /**
     * Creates new form GameFrame
     */
    public GameFrame(Dimension size) {
        initComponents();
        setTitle("CovidamPark");

        HUDWIDTH = 200;
        HUDHEIGHT = size.height;
        HEIGHT = size.height;
        WIDTH = size.width;
        
        gameEngine = new GameEngine();
        music = new BackgroundMusic(getClass().getResource("/Media/bg_music-16bit.wav"));
        build_efx = new BackgroundMusic(getClass().getResource("/Media/build_efx.wav"));
        infoFactory = new InfoFactory();
        buildingMenu = null;
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        HUDHEIGHT = ((int)screenSize.getHeight());
        
//        setPreferredSize(size);
//        setMinimumSize(size);
//        setMaximumSize(size);
//        HUDHEIGHT = this.getBounds().height;
        
        
        gamePanel = new GamePanel(this);
        hud = new HUD(this,HUDHEIGHT);
        gridOn = true;

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    gameEngine.setSelectedBuilding(null);
                }
            }
        });
        
        setFocusable(true);
        requestFocusInWindow();
        
        getLayeredPane().add(gamePanel,0,0);
        getLayeredPane().add(jLabel2,1,0);
        getLayeredPane().add(hud,2,0);
        getLayeredPane().add(jLabel7,3,0);
        getLayeredPane().add(timeLabel,4,0);
        
        pack();
        setVisible(true);
        
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    Thread.sleep(5000);
                    guideInfo("Drag mouse to move camera", "/GFX/drag.png");
                }catch(Exception e){
                }
            }
        };
        thread.start();
    }
    
    private class GamePanel extends JPanel implements ActionListener{
        
        Field[][] map = gameEngine.getMap().getFields();
        ArrayList<Building> buildings = gameEngine.getBuildings();
        Timer timer;
        ArrayList<Field> fields = new ArrayList<>();
        GameFrame frame;
        Building entrance;

        private int mousePosX;          // MOUSE POS ON THE X AXIS (IN PIXELS)
        private int mousePosY;          // MOUSE POS ON THE Y AXIS (IN PIXELS)
        private int fieldPosX;          // X POSITION OF THE FIELD'S TOP LEFT CORNER THE MOUSE IS ON (IN PIXELS)
        private int fieldPosY;          // Y POSITION OF THE FIELD'S TOP LEFT CORNER THE MOUSE IS ON (IN PIXELS)
        private int dragStartX;
        private int dragStartY;
        
        boolean once = false;
        BufferedImage bg;
        
        

        GamePanel(GameFrame frame){
            try {
                this.bg = ImageIO.read(new File("src/GFX/bg.png"));
            } catch (IOException ex) {
            }
            setBackground(new java.awt.Color(94,176,84));
            setBounds(0,0,40*48,40*27);
            setFocusable(true);
            Mouse mouseListener = new Mouse();
            addMouseListener(mouseListener);
            addMouseMotionListener(mouseListener);
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(82,153,73)));
            
            entrance  = new Building();
            gameEngine.getMap().placeEntrance(new Coordinate(23,0));
            gameEngine.setParkOpen(false);
            
            timer = new Timer(1000/15,this);
            timer.start();
            gameSpeed.start();
            //music.start();
            this.frame = frame;
            
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);       
            draw(g);
        }
        
        public void draw(Graphics g){
            // DRAW ALL THE BULLSHIT THATS ON THE MAP
            // FOREACH ALL BULDINGS AND SHIT (CREATES LAG)
            
//            // Background             // <------- VERY LAGGY
//            try {
//                BufferedImage img = ImageIO.read(new File("src/GFX/bg.png"));
//                g.drawImage(img,0,0,null);
//            } catch (IOException e) {
//            }
            g.drawImage(bg, 0, 0, null);

            
            
            // -- HOVERING SECTION I. -----------------------------------------------------------------------
            if(gameEngine.hasSelected()){                                       
                repaint();
                Building hover = gameEngine.getSelectedBuilding();
                
                if(!hover.getType().equals("SimpleRoad") && !hover.getType().equals("FancyRoad")){
                    
                    fieldPosX = hover.getLocation().getPos().x;
                    fieldPosY = hover.getLocation().getPos().y;
                    
                    if(gameEngine.getMap().checkIfFree(new Coordinate(fieldPosX/40,fieldPosY/40), hover)){
                        g.setColor(new java.awt.Color(144,238,144));  
                        g.fillRect(fieldPosX,fieldPosY,hover.getSize().x*40,hover.getSize().y*40);
                    }else{
                        g.setColor(new java.awt.Color(204,6,5));
                        g.fillRect(fieldPosX,fieldPosY,hover.getSize().x*40,hover.getSize().y*40);
                    }
                    
                }else{
                    g.setColor(new java.awt.Color(144,238,144)); 
                    g.fillRect(fieldPosX,fieldPosY,40,40);
                }
            }
            // -----------------------------------------------------------------------------------------------

            
            

            // -- Grid Draw Section --------------------------------------------------------------------------
            if(gridOn){
                    for(int i = 0; i < map.length; ++i) {
                    for(int j = 0; j < map[i].length; ++j) {
                        g.setColor(new java.awt.Color(82,153,73));
                        g.drawRect(i*40, j*40, 40, 40);
                    }
                }
            }
            // -----------------------------------------------------------------------------------------------
            
            //-- MAIN ENTRANCE
            
            g.drawImage(entrance.getImg(), 23*40, 0, null);
            //
            
            // -- Building Draw Section ----------------------------------------------------------------------
            for(Building b : buildings){
                int drawPosX = b.getLocation().getPos().x*40;
                int drawPosY = b.getLocation().getPos().y*40;
                int size_x = b.getSize().x*40;
                int size_y = b.getSize().y*40;
                
//                    BufferedImage img = ImageIO.read(new File("src/GFX/" + b.getType() + ".png"));
                    g.drawImage(b.getImg(), drawPosX, drawPosY, null);
//                    g.setColor(new java.awt.Color(0,0,0));
//                    g.fillRect(drawPosX, drawPosY, size_x, size_y);
            }
            // -----------------------------------------------------------------------------------------------
            
            // NPC (!!)
            for(Visitor visitor : gameEngine.getVisitors()){
                if(visitor.isVisible()){
                    g.drawImage(visitor.getImage(), visitor.getPosition().x+visitor.getOffset(), visitor.getPosition().y,null);
                }
//                    g.setColor(new java.awt.Color(0,0,0)); 
//                    g.fillRect(visitor.getPosition().x,visitor.getPosition().y,10,10);
            }
            
            for(Janitor j : gameEngine.getJanitors()){
                if(j.isVisible() && j.hasTarget()){
                    g.drawImage(j.getImage(), j.getPosition().x + j.getOffset(), j.getPosition().y,null);
                }
            }
            
            for(Cleaner c : gameEngine.getCleaners()){
                if(c.isVisible()){
                    g.drawImage(c.getImage(), c.getPosition().x + c.getOffset(), c.getPosition().y,null);
                }
            }
            
            // -- HOVERING SECTION II. (THE LETTERS NEED TO BE DRAWN LAST AS THEY MUST BE READ) --------------
            if(gameEngine.hasSelected()){                                                       // THIS COULD BE IN THE HOVERING SECTION I. BUT THAT WAY THE STRINGS GET COVERED
                Building hover = gameEngine.getSelectedBuilding();
                if(!hover.getType().equals("SimpleRoad") && !hover.getType().equals("FancyRoad")){                                              // IF SELECTED BUILDING IS NOT ROAD 
                    fieldPosX = hover.getLocation().getPos().x;
                    fieldPosY = hover.getLocation().getPos().y;
                    
                    if(gameEngine.getMap().checkIfFree(new Coordinate(fieldPosX/40,fieldPosY/40), hover)){                                  // IF CHECKFREE IS TRUE
                        g.setColor(new java.awt.Color(144,238,144));                                                                        // GREEN TEXT - BUILD
                        g.drawString(String.valueOf("$" + hover.getPrice() + " - [LMB] to build"), fieldPosX, fieldPosY-5);
                        g.drawString(String.valueOf("[ESC] to cancel"),  fieldPosX, fieldPosY-20);
                    }else{                                                                                                                  // IF CHECKFREE IS FALSE
                        g.setColor(new java.awt.Color(204,6,5));                                                                            // RED TEXT - TAKEN
                        g.drawString(String.valueOf("$" + hover.getPrice() + " - place is taken"), fieldPosX, fieldPosY-5);
                        g.drawString(String.valueOf("[ESC] to cancel"),  fieldPosX, fieldPosY-20);
                    }
                }else{                  
                    g.setColor(new java.awt.Color(144,238,144));                                                                    // GREEN TEXT - HOLD TO BUILD
                    g.drawString(String.valueOf("$" + hover.getPrice() + " - hold [LMB] to build"), mousePosX-20, mousePosY-20);
                    g.drawString(String.valueOf("[ESC] to cancel"), mousePosX-20, mousePosY-35);
                }
            }
            // ----------------------------------------------------------------------------------------------
        }

        @Override
        public void actionPerformed(ActionEvent e) {                            // NOT NEEDED ANYMORE SINCE REPAINT() IS ONLY CALLED WHEN CHANGE OCCURS
            repaint();
        }
        
        
        public class Mouse extends MouseAdapter{
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                
                boolean isRoad = false;
                if(gameEngine.hasSelected()){
                     isRoad = (gameEngine.getSelectedBuilding().getType().equals("SimpleRoad") || gameEngine.getSelectedBuilding().getType().equals("FancyRoad"));
                }                
                
                if(!gameEngine.hasSelected() && gameEngine.getMap().getFieldAtCoords(new Coordinate(fieldPosX/40,fieldPosY/40)).isTaken()){
                    if(BuildingMenu.getInstances() == 0){
                        buildingMenu = new BuildingMenu(frame, gameEngine.getMap().getBuildingOnCoords(new Coordinate(fieldPosX/40,fieldPosY/40)), gameEngine);
                        getLayeredPane().add(buildingMenu,5,0);
                        buildingMenu.requestFocusInWindow();
                    }else{
                        buildingMenu.removeFromFrame();
                        buildingMenu = new BuildingMenu(frame, gameEngine.getMap().getBuildingOnCoords(new Coordinate(fieldPosX/40,fieldPosY/40)), gameEngine);
                        getLayeredPane().add(buildingMenu,5,0);
                        buildingMenu.requestFocusInWindow();
                    }
                    
//                    buildingMenu.show(gamePanel , evt.getPoint().x, evt.getPoint().y);
                }
                if(gameEngine.hasSelected() && !isRoad){                            // IF THERE IS SELECTED AND IS NOT ROAD THEN PLACE A BUILDING
                    gameEngine.placeBuilding(new Coordinate(fieldPosX/40,fieldPosY/40));
                    gameEngine.ratePark();
                    try {
                        build_efx.efx();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(gameEngine.hasSelected() && isRoad){                              // IF ROAD IS SELECTED THEN RELEASING MOUSE MEANS TO NULL THE SELECTEDBUILDING
                    gameEngine.setSelectedBuilding(null);                            // IN OTHER CASES THE "placeBuilding" MAKES THE SELECTEDBUILDING NULL
                }
                
                hud.getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));

            }
            
            @Override
            public void mouseMoved(MouseEvent evt){
                fieldPosX = evt.getX() - (evt.getX() % 40);
                fieldPosY = evt.getY() - (evt.getY() % 40);
                gameEngine.hoverOn(fieldPosX,fieldPosY);
                mousePosX = evt.getX();
                mousePosY = evt.getY();

            }
            int lastX = 1;
            int lastY = 1;
            
            @Override
            public void mouseDragged(MouseEvent evt){

                fieldPosX = evt.getX() - (evt.getX() % 40);
                fieldPosY = evt.getY() - (evt.getY() % 40);
                mousePosX = evt.getX();
                mousePosY = evt.getY();
                
                if(gameEngine.hasSelected()){
                    if(gameEngine.getSelectedBuilding().getType().equals("SimpleRoad")){
                        gameEngine.placeRoad(new SimpleRoad(new Field(fieldPosX/40,fieldPosY/40)));
                    }
                    else if(gameEngine.getSelectedBuilding().getType().equals("FancyRoad")){
                        gameEngine.placeRoad(new FancyRoad(new Field(fieldPosX/40,fieldPosY/40)));
                    }
                }else{
//                    if(screenSize.width != 1920 || screenSize.height != 1080){
                        panelDragged(evt);
//                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent evt){
                int x = evt.getX() - (evt.getX() % 40);
                int y = evt.getY() - (evt.getY() % 40);
                dragStartX = evt.getX();
                dragStartY = evt.getY();
                if(gameEngine.getSelectedBuilding() != null && gameEngine.getSelectedBuilding().getType().equals("SimpleRoad")){
                    gameEngine.placeRoad(new SimpleRoad(new Field(x/40,y/40)));
                }else if(gameEngine.getSelectedBuilding() != null && gameEngine.getSelectedBuilding().getType().equals("FancyRoad")){
                    gameEngine.placeRoad(new FancyRoad(new Field(x/40,y/40)));
                }
            }
        }
        
        private void panelDragged(MouseEvent evt){
//            if(this.getLocation().x + evt.getX()-dragStartX <= 0 && (this.getLocation().x + evt.getX()-dragStartX) >= -(1920-frame.getBounds().width)){
                //System.out.println(this.getLocation());
                this.setLocation(this.getLocation().x + evt.getX()-dragStartX,this.getLocation().y);
//            }
//            if(this.getLocation().y + evt.getY()-dragStartY <= 0 && (this.getLocation().y + evt.getY()-dragStartY) >= -(1080-frame.getBounds().height)){
                this.setLocation(this.getLocation().x,this.getLocation().y + evt.getY()-dragStartY);
//            }
//            if(this.getLocation().x + evt.getX()-dragStartX <= 0 && this.getLocation().y + evt.getY()-dragStartY <= 0
//             &&this.getLocation().x + evt.getX()-dragStartX >= -1920 && this.getLocation().y + evt.getY()-dragStartY >= -1080){
//                    this.setLocation(this.getLocation().x + evt.getX()-dragStartX,this.getLocation().y + evt.getY()-dragStartY);
//            }
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);
        setResizable(false);
        setSize(WIDTH,HEIGHT);
        getContentPane().setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GFX/in.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 10, 35, 35);

        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        timeLabel.setToolTipText("");
        getContentPane().add(timeLabel);
        timeLabel.setBounds(320, 10, 100, 30);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Elapsed Time:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(210, 10, 100, 30);

        jLabel1.setBackground(new java.awt.Color(96, 176, 84));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        hud.openHud();
    }//GEN-LAST:event_jLabel2MouseReleased
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

    
//    public void openHUD(HUD hud){
//        hud.setSize(200,this.getHeight());                                    //STATIC VERSION
//    }
    
//    private class BuildingMenu extends JPopupMenu{
//            
//        JMenuItem menuItem;
//        
//        public BuildingMenu(GameFrame frame , Building building , GameEngine gameEngine) {
//            super();
//            if(!building.getType().equals("Entrance")){
//                if(building.getLevel() < 3 && building.getLevel() != 0) {
//                    menuItem = new JMenuItem("-" + round(building.getUpgradePrice()) + "$" + " - Upgrade");
//                    menuItem.addActionListener(new ActionListener(){
//                    @Override
//                    public void actionPerformed(ActionEvent e){
//                        gameEngine.upgradeBuilding(building);
//                        hud.getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
//                        frame.repaint();     
//                    }
//                });
//                } else {
//                    menuItem = new JMenuItem("Max level");
//                }
//                this.add(menuItem);
//                menuItem = new JMenuItem("+" + round(building.getPrice()*(60/100.0f)) + "$" + " - Sell");
//                menuItem.addActionListener(new ActionListener(){
//                    @Override
//                    public void actionPerformed(ActionEvent e){
//                        gameEngine.sellBuilding(building.getLocation().getPos());
//                        hud.getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
//                        frame.repaint();
//                    }
//                });
//                if(building instanceof Entertainer){
//                    if(getEntertainerUnderMaintenece(building)){
//                        this.add(menuItem);
//                        menuItem = new JMenuItem("Repair");
//                        menuItem.addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            entertainerRepair(building);
//                            }
//                        });
//                    }
//                }
//                
//            }else{
//                if(gameEngine.isParkOpen()){
//                    menuItem = new JMenuItem("Close");
//                        menuItem.addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            gameEngine.setParkOpen(false);
//                        }
//                    });
//                }else{
//                    menuItem = new JMenuItem("Open");
//                        menuItem.addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            gameEngine.setParkOpen(true);
//                        }
//                    });
//                }
//            }
//            this.add(menuItem);
//            setVisible(true);
//        }
//    }
    
    private class InfoFactory{
        Random rand;
        
        public InfoFactory() {
            rand = new Random();
            String[] messages = {"What does the fool say to the clever?","ELTE - IK is epic","HHK boys are amazing","ELTE - IK is epic","Your theme park is amazing",
                                    "You need to hire janitors first to repair","You need to hire a cleaner to clean the roads","Buying trashcan prevents dropping trash on roads",
                                    "You can turn on/off grid in the menu","Visitors' happiness goes down after dirty roads",};
            Thread thread = new Thread(){
                @Override
                public void run(){
                    while(true){
                        try{
                            Thread.sleep(40000);
                            int randInt = rand.nextInt(messages.length-1);
                            guideInfo(messages[randInt],"/GFX/info.png");                            
                        }catch(Exception e){
                            
                        }
                    }
                }
            };
            thread.start();

        }
        
    }
    
    public boolean getEntertainerUnderMaintenece(Building p) {
    if(p instanceof Entertainer) return ((Entertainer)p).isUnderMaintenece();
    else return false;
    }
    
    void entertainerRepair(Building p) {
    if(p instanceof Entertainer) ((Entertainer)p).repair();
    }
    
    public int getPriceOfBuilding(String building){
        boolean l = false;
        int price = 0;
        try{
            Scanner scanner = new Scanner(new File("src/GFX/buildingStats.txt"));
            while(scanner.hasNextLine() && !l){
                String line = scanner.nextLine();
                if(line.split("\t")[0].equals(building)){
                    price = Integer.parseInt(line.split("\t")[1]);
                }
            }
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        return price;
    }
    
    public void guideInfo(String text, String info){
        InfoPanel infoPanel = new InfoPanel(this,text,info);
        getLayeredPane().add(infoPanel, 6,0);
    }
    
    public HUD getHud(){
        return hud;
    }
    
    public BuildingMenu getBuildingMenu(){
        return buildingMenu;
    }
    
    public GameEngine getGameEngine(){
        return gameEngine;
    }
    
    public Timer getGameSpeed(){
        return gameSpeed;
    }
    
    public BackgroundMusic getGameMusic(){
        return music;
    }
    
    public Dimension getScreenSize(){
        return screenSize;
    }
    
///game timer basic implementation, maybe needs better arangement(dont know how to spell it) 
    int elapsedTime = 0;
    int seconds =0;
    int minutes =0;
    int hours =0;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    Timer gameSpeed = new Timer(1000, new ActionListener(){
  
    public void actionPerformed(ActionEvent e) {
   
        elapsedTime=elapsedTime+1000;
        hours = (elapsedTime/3600000);
        minutes = (elapsedTime/60000) % 60;
        seconds = (elapsedTime/1000) % 60;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        
        
        if(gameEngine.isOpen() && gameEngine.getParkRating() != 0 && seconds != 0){
            if( (seconds % gameEngine.getParkRating()) == 0 ){
                try {
                        gameEngine.createVisitor();
                } catch (IOException ex) {
                }
            }
        }
        
        hud.getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
        
        for(Visitor visitor: gameEngine.getVisitors()) {
            if(visitor.IsAlive()){
                visitor.doYourThing();
            }
        }
        
        for(Janitor j: gameEngine.getJanitors()) {
            if(j.hasTarget()){
                j.repairTarget();
            }
        }
        
        for(Cleaner c : gameEngine.getCleaners()) {
            if(c.isVisible()) {
                c.doYourThing();
            }
        }
        
        if(buildingMenu != null && buildingMenu.getBuilding() instanceof Entertainer) {
            buildingMenu.setCondition();
            buildingMenu.setSlots();
        }
        
        calculateWorkersSalary();
        refreshMoneyLabel();
         /*
        for(Building b : gameEngine.getBuildings()){
            if(b instanceof Entertainer){
                System.out.println(((Entertainer) b).getUserSlots());
            }
        }*/
    }

    
    
    });

    public void setGridOn(boolean l){
        gridOn = l;
    }
    
    public boolean getGridStatus(){
        return gridOn;
    }
    
    
    public void refreshMoneyLabel() {
        hud.getMoneyLabel().setText("$"+Integer.toString(gameEngine.getPlayer().getMoney()));
    }
    
    private void calculateWorkersSalary(){
        int salary = gameEngine.getJanitors().size()*2 + gameEngine.getCleaners().size();
        gameEngine.getPlayer().setMoney(gameEngine.getPlayer().getMoney() - salary);
    }
    
}