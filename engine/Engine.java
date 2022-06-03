/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine;

import javax.swing.JFrame;

/**
 *
 * @author 3095515
 */
public class Engine {
    public Engine() {
        JFrame window = new JFrame("Chess Engine v1.4");
        Display display = new Display();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(display);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        display.start();
    }
}
