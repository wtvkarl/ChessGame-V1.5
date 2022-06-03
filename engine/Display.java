/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine;

import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.loader.ImageUtils;
import com.krlv.source.engine.ui.UI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author 3095515
 */
public class Display extends JPanel implements Runnable{
    private Thread gameThread;
    private boolean running;
    
    private InputHandler inputHandler;
    private ChessBoard board;
    private static UI ui;
        
    public Display() {
        this.setLayout(null);
        this.setFocusable(true); //important for keylisteners
        this.setPreferredSize(new Dimension(800,800));
        this.setDoubleBuffered(true);
        inputHandler = new InputHandler();
        this.addMouseListener(inputHandler);
        this.addMouseMotionListener(inputHandler);
        this.addKeyListener(inputHandler);
        
        ImageUtils.loadAssets();
        ui = new UI(this);
        board = new ChessBoard(ui);
    }
    
    public void start(){
        running = true;
        gameThread = new Thread(this, "gameThread");
        gameThread.start();
    }
    
    
    @Override
    public void run() {
        int targetUpdates = 60;
        double nsPerSecond = 1_000_000_000.0;
        double drawInterval = nsPerSecond/targetUpdates;
        double delta = 0;
        long then = System.nanoTime();
        long now;
        boolean shouldRender = false;
        
        while(running){
            now = System.nanoTime();
            delta += (now - then) / drawInterval;
            then = now;
            
            if(delta >= 1){
                update();
                delta--;
                shouldRender = true;
            }
            
            if(shouldRender){
                repaint();
                shouldRender = false;
            }
            
        }
    }
    
    private void update(){
        board.update();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) (g);
        board.draw(g2d);
    }
}
