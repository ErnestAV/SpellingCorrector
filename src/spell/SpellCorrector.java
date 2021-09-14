package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File inputFile = new File(dictionaryFileName);
        Scanner newScanner = new Scanner(inputFile);

        Trie newTrie = new Trie();
        String newWord;

        while(newScanner.hasNext()) {
            newWord = newScanner.next().toLowerCase();
            newTrie.add(newWord);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
