/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.MoveType;

/**
 *
 * @author 3095515
 */
public class FenReader {
    
    private String fen;
    
    private static String piecePositions, colorToMove, castlingRights,
                          enPassantSquare, HMClock, FMClock;
    
    private static int halfMoves, fullMoves;
    
    public FenReader(String fen){
        this.fen = fen;
        initFenParts(fen);
    }
    
    private void initFenParts(String str){
        String[] strs = str.split(" ");
        piecePositions = strs[0];
        colorToMove = strs[1];
        castlingRights = strs[2];
        enPassantSquare = strs[3];
        HMClock = strs[4];
        halfMoves = Integer.parseInt(HMClock);
        FMClock = strs[5];
        fullMoves = Integer.parseInt(FMClock);
    }
    
    public static String getPiecePositions(){
        return piecePositions;
    }
    
    public static String getColorToMove(){
        return colorToMove;
    }
    
    public static String getCastlingRights(){
        return castlingRights;
    }
    
    public static String getEnPassantSquare(){
        return enPassantSquare;
    }
    
    public static String getHalfMoves(){
        return HMClock;
    }
    
    public static String getFullMoves(){
        return FMClock;
    }
    
    public static boolean isWhitesTurn(){
        return colorToMove.equals("w");
    }
    
    public static boolean isBlacksTurn(){
        return colorToMove.equals("b");
    }
    
    // mutators //
    
    private static char getFenNotationByType(Piece piece){
        switch(Math.abs(piece.getType())){
            case 1 : return (piece.getType() > 0) ? 'P' : 'p'; 
            case 2 : return (piece.getType() > 0) ? 'N' : 'n'; 
            case 3 : return (piece.getType() > 0) ? 'B' : 'b';
            case 4 : return (piece.getType() > 0) ? 'R' : 'r';
            case 5 : return (piece.getType() > 0) ? 'Q' : 'q';
            case 6 : return (piece.getType() > 0) ? 'K' : 'k';
            default : return '*';
        }
    }
    
    public static void updatePiecePositions(Piece[] board){
        String str = "";
        for(int row = 7; row >= 0; row--){
            int blanks = 0;
            for(int col = 0; col < 8; col++){
                Piece piece = board[row * 8 + col];
                if(!piece.isBlank()){ 
                    if(blanks > 0){
                        str += blanks;
                        blanks = 0;
                    }
                    str += getFenNotationByType(piece);
                }
                else{
                    blanks++;
                }
            }
            if(blanks > 0)
                str += blanks;
            if(row != 0)
                str += "/";
        }
        piecePositions = str;
    }
    
    public static void switchTurn(){
        colorToMove = (isWhitesTurn()) ? "b" : "w";
    }
    
    public static void updateCastlingRights(Move move){
        String rights = FenReader.getCastlingRights();
        if(move.getStartPiece().isKing()){
            if(move.getStartPiece().isWhite()){
                rights = rights.replace("K", "");
                rights = rights.replace("Q", "");
            }
            else if(move.getStartPiece().isBlack()){
                rights = rights.replace("k", "");
                rights = rights.replace("q", "");
            }
        }
        else if(move.getStartPiece().isRook()){
            if(move.getStartPiece().isWhite()){
                if(move.getStartIndex() == 0) //queenside
                    rights = rights.replace("Q", "");
                else if(move.getStartIndex() == 7) //kingside
                    rights = rights.replace("K", "");
            }
            else if(move.getStartPiece().isBlack()){
                if(move.getStartIndex() == 56) //queenside
                    rights = rights.replace("q", "");
                else if(move.getStartIndex() == 63) //kingside
                    rights = rights.replace("k", "");
            }
        }
        castlingRights = rights;
    }
    
    public static void updateEnPassantSquareCoordinate(Move move){
        if(!move.getStartPiece().isPawn() || move.getType() != MoveType.REGULAR || move.getRowDist() != 2){
            enPassantSquare = "-";
            return;
        }
        
        int indexBehindPawn = move.getStartPiece().isWhite() ?
                move.getStartIndex() + 8 :
                move.getStartIndex() - 8 ;     
        
        enPassantSquare = ChessBoard.getCoordinate(indexBehindPawn);
    }
    
    public static void updateMoveClocks(Move move){
        if(move.getStartPiece().isBlack())
            incrementFullMoveClock();
        if(move.getStartPiece().isPawn() || move.getType() == MoveType.CAPTURE){
            halfMoves = 0;
            HMClock = Integer.toString(halfMoves);
        }
        else{
            incrementHalfMoveClock();
        }
    }
    
    // accessors //
    
    public static void incrementHalfMoveClock(){
        halfMoves++;
        HMClock = Integer.toString(halfMoves);
    }
    
    public static void incrementFullMoveClock(){
        fullMoves++;
        FMClock = Integer.toString(fullMoves);
    }
    
    public static String getWhiteCastlingRights(){
        String str = "";
        for(Character ch : castlingRights.toCharArray()){
            if(Character.isUpperCase(ch)){
                str += ch;
            }
        }
        return str;
    }
    
    public static String getBlackCastlingRights(){
        String str = "";
        for(Character ch : castlingRights.toCharArray()){
            if(Character.isLowerCase(ch)){
                str += ch;
            }
        }
        return str;
    }
    
    public static String getFen(){
        String str = "";
        str += piecePositions + " ";
        str += colorToMove + " ";
        str += castlingRights + " ";
        str += enPassantSquare + " ";
        str += HMClock + " ";
        str += FMClock + " ";
        return str;
    }
    
    public static void printFen(){
        System.out.println(getFen());
    }
    
}
