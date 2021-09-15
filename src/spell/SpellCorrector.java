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

        while (newScanner.hasNext()) {
            newWord = newScanner.next().toLowerCase();
            myDictionary.add(newWord);
        }

        newScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        //Map<String, Integer> correctWords;

        //if not null return it as lower case
        if (myDictionary.find(inputWord) == null) {

            TreeSet<String> allCandidates = new TreeSet<String>();
            TreeSet<String> correctWords = new TreeSet<String>();

            int highestCount = 0;
            String suggestedWord = null;

            //TA-PSEUDO
            //run on original input word
            //algorithm
            //insertion
            //deletion
            //transposition (swapping)
            //alteration (changing values)

            //ADD ALL OF THESE TO ALLCANDIDATES (for string s: insertionSet) --> allCandidates.add(s) (do for all 4)
            allCandidates.addAll(insertionD(inputWord));
            allCandidates.addAll(deletionD(inputWord));
            allCandidates.addAll(transpositionD(inputWord));
            allCandidates.addAll(alterationD(inputWord));
            //Once all candidates is complete
            //Do for (string s: allCandidates) --> call trie.find in all of those --> if the word is found, add it correctWords.
            for (String s : allCandidates) {
                if (myDictionary.find(s) != null) {
                    if (myDictionary.find(s).getValue() > highestCount) {
                        suggestedWord = s;
                        highestCount = myDictionary.find(s).getValue();
                    }
                }
            }

            //if correctWords.size > 1 --> do precedenceCheck --> look at the count of all the nodes and have a current highest
            // if correctWords.size == 1 --> return that word
            // if correctWords.size < 1 --> call all 4 functions on allCandidates and put them into allCandidates2 and call all 4 functions again
            if (suggestedWord == null) {
                for (String s : allCandidates) {
                    correctWords.addAll(insertionD(s));
                    correctWords.addAll(deletionD(s));
                    correctWords.addAll(transpositionD(s));
                    correctWords.addAll(alterationD(s));
                }

                for (String s : correctWords) {
                    if (myDictionary.find(s) != null) {
                        if (myDictionary.find(s).getValue() > highestCount) {
                            suggestedWord = s;
                            highestCount = myDictionary.find(s).getValue();
                        }
                    }
                }
                return suggestedWord;
            }
            else {
                return suggestedWord;
            }
            /**
             if (correctWords.size() > 1) {
             for (String s : correctWords) {
             if (myDictionary.find(s).getValue() > highestCount) {
             suggestedWord = s;
             highestCount = myDictionary.find(s).getValue();
             }
             }
             return suggestedWord;
             }
             else if (correctWords.size() == 1){
             suggestedWord = correctWords.toString();
             return suggestedWord;
             }
             else if (correctWords.size() < 1) {
             for (String s : allCandidates) {
             allCandidates2.addAll(insertionD(s));
             allCandidates2.addAll(deletionD(s));
             allCandidates2.addAll(transpositionD(s));
             allCandidates2.addAll(alterationD(s));
             }
             for (String s : allCandidates2) {
             if (myDictionary.find(s) != null) {
             correctWords2.add(s);
             }
             }
             for (String j : correctWords2) {
             if (myDictionary.find(j).getValue() > highestCount) {
             suggestedWord = j;
             highestCount = myDictionary.find(j).getValue();
             }
             }
             return suggestedWord;
             } **/
        }

        return inputWord.toLowerCase();
    }

    private TreeSet<String> insertionD(String inputWord) {
        TreeSet<String> wordsToInsert = new TreeSet<String>();
        int sizeOfWord = inputWord.length();

        for (int i = 0; i < sizeOfWord + 1; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                char aChar = (char) ('a' + j);
                StringBuilder wordInput = new StringBuilder(inputWord);
                wordInput.replace(i, i, Character.toString(aChar));
                String string = wordInput.toString();
                wordsToInsert.add(string);
            }
        }

        return wordsToInsert;
    }

    private TreeSet<String> deletionD(String inputWord) { //deletion
        TreeSet<String> wordsToDelete = new TreeSet<String>();
        int sizeOfWord = inputWord.length();

        for (int i = 0; i < sizeOfWord; i++) {
            StringBuilder wordInput = new StringBuilder(inputWord);
            wordInput.deleteCharAt(i);
            String string = wordInput.toString();
            wordsToDelete.add(string);
        }

        return wordsToDelete;
    }

    private TreeSet<String> alterationD(String inputWord) { //alteration
        TreeSet<String> wordsToAlterate = new TreeSet<String>();
        int sizeOfWord = inputWord.length();

        for (int i = 0; i < sizeOfWord; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                char aChar = (char) ('a' + j);
                StringBuilder wordInput = new StringBuilder(inputWord);
                wordInput.replace(i, i + 1, Character.toString(aChar));
                String string = wordInput.toString();
                wordsToAlterate.add(string);
            }
        }

        return wordsToAlterate;
    }

    private TreeSet<String> transpositionD(String inputWord) { //transposition
        TreeSet<String> wordsToSwap = new TreeSet<String>();
        int sizeOfWord = inputWord.length();

        for (int i = 0; i < sizeOfWord - 1; i++) {
            StringBuilder wordInput = new StringBuilder(inputWord);
            wordInput.setCharAt(i, inputWord.charAt(i + 1));
            wordInput.setCharAt(i + 1, inputWord.charAt(i));
            String string = wordInput.toString();
            wordsToSwap.add(string);
        }

        return wordsToSwap;
    }

    /** FAILED ATTEMPT TO USE A MAP
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

     private Map<String, Integer> getValidWordsP2(ArrayList<String> array) {
     ArrayList<String> newArray = new ArrayList<String>();

     for (String string : array) {
     newArray.addAll(alterationD(string));
     }
     return getValidWords(newArray);
     }
     **/
}
