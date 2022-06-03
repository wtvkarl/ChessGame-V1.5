/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.states;

import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.board.FenReader;
import com.krlv.source.engine.board.Piece;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */

public class BoardState {
    
    private static boolean checkmated;
    private static int winnerColor;
    
    public static boolean isCheckmate(){
        ArrayList<Piece> pieces = (FenReader.isWhitesTurn()) ? ChessBoard.getWhitePieces() : ChessBoard.getBlackPieces();
        for(Piece piece : pieces){
            if(!piece.getValidMoves().isEmpty()){
                checkmated = false;
                winnerColor = 0;
                return false;
            }
        }
        checkmated = true;
        winnerColor = (FenReader.isWhitesTurn()) ? -1 : 1;
        return true;
    }
    
    public static boolean isCheckmated(){
        return checkmated;
    }
    
    public static int getWinnerColor(){
        return winnerColor;
    }
    
}
