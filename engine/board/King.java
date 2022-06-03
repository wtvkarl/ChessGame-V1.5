/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.maps.AttackMap;
import com.krlv.source.engine.moves.CaptureMove;
import com.krlv.source.engine.moves.CastlingMove;
import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.RegularMove;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class King extends Piece{
    
    public King(int index, int color){
        this.type = 6 * color;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }

    @Override
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> moves = new ArrayList();
        int[] board = ChessBoard.getTypeBoard();
        
        for(int i = 0; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 1; j <= distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(PieceUtils.isInBoard(target) && !AttackMap.isInEnemySquares(target)){
                    if(board[target] == 0){
                        moves.add(new RegularMove(getIndex(), target));
                    }
                    else{ 
                        if(Integer.signum(board[target] * getColor()) == -1){
                            moves.add(new CaptureMove(getIndex(), target));
                        }      
                    }
                }
                break;
            }
        }

        moves.addAll(getCastlingMoves());
        
        return moves;
    }
    
    private ArrayList<Move> getCastlingMoves(){
        ArrayList<Move> moves = new ArrayList();
        
        String rights = (isWhite()) ? FenReader.getWhiteCastlingRights().toLowerCase() : 
                                      FenReader.getBlackCastlingRights().toLowerCase();
        if(rights.isEmpty())
            return moves;
        
        Piece kingsideRook = (isWhite()) ? ChessBoard.getBoard()[7] : ChessBoard.getBoard()[63];
        Piece queensideRook = (isWhite()) ? ChessBoard.getBoard()[0] : ChessBoard.getBoard()[56];
                
        if(rights.contains("k")){ //kingside castling
            if(kingsideRook.isRook()){ 
                int[] board = ChessBoard.getTypeBoard();
                if(getRow() == 0){ //white's side
                    if(board[5] == 0 && board[6] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(5) && !AttackMap.isInEnemySquares(6)){
                            moves.add(new CastlingMove(getIndex(), kingsideRook.getIndex(), true));
                        }
                    }
                }
                else{
                    if(board[61] == 0 && board[62] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(61) && !AttackMap.isInEnemySquares(62)){
                            moves.add(new CastlingMove(getIndex(), kingsideRook.getIndex(), true));
                        }
                    }
                }
            }
        }
        
        if(rights.contains("q")){ //queenside castling
            if(queensideRook.isRook()){ 
                int[] board = ChessBoard.getTypeBoard();
                if(getRow() == 0){ //white's side
                    if(board[1] == 0 && board[2] == 0 && board[3] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(2) && !AttackMap.isInEnemySquares(3)){
                            moves.add(new CastlingMove(getIndex(), queensideRook.getIndex(), false));
                        }
                    }
                }
                else{
                    if(board[57] == 0 && board[58] == 0 && board[59] == 0){ //if the squares between king and rook are blank
                        if(!AttackMap.isInEnemySquares(58) && !AttackMap.isInEnemySquares(59)){
                            moves.add(new CastlingMove(getIndex(), queensideRook.getIndex(), false));
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
        
        for(int i = 0; i < 8; i++){
            int distToEdge = ChessBoard.getDistToEdge(getIndex(), i);
            for(int j = 1; j < distToEdge; j++){
                int target = getIndex() + ChessBoard.getDirectionOffsets()[i] * j;
                if(PieceUtils.isInBoard(target)){
                    indecies.add(target);
                }
                break;
            }
        }
        
        return indecies;
    }

    @Override
    public ArrayList<Integer> getKingAttackPath() {return new ArrayList();}
    
    @Override
    public ArrayList<Integer> getVirtualKingAttackMap(int[] virtualBoard) {
        return getAttackMap();
    }
    
}
