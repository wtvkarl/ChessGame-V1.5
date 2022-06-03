/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

/**
 *
 * @author 3095515
 */
public class PieceUtils {
    
    public static boolean isInBoard(int index){
        return index >= 0 && index <= 63;
    }
    
    public static boolean isFriendly(int c1, int c2){
        return Integer.signum(c1) == Integer.signum(c2);
    }
    
    public static boolean isEnemy(int c1, int c2){
        return Integer.signum(c1) != Integer.signum(c2) && !isBlank(c1);
    }
    
    public static boolean isBlank(int type){
        return type == 0;
    }
    
    public static boolean isAboutToPromote(int target, int color){
        return (Integer.signum(color) == 1) ? target / 8 == 7 : target / 8 == 0;
    }
    
    public static boolean isSlidingPiece(int type){
        int abs = Math.abs(type);
        //bishop, rook, or queen
        return abs == 3 || abs == 4 || abs == 5;
    }
    
}
