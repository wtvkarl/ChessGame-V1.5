/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.InputHandler;

/**
 *
 * @author 3095515
 */
public class BoardUtils {
    
    public static boolean mouseIsInBoard(){
        int x = InputHandler.getX();
        int y = InputHandler.getY();
        return (x >= 80 && x <= 720) && (y >= 80 && y <= 720);
    }

    public static boolean isColorToMove(Piece draggedPiece) {
        return (FenReader.isWhitesTurn()) ? draggedPiece.getColor() == 1 : draggedPiece.getColor() == -1;
    }
    
}
