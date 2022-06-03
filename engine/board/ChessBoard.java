/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.board;

import com.krlv.source.engine.InputHandler;
import com.krlv.source.engine.file.SaveFile;
import com.krlv.source.engine.loader.ImageUtils;
import com.krlv.source.engine.maps.AttackMap;
import com.krlv.source.engine.maps.KingCheckMap;
import com.krlv.source.engine.maps.PinnedMap;
import com.krlv.source.engine.moves.Move;
import com.krlv.source.engine.moves.MoveHandler;
import com.krlv.source.engine.moves.MoveType;
import com.krlv.source.engine.states.BoardState;
import com.krlv.source.engine.ui.UI;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class ChessBoard {
    
    //board variables
    private static final int squareSize = 80;
    private static final String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    //edge distance array
    //N - S - W - E - NW - NE - SW - SE  (relative to white's perspective)
    private static final int[] directionOffsets = {8, -8, -1, 1, 7, 9, -9, -7};
    private static int[][] edgeDistances;
    
    //information boards
    private static int[] typeBoard;
    private static String[] coordinateBoard;
    private static Piece[] board;
    
    //FEN string tracker
    private static FenReader fenReader;
    
    //piece moving variables
    private static Piece draggedPiece;
    private static ArrayList<Move> validMoves = new ArrayList();
    
    //piece lists
    private static ArrayList<Piece> whitePieces, blackPieces;
    
    //kings 
    private static Piece whiteKing, blackKing;
    
    //ui
    private static UI ui;
    
    public ChessBoard(UI ui){
        this.ui = ui;
        fenReader = new FenReader(startFen);
        initDistToEdgeArray();
        updateCoordinateBoard();
        updateBoard();
        updateTypeBoard();
    }
    
    // distance to edge array methods //
    
    private void initDistToEdgeArray(){
        edgeDistances = new int[64][8];
        for(int i = 0; i < 64; i++){
            int row = i / 8;
            int col = i % 8;
            
            int distN = 7 - row;
            int distS = row;
            int distW = col;
            int distE = 7 - col;
            int distNW = Math.min(distN, distW);
            int distNE = Math.min(distN, distE);
            int distSW = Math.min(distS, distW);
            int distSE = Math.min(distS, distE);
            
            int[] distances = {
                distN,
                distS,
                distW,
                distE,
                distNW,
                distNE,
                distSW,
                distSE
            };
            
            edgeDistances[i] = distances;
        }
    }
    
    public static int getDistToEdge(int pieceIndex, int direction){
        return edgeDistances[pieceIndex][direction];
    }
    
    public static int[] getDirectionOffsets(){
        return directionOffsets;
    }
    
    // end of distance to edge array methods //
    
    private static void updateCoordinateBoard(){
        coordinateBoard = new String[64];
        for(int row = 0; row < 8 ; row++){
            for(int col = 0; col < 8; col++){
                int index = row * 8 + col;
                char file = (char)(97 + col);
                int rank = row + 1;
                coordinateBoard[index] = "" + file + rank;
            }
        }
    }
    
    private static void updateBoard(){
        board = new Piece[64];
        whitePieces = new ArrayList();
        blackPieces = new ArrayList();
        
        int boardIndex = 56;
        String[] rows = FenReader.getPiecePositions().split("/");
        for(String str : rows){
            for(Character ch : str.toCharArray()){
                if(Character.isDigit(ch)){
                    int blanks = Character.getNumericValue(ch);
                    while(blanks != 0){
                        board[boardIndex] = new Blank(boardIndex);
                        blanks--;
                        boardIndex++;
                    }
                    continue;
                }
                else if(Character.isUpperCase(ch)){
                    switch(ch){
                        case 'P' -> board[boardIndex] = new Pawn(boardIndex, 1);
                        case 'N' -> board[boardIndex] = new Knight(boardIndex, 1);
                        case 'B' -> board[boardIndex] = new Bishop(boardIndex, 1);
                        case 'R' -> board[boardIndex] = new Rook(boardIndex, 1);
                        case 'Q' -> board[boardIndex] = new Queen(boardIndex, 1);
                        case 'K' -> board[boardIndex] = new King(boardIndex, 1);
                    }
                }
                else if(Character.isLowerCase(ch)){
                    switch(ch){
                        case 'p' -> board[boardIndex] = new Pawn(boardIndex, -1);
                        case 'n' -> board[boardIndex] = new Knight(boardIndex, -1);
                        case 'b' -> board[boardIndex] = new Bishop(boardIndex, -1);
                        case 'r' -> board[boardIndex] = new Rook(boardIndex, -1);
                        case 'q' -> board[boardIndex] = new Queen(boardIndex, -1);
                        case 'k' -> board[boardIndex] = new King(boardIndex, -1);
                    }
                }
                
                if(board[boardIndex].isWhite()){
                    whitePieces.add(board[boardIndex]);
                    if(board[boardIndex].isKing())
                        whiteKing = board[boardIndex];
                }
                else if(board[boardIndex].isBlack()){
                    blackPieces.add(board[boardIndex]);
                    if(board[boardIndex].isKing())
                        blackKing = board[boardIndex];
                }
                
                boardIndex++;
            }
            boardIndex -= 16;
        }
        
    }
    
    private static void updateTypeBoard(){
        typeBoard = new int[64];
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                typeBoard[row * 8 + col] = board[row * 8 + col].getType();
            }
        }
    }
    
    // draw methods //
    
    public void draw(Graphics2D g2d){
        drawBackground(g2d);
        drawSquares(g2d);
        
        Piece checkedKing = KingCheckMap.getCheckedKing();
        if(checkedKing != null){
            if(BoardState.isCheckmated()){
                drawCheckmatedKing(checkedKing, g2d);
            }
            else{
                drawCheckedKing(checkedKing, g2d);
            }
        }
        
        drawValidMoves(g2d);
        drawPieces(g2d);
        
        if(InputHandler.debugModeEnabled()){
            drawAttackMap(g2d);
        }
        
        drawDraggedPiece(g2d);
    }
    
    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(ImageUtils.getBackground(), 0, 0, null);
    }
    
    private void drawSquares(Graphics2D g2d){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                int index = (fenReader.isWhitesTurn()) ?  (63 - (row * 8 + (7-col))) : (row * 8 + (7-col));
                int drawX = (col + 1) * squareSize;
                int drawY = (row + 1) * squareSize;
                g2d.setColor(((row + col) % 2 == 0) ? ImageUtils.getLightColor() : ImageUtils.getDarkColor());
                g2d.fillRect(drawX, drawY, squareSize, squareSize);      
                
                Piece piece = board[index];
                if(!piece.isBlank() && piece != draggedPiece)
                    g2d.drawImage(ImageUtils.getPieceImageByType(piece.getType()), drawX, drawY, null);
            }
        }
    }
    
    private void drawValidMoves(Graphics2D g2d){
        if(draggedPiece == null || validMoves.isEmpty())
            return;
        for(Move move: validMoves){
            g2d.setColor(ImageUtils.getMoveColor(move));
            int in = move.getTargetIndex();
            int drawX = (draggedPiece.isWhite()) ? (in % 8) * squareSize + squareSize : 
                    (7 - in % 8) * squareSize + squareSize;
            
            int drawY = (draggedPiece.isWhite()) ? (7 - in / 8) * squareSize + squareSize :
                    in / 8 * squareSize + squareSize;
            
            g2d.fillRect(drawX + 15, drawY + 15, squareSize - 30, squareSize - 30);
        }
    }
    
    private void drawPieces(Graphics2D g2d){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                int index = (fenReader.isWhitesTurn()) ?  (63 - (row * 8 + (7-col))) : (row * 8 + (7-col));
                int drawX = (col + 1) * squareSize;
                int drawY = (row + 1) * squareSize;     
                
                Piece piece = board[index];
                if(!piece.isBlank() && piece != draggedPiece)
                    g2d.drawImage(ImageUtils.getPieceImageByType(piece.getType()), drawX, drawY, null);
            }
        }
    }
    
    private void drawDraggedPiece(Graphics2D g2d){
        if(draggedPiece == null)
            return;
        
        if(draggedPiece.isBlank())
            return;
        
        int drawX = InputHandler.getX() - squareSize/2;
        int drawY = InputHandler.getY() - squareSize/2;
        
        g2d.drawImage(ImageUtils.getPieceImageByType(draggedPiece.getType()), drawX, drawY, null);
    }
    
    private void drawAttackMap(Graphics2D g2d){
        g2d.setColor(ImageUtils.getPrevMoveColor());
        for(int i = 0; i < AttackMap.getAttackMap().length; i++){
            if(AttackMap.isInEnemySquares(i)){
                int drawX = (FenReader.isWhitesTurn()) ? (i % 8) * squareSize + squareSize : 
                        (7 - i % 8) * squareSize + squareSize;

                int drawY = (FenReader.isWhitesTurn()) ? (7 - i / 8) * squareSize + squareSize :
                        i / 8 * squareSize + squareSize;

                g2d.fillRect(drawX + 15, drawY + 15, squareSize - 30, squareSize - 30);
            }
        }
    }
    
    private void drawCheckedKing(Piece king, Graphics2D g2d){
        int row = king.getRow();
        int col = king.getCol();
        
        int drawX = (king.isWhite()) ? (col + 1) * squareSize : 
                    (7 - col + 1) * squareSize;
            
        int drawY = (king.isWhite()) ? (7 - row + 1) * squareSize :
                    (row + 1) * squareSize;
                
        g2d.setColor(ImageUtils.getCheckedColor());
        g2d.fillRect(drawX + 5, drawY + 5, squareSize - 10, squareSize - 10);
    }
    
    private void drawCheckmatedKing(Piece king, Graphics2D g2d){
        int row = king.getRow();
        int col = king.getCol();
        
        int drawX = (king.isWhite()) ? (col + 1) * squareSize : 
                    (7 - col + 1) * squareSize;
            
        int drawY = (king.isWhite()) ? (7 - row + 1) * squareSize :
                    (row + 1) * squareSize;
                
        g2d.setColor(ImageUtils.getCheckmateColor());
        g2d.fillRect(drawX + 5, drawY + 5, squareSize - 10, squareSize - 10);
    }
    
    // end of draw methods //
    
    // helper update methods //

    public Piece getPieceOnMouse(){
        int x = InputHandler.getX();
        int y = InputHandler.getY();
        
        int col = FenReader.isWhitesTurn() ? 
                ((x - x % squareSize) / squareSize - 1) :
                7 - ((x - x % squareSize) / squareSize - 1);
        
        int row = FenReader.isWhitesTurn() ? 
                7 - ((y - y % squareSize) / squareSize - 1) : 
                ((y - y % squareSize) / squareSize - 1);
                
        return board[row * 8 + col];
    }
    
        // board accessor methods //
    
    public static int[] getTypeBoard(){
        return typeBoard;
    }
    
    public static String getCoordinate(int index){
        return coordinateBoard[index];
    }
    
    public static Piece getPieceAt(int index){
        return board[index];
    }
    
    public static Piece[] getBoard(){
        return board;
    }
    
    public static ArrayList<Piece> getWhitePieces(){
        return whitePieces;
    }
    
    public static ArrayList<Piece> getBlackPieces(){
        return blackPieces;
    }
    
    public static Piece getWhiteKing(){
        return whiteKing;
    }
    
    public static Piece getBlackKing(){
        return blackKing;
    }
    
    public static Piece getEnemyKing(int color){
        return (color == 1) ? getBlackKing() : getWhiteKing();
    }
    
    public static ArrayList<Piece> getEnemyPieces(int color){
        return (color == 1) ? getBlackPieces() : getWhitePieces();
    }
            
        // end of board accessor methods //
            
    // end of helper update methods //
    
    // update methods //
    
    public void update(){
        
        if(InputHandler.isPressed()){
            if(draggedPiece == null && BoardUtils.mouseIsInBoard()){
                draggedPiece = getPieceOnMouse();
                if(BoardUtils.isColorToMove(draggedPiece)){
                    validMoves = draggedPiece.getValidMoves();
                }
                else
                    draggedPiece = null;
            }
        }
        else{
            if(draggedPiece != null && BoardUtils.mouseIsInBoard()){
                Piece targetPiece = getPieceOnMouse();
                Move move = MoveHandler.getMove(validMoves, targetPiece.getIndex());
                if(move != null){
                    
                    if(move.type == MoveType.PROMOTION){
                        ui.setPromotionButtonIcons();
                        ui.setPromotionButtonBounds(targetPiece);
                        ui.setPromotionMenuVisible(true);
                    }
                    
                    move.execute();
                    
                    if(move.type == MoveType.PROMOTION){
                        ui.setPromotionMenuVisible(false);
                    }
                    
                    FenReader.updatePiecePositions(board);
                    FenReader.switchTurn();
                    FenReader.updateCastlingRights(move);
                    FenReader.updateEnPassantSquareCoordinate(move);
                    FenReader.updateMoveClocks(move);
                    FenReader.printFen();
                    
                    updateTypeBoard();
                    AttackMap.updateAttackMap();
                    KingCheckMap.updateCheckBlockMap();
                    PinnedMap.updatePinnedMap();
                    
                    if(BoardState.isCheckmate()){
                        System.out.println("Checkmate!");
                        resetBoard();
                    }
                }
            }
            validMoves.clear();
            draggedPiece = null;
        }
        
    }
    
    @Override
    public String toString(){
        String str = ""; 
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                str += board[row * 8 + col].getIndex() + " ";
            }
            str += "\n";
        }
        return str;
    }
    
    public void printTypeBoard(){
        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){
                System.out.print(typeBoard[row * 8 + col] + "\t");
            }
            System.out.println();
        }
    }
    
    public void resetBoard(){
        fenReader = new FenReader(startFen);
        updateBoard();
        updateTypeBoard();
        AttackMap.updateAttackMap();
        KingCheckMap.updateCheckBlockMap();
        PinnedMap.updatePinnedMap();
    }
    
    public static void loadBoard(String str) {
        fenReader = new FenReader(str);
        updateBoard();
        updateTypeBoard();
        AttackMap.updateAttackMap();
        KingCheckMap.updateCheckBlockMap();
        PinnedMap.updatePinnedMap();
    }
}
