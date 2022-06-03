 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.maps.KingCheckMap;
import com.krlv.source.engine.maps.PinnedMap;
import com.krlv.source.engine.moves.CaptureMove;
import com.krlv.source.engine.moves.EnPassantMove;
import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.PromotionMove;
import com.krlv.source.engine.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Pawn extends Piece{
    
    public Pawn(int index, int color){
        this.type = 1 * color;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }
    
    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> moves = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        boolean firstTimeMoving = (isWhite()) ? getRow() == 1 : getRow() == 6;
        int spacesForward = (firstTimeMoving) ? 2 : 1;
        
        for(int i = 1; i <= spacesForward; i++){
            int target = getIndex() + (8 * getColor() * i);
            if(board[target] == 0)
                if(KingCheckMap.kingIsInCheck(getColor())){
                    if(KingCheckMap.canBlockCheck(target)){
                        moves.add(new RegularMove(getIndex(), target));
                    }
                }
                else{
                    Move move = new RegularMove(getIndex(), target);
                    if(PinnedMap.isValidPinnedMove(move))
                        moves.add(move);
                }
            
            if(i == 1){
                int leftCapture = target + (-1);
                int rightCapture = target + 1;
                
                if(leftCapture / 8 == target / 8){ //same row
                    if(PieceUtils.isEnemy(board[leftCapture], getColor())){ //enemy piece
                        if(PieceUtils.isAboutToPromote(leftCapture, getColor())){
                            if(KingCheckMap.kingIsInCheck(getColor())){
                                if(KingCheckMap.canBlockCheck(leftCapture)){
                                    moves.add(new PromotionMove(getIndex(), leftCapture));
                                }
                            }
                            else{
                                Move move = new PromotionMove(getIndex(), leftCapture);
                                if(PinnedMap.isValidPinnedMove(move))
                                    moves.add(move);
                            }
                                
                        }
                        else{
                            if(KingCheckMap.kingIsInCheck(getColor())){
                                if(KingCheckMap.canBlockCheck(leftCapture)){
                                    moves.add(new CaptureMove(getIndex(), leftCapture));
                                }
                            }
                            else{
                                Move move = new CaptureMove(getIndex(), leftCapture);
                                if(PinnedMap.isValidPinnedMove(move))
                                    moves.add(move);
                            }
                        }
                    }
                    else if(ChessBoard.getCoordinate(leftCapture).equals(FenReader.getEnPassantSquare())){
                        Move move = new EnPassantMove(getIndex(), leftCapture);
                        if(PinnedMap.isValidPinnedMove(move))
                            moves.add(move);
                    }
                }
                
                if(rightCapture / 8 == target / 8){
                    if(PieceUtils.isEnemy(board[rightCapture], getColor())){
                        if(PieceUtils.isAboutToPromote(rightCapture, getColor())){
                            if(KingCheckMap.kingIsInCheck(getColor())){
                                if(KingCheckMap.canBlockCheck(rightCapture)){
                                    moves.add(new PromotionMove(getIndex(), rightCapture));
                                }
                            }
                            else{
                                Move move = new PromotionMove(getIndex(), rightCapture);
                                if(PinnedMap.isValidPinnedMove(move))
                                    moves.add(move);
                            }
                        }
                        else{
                            if(KingCheckMap.kingIsInCheck(getColor())){
                                if(KingCheckMap.canBlockCheck(rightCapture)){
                                    moves.add(new CaptureMove(getIndex(), rightCapture));
                                }
                            }
                            else{
                                Move move = new CaptureMove(getIndex(), rightCapture);
                                if(PinnedMap.isValidPinnedMove(move))
                                    moves.add(move);
                            }
                        }
                    }
                    else if(ChessBoard.getCoordinate(rightCapture).equals(FenReader.getEnPassantSquare())){
                        Move move = new EnPassantMove(getIndex(), rightCapture);
                        if(PinnedMap.isValidPinnedMove(move))
                            moves.add(move);
                    }
                }
            }
        }        
        return moves;
    }

    @Override
    public ArrayList<Integer> getAttackMap() {
        ArrayList<Integer> indecies = new ArrayList();
                
        int target = getIndex() + (8 * getColor());

        int leftCapture = target + (-1);
        int rightCapture = target + 1;

        if(leftCapture / 8 == target / 8){ //same row
            indecies.add(leftCapture);
        }
        
        if(rightCapture / 8 == target / 8){
            indecies.add(rightCapture);
        }
                        
        return indecies;
    }
    
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
