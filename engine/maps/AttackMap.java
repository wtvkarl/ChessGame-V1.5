/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.maps;

import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.board.FenReader;
import com.krlv.source.engine.board.Piece;
import com.krlv.source.engine.board.PieceUtils;

/**
 *
 * @author 3095515
 */
public class AttackMap {
    
    private static int[] attackMap = new int[64];
    
    public static void updateAttackMap(){
        attackMap = new int[64];
        for(Piece piece : (FenReader.isWhitesTurn()) ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces()){
            for(int index : piece.getAttackMap()){
                if(PieceUtils.isInBoard(index))
                    attackMap[index] = 1;
            }
        }
    }
    
    public static int[] getVirtualAttackMap(int[] virtualBoard){
        int[] virtualAttackMap = new int[64];
        for(Piece piece : (FenReader.isWhitesTurn()) ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces()){
            for(int index : piece.getVirtualKingAttackMap(virtualBoard)){
                if(PieceUtils.isInBoard(index))
                    virtualAttackMap[index] = 1;
            }
        }
        return virtualAttackMap;
    }
    
    public static boolean isInEnemySquares(int targetIndex){  
        return attackMap[targetIndex] == 1;
    }
    
    public static boolean isInEnemySquares(int targetIndex, int[] virtualBoard){
        return virtualBoard[targetIndex] == 1;
    }
    
    public static void printAttackMap(){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                System.out.print(attackMap[row * 8 + col] + " ");
            }
            System.out.println();
        }
    }
    
    public static int[] getAttackMap(){
        return attackMap;
    }
}
