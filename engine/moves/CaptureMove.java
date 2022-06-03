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
public class CaptureMove extends Move{
    
    public CaptureMove(int start, int target){
        this.startPiece = ChessBoard.getPieceAt(start);
        this.targetPiece = ChessBoard.getPieceAt(target);
        this.startIndex = startPiece.getIndex();
        this.targetIndex = targetPiece.getIndex();
        this.type = MoveType.CAPTURE;
    }

    @Override
    public void execute() {
        ChessBoard.getBoard()[targetIndex] = startPiece;
        startPiece.setIndex(targetIndex);
        ChessBoard.getBoard()[startIndex] = new Blank(startIndex);
        if(targetPiece.isWhite())
            ChessBoard.getWhitePieces().remove(targetPiece);
        else{
            ChessBoard.getBlackPieces().remove(targetPiece);
        }
    }
    
}
