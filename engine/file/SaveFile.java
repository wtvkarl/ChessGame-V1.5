/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.engine.file;

import com.krlv.source.engine.board.ChessBoard;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;  
import java.io.IOException;

/**
 *
 * @author 3095515
 */
public class SaveFile {
    
    public static void saveBoard(String fen) {
        try {
            FileWriter myWriter = new FileWriter("files/board.txt");
            myWriter.write(fen);
            myWriter.write("\r\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void clearBoardFile(){
        try {
            FileWriter writer = new FileWriter("files/board.txt");
            writer.write("");
            writer.close();
            System.out.println("Successfully cleared the file.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void loadFile() {
        try {
            File file = new File("files/board.txt");
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int)file.length()];
            reader.read(chars);
            String str = new String(chars);
            if(str.isBlank() || str.isEmpty()){
                System.out.println("File is empty. Load a new position.");
                return;
            }
            System.out.println("Successfully read the file.");
            ChessBoard.loadBoard(str);
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
