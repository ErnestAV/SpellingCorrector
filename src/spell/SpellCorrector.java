package spell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner newScanner = new Scanner(new File(dictionaryFileName));

        Trie newTrie = new Trie();
        String newWord;

        while(newScanner.hasNext()) {
            newWord = newScanner.next().toLowerCase();
            newTrie.add(newWord);
        }

        newScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
