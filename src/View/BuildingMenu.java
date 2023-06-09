/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Building.Building;
import Building.Entertainer;
import Building.Road;
import Building.Utility;
import GameEngine.GameEngine;
import Npc.Janitor;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import static java.lang.Math.round;
import javax.swing.JFrame;
import org.w3c.dom.css.RGBColor;

/**
 *
 * @author Felhasznalo
 */
public class BuildingMenu extends javax.swing.JPanel {
    GameFrame frame;
    GameEngine gameEngine;
    Building building;
    static int instances = 0;

    /**
     * Creates new form BuildingMenu
     */
    public BuildingMenu(GameFrame frame , Building building , GameEngine gameEngine) {
        if(instances < 1){
            initComponents();
            this.frame = frame;
            this.building = building;
            this.gameEngine = gameEngine;
            int posX = frame.getLayeredPane().getMousePosition().x + 30;
            int posY = frame.getLayeredPane().getMousePosition().y + 20;
            
            nameLabel.setText(building.getType());
            if(building.getLevel()<3){
                upgradeLabel.setText("UPGRADE");
            }else{
                upgradeLabel.setText("MAX LEVEL");
            }
            
            addKeyListener(new KeyAdapter(){
                        public void keyPressed(KeyEvent e){
                            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                                removeFromFrame();
                            }
                        }
            });
            
            if(building instanceof Entertainer && !((Entertainer)building).isUnderMaintenece()){
                this.remove(repairLabel);
            }
            
            if(building.getType().equals("Entrance")){
                removeAll();
                add(openLabel);
                add(nameLabel);
                add(closeLabel);
                setOpenLabel();
                setBounds(posX, posY, 220, 150);
            }else if(building instanceof Entertainer){
                conditionLabel.setText("Condition: "+((Entertainer)building).getCondition());
                remove(openLabel);
                remove(roadAndDecorSellLabel);
                setCondition();
                setSlots();
                setBounds(posX, posY, 220, 300);
            }else if(building instanceof Utility){
                removeAll();
                add(closeLabel);
                add(nameLabel);
                add(sellLabel);
                add(upgradeLabel);
                setBounds(posX, posY, 220, 300);

            }else{
                removeAll();
                add(closeLabel);
                add(nameLabel);
                add(roadAndDecorSellLabel);
                setBounds(posX, posY, 220, 150);
            }
            
            repairLabel.setBackground((gameEngine.getJanitors().size() > 0) ? (new Color(255,153,0)) : Color.gray);

            setFocusable(true);
            requestFocusInWindow();  
            instances++;
        }
        
    }

    public static int getInstances() {
        return instances;
    }
    
    public Building getBuilding(){
        return building;
    }
    
    private void setOpenLabel(){
        if(gameEngine.isParkOpen()){
            nameLabel.setText("PARK IS OPEN");
            nameLabel.setBackground(new Color(51,204,0));
            nameLabel.setForeground(Color.white);
            openLabel.setText("CLOSE");
            setBackground(new Color(186,229,183));
        }else{
            nameLabel.setBackground(new Color(153,0,0));
            nameLabel.setForeground(Color.white);
            nameLabel.setText("PARK IS CLOSED");
            openLabel.setText("OPEN");
            setBackground(new Color(204,204,204));

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

        nameLabel = new javax.swing.JLabel();
        upgradeLabel = new javax.swing.JLabel();
        sellLabel = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        repairLabel = new javax.swing.JLabel();
        openLabel = new javax.swing.JLabel();
        slotsLabel = new javax.swing.JLabel();
        conditionLabel = new javax.swing.JLabel();
        roadAndDecorSellLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        slotsStateLabel = new javax.swing.JLabel();
        conditionStateLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        setLayout(null);

        nameLabel.setBackground(new java.awt.Color(204, 204, 204));
        nameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        nameLabel.setOpaque(true);
        add(nameLabel);
        nameLabel.setBounds(9, 27, 202, 48);

        upgradeLabel.setBackground(new java.awt.Color(94, 176, 84));
        upgradeLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        upgradeLabel.setForeground(new java.awt.Color(247, 184, 86));
        upgradeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upgradeLabel.setText("UPGRADE");
        upgradeLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        upgradeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        upgradeLabel.setOpaque(true);
        upgradeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                upgradeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                upgradeLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                upgradeLabelMouseReleased(evt);
            }
        });
        add(upgradeLabel);
        upgradeLabel.setBounds(10, 240, 93, 36);

        sellLabel.setBackground(new java.awt.Color(153, 0, 0));
        sellLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sellLabel.setForeground(new java.awt.Color(153, 153, 153));
        sellLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sellLabel.setText("SELL");
        sellLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        sellLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sellLabel.setOpaque(true);
        sellLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sellLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sellLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sellLabelMouseReleased(evt);
            }
        });
        add(sellLabel);
        sellLabel.setBounds(120, 240, 93, 36);

        closeLabel.setBackground(new java.awt.Color(204, 0, 0));
        closeLabel.setForeground(new java.awt.Color(255, 255, 255));
        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setText("X");
        closeLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        closeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabel.setOpaque(true);
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        add(closeLabel);
        closeLabel.setBounds(199, 3, 18, 18);

        repairLabel.setBackground(new java.awt.Color(255, 153, 0));
        repairLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        repairLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repairLabel.setText("REPAIR");
        repairLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        repairLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repairLabel.setOpaque(true);
        repairLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                repairLabelMouseReleased(evt);
            }
        });
        add(repairLabel);
        repairLabel.setBounds(10, 190, 200, 36);

        openLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        openLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        openLabel.setText("OPEN");
        openLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        openLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        openLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                openLabelMouseReleased(evt);
            }
        });
        add(openLabel);
        openLabel.setBounds(40, 90, 140, 40);

        slotsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotsLabel.setText("Slots:");
        slotsLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        add(slotsLabel);
        slotsLabel.setBounds(10, 140, 200, 30);

        conditionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        conditionLabel.setText("Condition:");
        conditionLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        add(conditionLabel);
        conditionLabel.setBounds(10, 90, 200, 30);

        roadAndDecorSellLabel.setBackground(new java.awt.Color(153, 0, 0));
        roadAndDecorSellLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        roadAndDecorSellLabel.setForeground(new java.awt.Color(153, 153, 153));
        roadAndDecorSellLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roadAndDecorSellLabel.setText("SELL");
        roadAndDecorSellLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        roadAndDecorSellLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roadAndDecorSellLabel.setOpaque(true);
        roadAndDecorSellLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                roadAndDecorSellLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                roadAndDecorSellLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                roadAndDecorSellLabelMouseReleased(evt);
            }
        });
        add(roadAndDecorSellLabel);
        roadAndDecorSellLabel.setBounds(40, 90, 140, 40);

        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        add(jLabel4);
        jLabel4.setBounds(10, 170, 200, 10);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        add(jLabel2);
        jLabel2.setBounds(10, 120, 200, 10);

        slotsStateLabel.setBackground(new java.awt.Color(255, 0, 0));
        slotsStateLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 2, new java.awt.Color(0, 0, 0)));
        slotsStateLabel.setOpaque(true);
        add(slotsStateLabel);
        slotsStateLabel.setBounds(10, 170, 200, 10);

        conditionStateLabel.setBackground(new java.awt.Color(0, 255, 51));
        conditionStateLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 2, new java.awt.Color(0, 0, 0)));
        conditionStateLabel.setOpaque(true);
        add(conditionStateLabel);
        conditionStateLabel.setBounds(10, 120, 200, 10);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        add(jLabel1);
        jLabel1.setBounds(10, 120, 200, 10);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setOpaque(true);
        add(jLabel5);
        jLabel5.setBounds(10, 170, 200, 10);
    }// </editor-fold>//GEN-END:initComponents

    public void setCondition(){
        float conditionPercent = (float)((Entertainer)building).getCondition() / 100;
        conditionLabel.setText("Condition: " + ((Entertainer)building).getCondition());
        if(((Entertainer)building).getCondition() == 0 ){
            add(repairLabel);
        }
        conditionStateLabel.setSize((int)(200*conditionPercent),10);
        if(((Entertainer)building).getCondition() > 60){
            conditionStateLabel.setBackground(new Color(0,255,51));
        }else if(((Entertainer)building).getCondition() > 30){
            conditionStateLabel.setBackground(Color.orange);
        }else{
            conditionStateLabel.setBackground(Color.red);
        }
    }

    public void setSlots() {
        int slotsPercent = (int)((float)( (Entertainer)building).getUserSlots() / ((Entertainer)building).getSlots()  * 100);
        slotsLabel.setText("Slots: " + ((Entertainer)building).getUserSlots() + "/" + ((Entertainer)building).getSlots());
        slotsStateLabel.setSize(2*(int)(slotsPercent),10);
        
        if(slotsPercent > 80){
            slotsStateLabel.setBackground(Color.red);
        }else if(slotsPercent > 50){
            slotsStateLabel.setBackground(Color.orange);
        }else{
            slotsStateLabel.setBackground(new Color(0,255,51));
        }
    }
    
    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        removeFromFrame();
        
    }//GEN-LAST:event_closeLabelMouseClicked

    private void upgradeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upgradeLabelMouseEntered
        showUpgradePrice();
    }//GEN-LAST:event_upgradeLabelMouseEntered

    private void showUpgradePrice(){
        if(building.getLevel()<3){
            upgradeLabel.setText("-$" + round(building.getUpgradePrice()));           
        }else{
            upgradeLabel.setText("MAX LEVEL"); 
        }
    }
    
    private void upgradeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upgradeLabelMouseExited
        if(building.getLevel()<3){
            upgradeLabel.setText("UPGRADE");       
        }else{
            upgradeLabel.setText("MAX LEVEL"); 
        }
    }//GEN-LAST:event_upgradeLabelMouseExited

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        if(!stillInPanel(evt.getPoint())){
            removeFromFrame();
        }
    }//GEN-LAST:event_formMouseExited

    private void upgradeLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upgradeLabelMouseReleased
        gameEngine.upgradeBuilding(building);
        frame.getHud().getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
        showUpgradePrice();
    }//GEN-LAST:event_upgradeLabelMouseReleased

    private void sellLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellLabelMouseReleased
        gameEngine.sellBuilding(building.getLocation().getPos());
        gameEngine.ratePark();
        frame.getHud().getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
        removeFromFrame();
    }//GEN-LAST:event_sellLabelMouseReleased

    private void sellLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellLabelMouseEntered
        sellLabel.setText("+$" + (int)(building.getPrice()*(60/100.0f)));
    }//GEN-LAST:event_sellLabelMouseEntered

    private void sellLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellLabelMouseExited
        sellLabel.setText("SELL");
    }//GEN-LAST:event_sellLabelMouseExited

    private void openLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openLabelMouseReleased
        gameEngine.setParkOpen(!gameEngine.isParkOpen());
        setOpenLabel();
    }//GEN-LAST:event_openLabelMouseReleased

    private void repairLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repairLabelMouseReleased
        
        if(gameEngine.getJanitorsSize() > 0){
            gameEngine.janitorAddTask(building,this);
        }else{
            //no janitors
        }
    }//GEN-LAST:event_repairLabelMouseReleased

    public void removeRepairLabel(){
        if(frame.getBuildingMenu().getBuilding().equals(this.building)) {
            remove(repairLabel);
            conditionLabel.setText("Condition: 100");
            conditionStateLabel.setSize(200,10);
            conditionStateLabel.setBackground(new Color(0,255,51));
        }
    }
    
    private void roadAndDecorSellLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roadAndDecorSellLabelMouseEntered
        roadAndDecorSellLabel.setText("+$" + (int)(building.getPrice()*(60/100.0f)));
    }//GEN-LAST:event_roadAndDecorSellLabelMouseEntered

    private void roadAndDecorSellLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roadAndDecorSellLabelMouseExited
        roadAndDecorSellLabel.setText("SELL");
    }//GEN-LAST:event_roadAndDecorSellLabelMouseExited

    private void roadAndDecorSellLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roadAndDecorSellLabelMouseReleased
        gameEngine.sellBuilding(building.getLocation().getPos());
        gameEngine.ratePark();
        frame.getHud().getMoneyLabel().setText("$" + String.valueOf(gameEngine.playerMoney()));
        removeFromFrame();
    }//GEN-LAST:event_roadAndDecorSellLabelMouseReleased

    private boolean stillInPanel(Point2D p) {
        return (this.contains((Point)p));
    }
    
    public void removeFromFrame(){
       frame.getLayeredPane().remove(this);
       frame.repaint();
       instances--;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel conditionLabel;
    private javax.swing.JLabel conditionStateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel openLabel;
    private javax.swing.JLabel repairLabel;
    private javax.swing.JLabel roadAndDecorSellLabel;
    private javax.swing.JLabel sellLabel;
    private javax.swing.JLabel slotsLabel;
    private javax.swing.JLabel slotsStateLabel;
    private javax.swing.JLabel upgradeLabel;
    // End of variables declaration//GEN-END:variables

}
