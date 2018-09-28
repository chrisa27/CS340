package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by video on 2/3/2017.
 */

public class Partition {
    Set<String> words;
    Key positions;

    public Partition(){

    }

    public Partition(File input, int wordLength) throws IOException{
        String currentWord;
        words = new HashSet<String>();
        positions = new Key(wordLength);

        Scanner fileWords = new Scanner(

                            new BufferedReader(
                                    new FileReader(input)
                            )
        );

        while(fileWords.hasNext()){
            currentWord = fileWords.next();
            if (currentWord.matches("[a-zA-Z]*") && currentWord.length() == wordLength){
                words.add(currentWord);
            }
        }
    }

    public Partition(Key key){
        words = new HashSet<String>();
        positions = key;
    }

    public Partition getBestPartition(String guess){
        TreeSet<Partition> newPartitions = new TreeSet<Partition>(new PartitionCompare());
        String word;
        char letter = guess.charAt(0);

        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            Partition temp;
            word = it.next();
            Key wordKey = new Key(word, letter);
            temp = new Partition(wordKey);
            if (!newPartitions.contains(temp)){
                temp.addWord(word);
                newPartitions.add(temp);
            }
            else {
                Iterator<Partition> it1 = newPartitions.iterator();
                boolean found = false;
                while (it1.hasNext() && !found){
                    temp = it1.next();
                    if (temp.getPositions().equals(wordKey)){
                        found = true;
                    }
                }
                temp.addWord(word);
            }
        }

        TreeSet<Partition> returnValues = new TreeSet<Partition>(new PartitionCompare());
        Iterator<Partition> IT = newPartitions.iterator();

        while (IT.hasNext()){
            returnValues.add(IT.next());
        }

        return returnValues.first();
    }

    public void addWord(String word){
        words.add(word);
    }

    public Set<String> getWords() {
        return words;
    }

    public Key getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if(o instanceof Partition){
            Partition obj = (Partition) o;

            if (positions.equals(obj.getPositions())){
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
        return positions.hashCode();
    }
}
