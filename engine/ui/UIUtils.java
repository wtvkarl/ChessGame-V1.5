/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.ui;

import java.awt.Color;

/**
 *
 * @author 3095515
 */
public class UIUtils {
    
    public static Color promotionButtonBGColor = new Color(187, 255, 0);   

    public static boolean isPromotionCommand(String actionCommand) {
        return  "knight".equals(actionCommand) ||
                "bishop".equals(actionCommand) || 
                "rook".equals(actionCommand) ||
                "queen".equals(actionCommand);
    }

    public static boolean isSaveCommand(String actionCommand) {
        return "save".equals(actionCommand);
    }
    
    public static boolean isClearCommand(String actionCommand){
        return "clearFile".equals(actionCommand);
    }
    
    public static boolean isLoadCommand(String actionCommand){
        return "load".equals(actionCommand);
    }
}
