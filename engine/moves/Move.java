/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.moves;

import com.krlv.source.engine.board.Piece;

/**
 *
 * @author 3095515
 */
public abstract class Move {
    
    public MoveType type;
    public Piece startPiece, targetPiece;
    public int startIndex, targetIndex;
    
    public abstract void execute();
    
    public MoveType getType(){
        return type;
    }
    
    public Piece getStartPiece(){
        return startPiece;
    }
    
    public Piece getTargetPiece(){
        return targetPiece;
    }
    
    public int getStartIndex(){
        return startIndex;
    }
    
    public int getTargetIndex(){
        return targetIndex;
    }
    
    public int getRowDist(){
        return Math.abs(startIndex / 8 - targetIndex / 8);
    }
    
    public int getColDist(){
        return Math.abs(startIndex % 8 - targetIndex % 8);
    }
}
