/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.ui;

import com.krlv.source.engine.Display;
import com.krlv.source.engine.board.FenReader;
import com.krlv.source.engine.board.Piece;
import com.krlv.source.engine.file.SaveFile;
import com.krlv.source.engine.loader.ImageUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author 3095515
 */
public class UI implements ActionListener{
        
    private static BufferedImage[] whiteIcons, blackIcons;
    private static Button[] promotionButtons;
    private static Button saveButton;
    private static Button clearFileButton;
    private static Button loadFileButton;
    private static String promotionButtonCommand = "";
    
    public static final int squareSize = 80;
    
    public UI(Display d){
        whiteIcons = ImageUtils.getWhitePromotionIcons();
        blackIcons = ImageUtils.getBlackPromotionIcons();
        initializePromotionButtons();   
        initializeSaveButtons();
        addAssetsToDisplay(d);
        
    }
    
    public void addAssetsToDisplay(Display d){
        for(Button b : promotionButtons){
            d.add(b);
        }
        
        d.add(saveButton);
        d.add(clearFileButton);
        d.add(loadFileButton);
        
    }
    
    // promotion button methods //
    
    private void initializePromotionButtons(){
        promotionButtons = new Button[4];
        promotionButtons[0] = new Button(whiteIcons[0], "queen", this, UIUtils.promotionButtonBGColor);
        promotionButtons[1] = new Button(whiteIcons[1], "rook", this, UIUtils.promotionButtonBGColor);
        promotionButtons[2] = new Button(whiteIcons[2], "bishop", this, UIUtils.promotionButtonBGColor);
        promotionButtons[3] = new Button(whiteIcons[3], "knight", this, UIUtils.promotionButtonBGColor);
        
        for(int i = 0; i < 4; i++){
            promotionButtons[i].setBounds(0, 0, squareSize, squareSize);
        }
    }
    
    public void setPromotionButtonBounds(Piece piece){
        int menuX = (FenReader.isWhitesTurn()) ? (piece.getCol() + 1) * squareSize : (7 - piece.getCol() + 1) * squareSize;
        int menuY = (FenReader.isWhitesTurn()) ? (8 - piece.getRow()) * squareSize : (piece.getRow() + 1) * squareSize ;
        
        int direction = (menuY < 400) ? 1 : -1;
        
        for(int i = 0; i < 4; i++){
            int buttonX = menuX;
            int buttonY = menuY + (squareSize * direction * i);
            //draw buttons w/ queen first for convenience
            promotionButtons[i].setLocation(buttonX, buttonY);
        }
    }
    
    public void setPromotionButtonIcons(){
        BufferedImage[] icons = (FenReader.isWhitesTurn()) ? 
                ImageUtils.getWhitePromotionIcons() :
                ImageUtils.getBlackPromotionIcons() ;
        
        for(int i = 0; i < icons.length; i++){
            promotionButtons[i].setIcon(icons[3-i]);
        }
        
    }
    
    public void setPromotionMenuVisible(boolean vis){
        for(Button button : promotionButtons){
            button.setVisible(vis);
        }
    }
    
    public static String getPromotionChoice(){
        return promotionButtonCommand;
    }
    
    public static void resetPromotionChoice(){
        promotionButtonCommand = "";
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(UIUtils.isPromotionCommand(ae.getActionCommand())){
            promotionButtonCommand = ae.getActionCommand();
        }
        else if(UIUtils.isSaveCommand(ae.getActionCommand())){
            SaveFile.saveBoard(FenReader.getFen());
        }
        else if(UIUtils.isClearCommand(ae.getActionCommand())){
            SaveFile.clearBoardFile();
        }
        else if(UIUtils.isLoadCommand(ae.getActionCommand())){
            SaveFile.loadFile();
        }
    }
    
    // end of promotion button methods //

    // start of save button methods //
    
    private void initializeSaveButtons(){
        saveButton = new Button(ImageUtils.getSaveIcon(), "save", this, Color.black);
        saveButton.setBounds(0,0,64,64);
        saveButton.setVisible(true);
        
        clearFileButton = new Button(ImageUtils.getClearFileIcon(), "clearFile", this, Color.black);
        clearFileButton.setBounds(64,0,64,64);
        clearFileButton.setVisible(true);
        
        loadFileButton = new Button(ImageUtils.getLoadFileIcon(), "load", this, Color.black);
        loadFileButton.setBounds(128,0,64,64);
        loadFileButton.setVisible(true);
    }
}
