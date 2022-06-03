/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author 3095515
 */
public class Button extends JButton{
    
    private static UI ui;
    
    public Button(BufferedImage img, String com, UI ui, Color bg){
        this.setMinimumSize(new Dimension(ui.squareSize, ui.squareSize));
        this.setDoubleBuffered(true);
        this.setIcon(new ImageIcon(img));
        this.setActionCommand(com);
        this.addActionListener(ui);
        this.setVisible(false);
        this.ui = ui;
        setBGColor(bg);
        validate();
    }
    
    public void setBGColor(Color c){
        this.setBackground(c);
    }
    
    public void setIcon(BufferedImage image){
        this.setIcon(new ImageIcon(image));
    }
    
    @Override
    public String toString(){
        return getActionCommand();
    }
}
