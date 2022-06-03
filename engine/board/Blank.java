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
public class Blank extends Piece{
    
    public Blank(int index){
        this.type = 0;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }

    @Override
    public ArrayList<Move> getValidMoves() {return new ArrayList();}

    @Override
    public ArrayList<Integer> getAttackMap() {return new ArrayList();}

    @Override
    public ArrayList<Integer> getKingAttackPath() {return new ArrayList();}  
    
    @Override
    public ArrayList<Integer> getVirtualKingAttackMap(int[] virtualBoard) {return new ArrayList();}
}
