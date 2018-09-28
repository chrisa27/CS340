package com.example;

import java.util.Arrays;

/**
 * Created by video on 2/3/2017.
 */

public class Key {
    char[] letters;

    public Key(int wordLength){
        letters = new char[wordLength];

        for (int i = 0; i < wordLength; i++){
            letters[i] = '-';
        }
    }

    public Key(String word, char guess){
        letters = new char[word.length()];
        for (int i = 0; i < letters.length; i++){
            if (word.charAt(i) == guess){
                letters[i] = guess;
            }
            else {
                letters[i] = '-';
            }
        }
    }

    public boolean isRightmost(Key other){
        for (int i = 1; i <= letters.length; i++){
            if (letters[letters.length - i] != other.getLetters()[letters.length - 1]){
                if (letters[letters.length - 1] != '-'){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public void print(){
        for (int i = 0; i < letters.length; i++){
            System.out.print(letters[i]);
        }
        System.out.print('\n');
    }

    public int getDifference(Key other){
        int num = 0;
        for (int i = 0; i < letters.length; i++){
            if(letters[i] != other.getLetters()[i]){
                num++;
            }
        }
        return num;
    }

    public int numLetters(){
        int num = 0;
        for (int i = 0; i < letters.length; i++){
            if (letters[i] != '-'){
                num++;
            }
        }
        return num;
    }

    public char[] getLetters(){
        return letters;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o instanceof Key){
            Key obj = (Key) o;
            if (Arrays.equals(letters, obj.getLetters())){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int value = 0;
        for (int i = 0; i < letters.length; i++){
            if (letters[i] != '-'){
                value += letters[i];
            }
        }
        return value * 57;
    }
}
