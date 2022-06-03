/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.moves;

import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class MoveHandler {
    public static Move getMove(ArrayList<Move> validMoves, int targetPieceIndex) {
        if(validMoves == null)
            return null;
        if(validMoves.isEmpty())
            return null;
        
        for(Move move : validMoves){
            if(move.getTargetIndex() == targetPieceIndex)
                return move;
        }
        return null;
    }
}
