package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by video on 2/3/2017.
 */

public class EvilHangmanGame implements IEvilHangmanGame {
    Partition currentWords;
    int wordLength;
    int guessesLeft;
    Key correctLetters;
    TreeSet<String> guessedLetters;

    public void startGame(File dictionary, int wordLength){
        this.wordLength = wordLength;
        try {
            currentWords = new Partition(dictionary, wordLength);
        } catch(IOException e){
            System.out.print("error with file");
        }

        correctLetters = currentWords.getPositions();
        guessedLetters = new TreeSet<String>();
    }

    public void setGuessesLeft(int guesses){
        guessesLeft = guesses;
    }

    public void runGame(){
        boolean won = false;

        while(guessesLeft != 0 && !won){
            char guess;
            turnOutput();
            guess = turnInput();

            try {
                makeGuess(guess);
            } catch(GuessAlreadyMadeException e){
                System.out.println("That letter has already been guessed!");
            }

            if (currentWords.getWords().size() == 1){
                won = true;
            }
        }

        Iterator<String> it = currentWords.getWords().iterator();
        if (won){
            correctLetters.print();
            System.out.print("You win!");
        }
        else {
            System.out.print("You lose. The correct word was " + it.next());
        }
    }

    public void turnOutput(){
        System.out.println("Number of guesses left: " + guessesLeft);
        System.out.println("Letters guessed: " + guessedLetters.toString());
        correctLetters.print();
    }

    //doesnt check already guessed
    public char turnInput(){
        boolean valid = false;
        String Sguess;
        char guess;
        Scanner input = new Scanner(System.in);

       while(!valid) {
           System.out.println("What is your next guess? ");
           Sguess = input.next();

           if (Sguess.length() == 1) {
               Sguess = Sguess.toLowerCase();
               guess = Sguess.charAt(0);
               if (Character.isLetter(guess)) {
                   return guess;
               }
           }
           System.out.println("That guess is not valid");
       }
        return '-';
    }

    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException{
        StringBuilder Guess = new StringBuilder();
        Guess.append(guess);
        if(guessedLetters.contains(Guess.toString())){
            throw new GuessAlreadyMadeException();
        }
        else{
            guessedLetters.add(Guess.toString());
        }

        Partition temp = currentWords.getBestPartition(Guess.toString());

        if(correctLetters.equals(temp.getPositions())){
            guessesLeft--;
            System.out.println("Sorry there were no " + guess + "'s");
        }
        else{
            int newLetters = temp.getPositions().getDifference(correctLetters);
            System.out.println("There were " + newLetters + guess + "'s");
        }

        currentWords = temp;
        correctLetters = currentWords.getPositions();

        return new TreeSet<String>();
    }
}
