/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.moves;

import com.krlv.source.engine.board.Blank;
import com.krlv.source.engine.board.ChessBoard;

/**
 *
 * @author 3095515
 */
public class CastlingMove extends Move{

    private int newRookIndex, newKingIndex;
    
    public CastlingMove(int start, int target, boolean kingside){
        startPiece = ChessBoard.getPieceAt(start);
        targetPiece = ChessBoard.getPieceAt(target);
        startIndex = start;
        targetIndex = target;
        type = MoveType.CASTLING;
        
        newKingIndex = (kingside) ? startIndex + 2 : startIndex - 2;
        newRookIndex = (kingside) ? newKingIndex - 1 : newKingIndex + 1;
    }
    
    @Override
    public void execute() {
        ChessBoard.getBoard()[newKingIndex] = startPiece;
        ChessBoard.getBoard()[newRookIndex] = targetPiece;
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
        ChessBoard.getBoard()[targetIndex] = new Blank(targetIndex);
        targetPiece.setIndex(newRookIndex);
        startPiece.setIndex(newKingIndex);
    }
    
    @Override 
    public int getTargetIndex(){
        return newKingIndex;
    }
    
}
