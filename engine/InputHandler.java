/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author 3095515
 */
public class InputHandler implements MouseMotionListener, MouseListener, KeyListener{
    private static boolean isPressed, debugModeEnabled;
    private static boolean resetButtonPressed;
    private static int mouseX, mouseY;
    
    public static int getX(){
        return mouseX;
    }
    
    public static int getY(){
        return mouseY;
    }
    
    public static boolean isPressed(){
        return isPressed;
    }
    
    public static boolean debugModeEnabled(){
        return debugModeEnabled;
    }
    
    public static boolean resetButtonPressed(){
        return resetButtonPressed;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == 32){
            debugModeEnabled = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyCode() == 32){
            debugModeEnabled = false;
        }
    }
}
