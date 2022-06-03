/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.krlv.source.engine.maps;

import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.board.FenReader;
import com.krlv.source.engine.board.Piece;
import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.MoveType;
import java.util.ArrayList;

/**
 *
 * @author kvill
 */
public class PinnedMap {
    
    private static int[] pinnedPieces = new int[64];
    
    public static void updatePinnedMap(){
        pinnedPieces = new int[64];
        Piece king = (FenReader.isWhitesTurn()) ? ChessBoard.getWhiteKing() : ChessBoard.getBlackKing();
        ArrayList<Piece> pieces = (king.isWhite()) ? ChessBoard.getBlackPieces() : ChessBoard.getWhitePieces();
          
    }
    
    public static void printPinnedMap(){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                System.out.print(pinnedPieces[row * 8 + col] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }
    
    public static int[] getPinnedMap(){
        return pinnedPieces;
    }
    
    public static boolean isValidPinnedMove(Move move){
        Piece king = (move.getStartPiece().isWhite()) ? ChessBoard.getWhiteKing() : ChessBoard.getBlackKing();
        
        // by making the square where the start piece of the move is blank,
        // we essentially "move" the piece, because to move a piece is to move it
        // to a different location, so to check if the move is valid, we just check if the 
        // king is in check in this virtual position
        
        int[] board = ChessBoard.getTypeBoard();
        
        board[move.getStartIndex()] = 0; 
        board[move.getTargetIndex()] = 1; 
        
        int[] virtualAttackMap = AttackMap.getVirtualAttackMap(board);
        
        if(move.getType() == MoveType.CAPTURE){
            ArrayList<Piece> attackers = KingCheckMap.getVirtualAttackers(king, board);
            
            board[move.getStartIndex()] = move.getStartPiece().getType(); 
            board[move.getTargetIndex()] = move.getTargetPiece().getType(); 
            
            if(attackers.size() == 1 && move.getTargetIndex() == attackers.get(0).getIndex())
                return true;
        }

        boolean valid = !AttackMap.isInEnemySquares(king.getIndex(), virtualAttackMap);
            
        board[move.getStartIndex()] = move.getStartPiece().getType(); 
        board[move.getTargetIndex()] = move.getTargetPiece().getType(); 
        
        return valid;
        
    }
    
    private static void printVirtualAttackMap(int[] board){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                System.out.print(board[row * 8 + col] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }
    
}
