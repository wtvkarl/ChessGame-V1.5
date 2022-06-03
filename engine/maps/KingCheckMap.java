/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.maps;

import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.board.FenReader;
import com.krlv.source.engine.board.Piece;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class KingCheckMap {
        
    private static int[] squaresToBlock = new int[64];
    private static boolean canBlock = false;
    
    public static Piece getCheckedKing(){
        
        if(FenReader.isWhitesTurn()){
            if(AttackMap.isInEnemySquares(ChessBoard.getWhiteKing().getIndex())){
                return ChessBoard.getWhiteKing();
            }
        }
                
        if(FenReader.isBlacksTurn()){
            if(AttackMap.isInEnemySquares(ChessBoard.getBlackKing().getIndex())){
               return ChessBoard.getBlackKing();
            }
        }
        
        return null;
    }
    
    public static ArrayList<Piece> getAttackers(){
        Piece king = getCheckedKing();
        //if the king is not in check, there must be 0 attackers.
        if(king == null)
            return new ArrayList();
        
        ArrayList<Piece> enemies = king.isWhite() ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces();
        ArrayList<Piece> attackers = new ArrayList();
        
        for(Piece piece : enemies){
            //we dont count the king, kings cant be next to each other
            if(!piece.isKing()){ 
                //if the checked king is in the piece's attack map, it must be an attacker
                if(piece.getAttackMap().contains(king.getIndex())){ 
                    attackers.add(piece);
                }
            }
        }
        
        return attackers;
        
    }
    
    public static ArrayList<Piece> getVirtualAttackers(Piece king, int[] virtualBoard){
        //if the king is not in check, there must be 0 attackers.
        
        ArrayList<Piece> enemies = king.isWhite() ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces();
        ArrayList<Piece> attackers = new ArrayList();
        
        for(Piece piece : enemies){
            //we dont count the king, kings cant be next to each other
            if(!piece.isKing()){ 
                //if the checked king is in the piece's attack map, it must be an attacker
                if(piece.getVirtualKingAttackMap(virtualBoard).contains(king.getIndex())){ 
                    attackers.add(piece);
                }
            }
        }
        
        return attackers;
    }
    
    public static ArrayList<Integer> getAttackerPath(){
        ArrayList<Piece> attackers = getAttackers();
        //if there is more than one attacker, we cannot block.
        if(attackers.size() == 1){
            return attackers.get(0).getKingAttackPath();
        }   
        
        return new ArrayList();
    }
    
    public static void updateCheckBlockMap(){
        if(getCheckedKing() == null){
            squaresToBlock = new int[64];
            return;
        }
        
        ArrayList<Piece> attackers = getAttackers();
        if(attackers.size() == 1){
            canBlock = true;
            for(Integer in : getAttackerPath()){
                squaresToBlock[in] = 1;
            }
        }
        else{
            canBlock = false;
        }
    }   
    
    public static void printCheckBlockMap(){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                System.out.print(squaresToBlock[row * 8 + col] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }
    
    public static boolean kingIsInCheck(int color){
        Piece king = getCheckedKing();
        if(king == null)
            return false;
        
        return color == king.getColor();
    }
    
    public static int[] getCheckBlockMap(){
        return squaresToBlock;
    }
    
    public static boolean canBlockCheck(int index){
        return canBlock && squaresToBlock[index] == 1;
    }
}
