/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.moves;

import com.krlv.source.engine.board.Bishop;
import com.krlv.source.engine.board.Blank;
import com.krlv.source.engine.board.ChessBoard;
import com.krlv.source.engine.board.Knight;
import com.krlv.source.engine.board.Piece;
import com.krlv.source.engine.board.Queen;
import com.krlv.source.engine.board.Rook;
import com.krlv.source.engine.ui.UI;

/**
 *
 * @author 3095515
 */
public class PromotionMove extends Move {

    Piece chosenPiece;
    
    public PromotionMove(int start, int target){
        startPiece = ChessBoard.getPieceAt(start);
        startIndex = start;
        targetPiece = ChessBoard.getPieceAt(target);
        targetIndex = target;
        type = MoveType.PROMOTION;
    }
    
    
    @Override
    public void execute() {        
        while(UI.getPromotionChoice().isEmpty()){
            System.out.print("");    
            String choice = UI.getPromotionChoice();
            switch(choice){
                case "knight" -> chosenPiece = new Knight(targetIndex, startPiece.getColor());
                case "bishop" -> chosenPiece = new Bishop(targetIndex, startPiece.getColor());
                case "rook" -> chosenPiece = new Rook(targetIndex, startPiece.getColor());
                case "queen" -> chosenPiece = new Queen(targetIndex, startPiece.getColor());
            }
        }
        UI.resetPromotionChoice();
        
        if(startPiece.isWhite()){
            ChessBoard.getWhitePieces().remove(startPiece);
            ChessBoard.getWhitePieces().add(chosenPiece);
        }
        else{
            ChessBoard.getBlackPieces().remove(startPiece);
            ChessBoard.getBlackPieces().add(chosenPiece);
        }
        
        
        ChessBoard.getBoard()[targetIndex] = chosenPiece;
        startPiece.setIndex(targetIndex);
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
    }
    
}
