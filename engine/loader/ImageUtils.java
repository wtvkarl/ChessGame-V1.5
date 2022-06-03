/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.loader;


import com.krlv.source.engine.moves.Move;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author 3095515
 */
public class ImageUtils {
    
    private static final Color regularMoveColor = new Color(189, 184, 92);
    private static final Color captureMoveColor = new Color(209, 71, 71);
    private static final Color prevMoveColor = new Color(159, 94, 191);
    private static final Color castlingMoveColor = new Color(107, 156, 255);
    private static final Color promotionMoveColor = new Color(167, 227, 98);
    private static final Color checkedColor = new Color(255, 201, 201);
    private static final Color checkmatedColor = Color.red;
    
    private static BufferedImage[] whitePieces, blackPieces;
    private static BufferedImage[] whiteIcons, blackIcons;
    private static BufferedImage background;
    private static BufferedImage saveIcon;
    private static BufferedImage clearFileIcon;
    private static BufferedImage loadFileIcon;
    
    private static Color darkSquare = new Color(133, 95, 46);
    private static Color lightSquare = new Color(186, 133, 65);
    
    public static void loadAssets(){
        whitePieces = new BufferedImage[6];
        blackPieces = new BufferedImage[6];
        whiteIcons = new BufferedImage[4];
        blackIcons = new BufferedImage[4];
        
        try{
            background = ImageIO.read(new File("res/imgs/background.png"));
            
            whitePieces[0] = ImageIO.read(new File("res/imgs/whitepawn.png"));
            whitePieces[1] = ImageIO.read(new File("res/imgs/whiteknight.png"));
            whitePieces[2] = ImageIO.read(new File("res/imgs/whitebishop.png"));
            whitePieces[3] = ImageIO.read(new File("res/imgs/whiterook.png"));
            whitePieces[4] = ImageIO.read(new File("res/imgs/whitequeen.png"));
            whitePieces[5] = ImageIO.read(new File("res/imgs/whiteking.png"));
            
            blackPieces[0] = ImageIO.read(new File("res/imgs/blackpawn.png"));
            blackPieces[1] = ImageIO.read(new File("res/imgs/blackknight.png"));
            blackPieces[2] = ImageIO.read(new File("res/imgs/blackbishop.png"));
            blackPieces[3] = ImageIO.read(new File("res/imgs/blackrook.png"));
            blackPieces[4] = ImageIO.read(new File("res/imgs/blackqueen.png"));
            blackPieces[5] = ImageIO.read(new File("res/imgs/blackking.png"));
            
            saveIcon = ImageIO.read(new File("res/imgs/save.png"));
            clearFileIcon = ImageIO.read(new File("res/imgs/clear.png"));
            loadFileIcon = ImageIO.read(new File("res/imgs/load.png"));
        }
        catch(IOException ie){}
        
        for(int i = 0; i < 4; i++){
            whiteIcons[i] = whitePieces[i+1];
            blackIcons[i] = blackPieces[i+1];
        }
    }
    
    public static BufferedImage getPieceImageByType(int type){
       if(type > 0)
           return whitePieces[type - 1];
       return blackPieces[Math.abs(type) - 1];
    }
    
    public static BufferedImage getBackground(){
        return background;
    }
    
    public static Color getLightColor(){
        return lightSquare;
    }
    
    public static Color getDarkColor(){
        return darkSquare;
    }
    
    public static Color getPrevMoveColor(){
        return prevMoveColor;
    }
    
    public static Color getCheckedColor(){
        return checkedColor;
    }
    
    public static Color getMoveColor(Move move){
        switch(move.getType()){
            case REGULAR : return regularMoveColor;
            case CAPTURE : return captureMoveColor;
            case ENPASSANT : return captureMoveColor;
            case CASTLING : return castlingMoveColor;
            case PROMOTION : return promotionMoveColor;
            default : return Color.black;
        }
    }
    
    public static BufferedImage getWhitePromotionIcon(int i){
        return whiteIcons[i];
    }
    
    public static BufferedImage getBlackPromotionIcon(int i){
        return blackIcons[i];
    }
    
    public static BufferedImage[] getWhitePromotionIcons(){
        return whiteIcons;
    }
    
    public static BufferedImage[] getBlackPromotionIcons(){
        return blackIcons;
    }
    
    public static Color getCheckmateColor(){
        return checkmatedColor;
    }
    
    public static BufferedImage getSaveIcon(){
        return saveIcon;
    }

    public static BufferedImage getClearFileIcon() {
        return clearFileIcon;
    }

    public static BufferedImage getLoadFileIcon() {
        return loadFileIcon;
    }
}
