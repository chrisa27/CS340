package com.example;

import java.io.File;
import java.io.IOException;

/**
 * Created by video on 2/3/2017.
 */

public class EvilHangman {
    public static void main(String[] args) throws IOException{
        File input = new File(args[0]);
        if (!input.exists()){
            System.out.print("The file doesn't exist");
            return;
        }

        int wordLength = Integer.parseInt(args[1]);
        int numGuesses = Integer.parseInt(args[2]);

        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(input, wordLength);
        game.setGuessesLeft(numGuesses);
        game.runGame();
    }
}
