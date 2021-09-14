package spell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    final int ALPHABET_SIZE = 26;
    ITrie myDictionary = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner newScanner = new Scanner(new File(dictionaryFileName));

        //myDictionary = new Trie();
        String newWord;

        while(newScanner.hasNext()) {
            newWord = newScanner.next().toLowerCase();
            myDictionary.add(newWord);
        }

        newScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        ArrayList<String> allCandidates = new ArrayList<String>();
        Map<String, Integer> correctWords;

        //if not null return it as lower case
        if (myDictionary.find(inputWord) != null) {
            return inputWord.toLowerCase();
        }

        //algorithm
            //insertion
            //deletion
            //transposition (swapping)
            //alteration (changing values)

            //if the data structure is empty
            //return correct word

        return null;
    }

    private ArrayList<String> insertionD(String inputWord, int valueOfD) {
        ArrayList<String> wordsToInsert = new ArrayList<String>();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char letterIndex = (char) ('a' + i);

            for (int j = 0; j < inputWord.length(); j++) {
                wordsToInsert.add(inputWord.substring(0, j));
                wordsToInsert.add(letterIndex + inputWord.substring(j + valueOfD));

                if (valueOfD == 0) {
                    wordsToInsert.add(inputWord + letterIndex);
                }
            }
        }
        return wordsToInsert;
    }

    private ArrayList<String> deletionD(String inputWord) {
        ArrayList<String> wordsToDelete = new ArrayList<String>();

        for (int i = 0; i < inputWord.length(); i++) {
            wordsToDelete.add(inputWord.substring(0, i) + inputWord.substring(i + 1));
        }

        return wordsToDelete;
    }

    private ArrayList<String> swapD(String inputWord) {
        ArrayList<String> wordsToSwap = new ArrayList<String>();
        for (int i = 0; i < inputWord.length() - 1; i++) {
            wordsToSwap.add(inputWord.substring(0, i) + inputWord.charAt(i + 1)
                    + inputWord.charAt(i) + inputWord.substring(i + 2));
        }
        return wordsToSwap;
    }

    private ArrayList<String> alterationD(String inputWord) {
        ArrayList<String> possibleCandidates = new ArrayList<String>();

        possibleCandidates.addAll(deletionD(inputWord));
        possibleCandidates.addAll(swapD(inputWord));
        possibleCandidates.addAll(insertionD(inputWord, 1));
        possibleCandidates.addAll(insertionD(inputWord, 0));

        return possibleCandidates;
    }

    private Map<String, Integer> getValidWords(ArrayList<String> array) {
        Map<String, Integer> dataMap = new HashMap<String, Integer>();

        INode newNode;

        for (String inputWord : array) {
            newNode = myDictionary.find(inputWord);

            if (newNode != null) {
                dataMap.put(inputWord, newNode.getValue());
            }
        }
        return dataMap;
    }
}
