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


public class Bishop extends Piece{
    
    public Bishop(int index, int color){
        this.type = 3 * color;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }

    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> moves = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
                
        for(int i = 4; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(PieceUtils.isInBoard(target)){
                    if(KingCheckMap.kingIsInCheck(getColor())){
                        if(board[target] == 0){
                            if(KingCheckMap.canBlockCheck(target))
                                moves.add(new RegularMove(getIndex(), target));
                        }
                        else{
                            if(PieceUtils.isEnemy(board[target], getType())){
                                if(KingCheckMap.canBlockCheck(target))
                                    moves.add(new CaptureMove(getIndex(), target));
                            }      
                            break;
                        }
                    }
                    else{
                        if(board[target] == 0){
                            Move move = new RegularMove(getIndex(), target);
                            if(PinnedMap.isValidPinnedMove(move))
                                moves.add(move);
                        }
                        else{ 
                            if(PieceUtils.isEnemy(board[target], getType())){
                                Move move = new CaptureMove(getIndex(), target);
                                if(PinnedMap.isValidPinnedMove(move))
                                    moves.add(move);
                            }      
                            break;
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
        int[] board = ChessBoard.getTypeBoard();
        
        for(int i = 4; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(PieceUtils.isInBoard(target)){
                    indecies.add(target);
                    if((board[target] != 0 && Math.abs(board[target]) != 6) || PieceUtils.isFriendly(getColor(), board[target]))
                        break;
                }
            }
        }

        return indecies;
    }
    
    @Override 
    public ArrayList<Integer> getKingAttackPath(){
        Piece enemyKing = (isWhite()) ? ChessBoard.getBlackKing() : ChessBoard.getWhiteKing();
        ArrayList<Integer> path = new ArrayList();
        boolean encounteredKing = false;
        
        for(int i = 4; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 0; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(PieceUtils.isInBoard(target)){
                    path.add(target);
                    
                    if(target == enemyKing.getIndex()){
                        encounteredKing = true;
                        break;
                    }
                }
            }
            if(encounteredKing){
                return path;
            }
            path.clear();
        }
        return path;
    }

    @Override
    public ArrayList<Integer> getVirtualKingAttackMap(int[] virtualBoard) {
        ArrayList<Integer> indecies = new ArrayList();
        
        for(int i = 4; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + (ChessBoard.getDirectionOffsets()[i] * j);
                if(PieceUtils.isInBoard(target)){
                    indecies.add(target);
                    if((virtualBoard[target] != 0 && Math.abs(virtualBoard[target]) != 6) || PieceUtils.isFriendly(getColor(), virtualBoard[target]))
                        break;
                }
            }
        }

        return indecies;
    }
    
}
