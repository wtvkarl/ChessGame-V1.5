/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.maps.KingCheckMap;
import com.krlv.source.engine.maps.PinnedMap;
import com.krlv.source.engine.moves.CaptureMove;
import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Knight extends Piece{
    
    private final int[] jumpOffsets = {17, 10, 6, -15, -17, -10, -6, 15};
    
    public Knight(int index, int color){
        this.type = 2 * color;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> moves = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(Integer in : jumpOffsets){
            int target = getIndex() + in;
            if(PieceUtils.isInBoard(target)){
                if(Math.abs((getIndex() % 8) - (target % 8)) <= 2){
                    if(KingCheckMap.kingIsInCheck(getColor())){
                        if(KingCheckMap.canBlockCheck(target)){
                            if(PieceUtils.isEnemy(board[target], getType()) && !PieceUtils.isBlank(board[target])){
                                moves.add(new CaptureMove(getIndex(), target));
                            }
                            else if(board[target] == 0 && Math.abs(board[target]) != 6){
                                moves.add(new RegularMove(getIndex(), target));
                            }
                        }
                    }
                    else{
                        if(PieceUtils.isEnemy(board[target], getType()) && !PieceUtils.isBlank(board[target])){
                            Move move = new CaptureMove(getIndex(), target);
                            if(PinnedMap.isValidPinnedMove(move))
                                moves.add(move);
                        }
                        else if(board[target] == 0 && Math.abs(board[target]) != 6){
                            Move move = new RegularMove(getIndex(), target);
                            if(PinnedMap.isValidPinnedMove(move))
                                moves.add(move);
                        }
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Integer> getAttackMap() {
        ArrayList<Integer> indecies = new ArrayList();
        
        for(Integer in : jumpOffsets){
            int target = getIndex() + in;
            if(PieceUtils.isInBoard(target)){
                if(Math.abs((getIndex() % 8) - (target % 8)) <= 2){
                    indecies.add(target);
                }
            }
        }        
        return indecies;

    }
    
    //you can't block a knight's attack since they can jump, so return only the piece index
    @Override
    public ArrayList<Integer> getKingAttackPath() {
        ArrayList<Integer> path = new ArrayList();
        path.add(getIndex());
        return path;
    }
    
    @Override
    public ArrayList<Integer> getVirtualKingAttackMap(int[] virtualBoard) {
        return getAttackMap();
    }
    
}
