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
public class EnPassantMove extends Move{
    
    public EnPassantMove(int start, int target){
        startPiece = ChessBoard.getPieceAt(start);
        startIndex = start;
        targetPiece = ChessBoard.getPieceAt(target);
        targetIndex = target;
        type = MoveType.ENPASSANT;
    }
    
    @Override
    public void execute(){
        ChessBoard.getBoard()[targetIndex] = startPiece;
        startPiece.setIndex(targetIndex);
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
        if(targetPiece.isWhite()){
            ChessBoard.getBoard()[targetIndex + 8] = new Blank(targetIndex + 8);
            ChessBoard.getWhitePieces().remove(targetPiece);
        }
        else{
            ChessBoard.getBoard()[targetIndex - 8] = new Blank(targetIndex - 8);
            ChessBoard.getBlackPieces().remove(targetPiece);
        }
    }
    
}
