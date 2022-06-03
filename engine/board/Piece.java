/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.moves.Move;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public abstract class Piece {
    public int type, row, col, index;
    
    public abstract ArrayList<Move> getValidMoves();
    public abstract ArrayList<Integer> getAttackMap();
    public abstract ArrayList<Integer> getKingAttackPath();
    public abstract ArrayList<Integer> getVirtualKingAttackMap(int[] virtualBoard);
    
    // 1 is white, -1 is black
    public int getColor(){
        return Integer.signum(type);
    }
    
    public int getType(){
        return type;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getIndex(){
        return index;
    }
    
    public String getCoordinate(){
        return ChessBoard.getCoordinate(index);
    }
    
    public void setIndex(int i){
        index = i;
        row = i / 8;
        col = i % 8;
    }
    
    public boolean isPawn(){
        return Math.abs(type) == 1;
    }
    
    public boolean isKnight(){
        return Math.abs(type) == 2;
    }
    
    public boolean isBishop(){
        return Math.abs(type) == 3;
    }
    
    public boolean isRook(){
        return Math.abs(type) == 4;
    }
    
    public boolean isQueen(){
        return Math.abs(type) == 5;
    }
    
    public boolean isKing(){
        return Math.abs(type) == 6;
    }
    
    public boolean isBlank(){
        return type == 0;
    }
    
    public boolean isWhite(){
        return type > 0;
    }
    
    public boolean isBlack(){
        return type < 0;
    }
    
    @Override
    public String toString(){
        return String.format("%d @ %s", getType(), getCoordinate());
    }
}
