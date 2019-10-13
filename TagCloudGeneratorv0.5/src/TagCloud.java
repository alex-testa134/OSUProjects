import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine3;

/**
 * This program asks the user for a input text file, then it outputs a html page
 * with a tag cloud of words from the input file.
 *
 * @author Alex Testa
 * @author Devin Chen
 *
 */
public final class TagCloud {

    /**
     * The separators.
     */
    private static final String SEPARATORS = " \t\n\r`~!@#$%^&*()-_+=[]{}\\|:;'\",.<>/?";

    /**
     * Minimum number of times a word can exist.
     */
    private static int minNum = 0;

    /**
     * Maximum number of times a word can exist.
     */
    private static int maxNum = 0;

    /**
     * Maximum font size.
     */
    private static final int MAX_FONT_SIZE = 48;

    /**
     * Maximum font size.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloud() {
    }

    /**
     * Compares words according to the alphabet.
     */
    private static class Comparator1
            implements Comparator<Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> s1, Pair<String, Integer> s2) {
            return s1.key().compareToIgnoreCase(s2.key());
        }
    }

    /**
     * Compares words according to the number of times they occur.
     */
    private static class Comparator2
            implements Comparator<Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> s1, Pair<String, Integer> s2) {
            return s2.value().compareTo(s1.value());
        }
    }

    private static void outputPage(
            SortingMachine<Map.Pair<String, Integer>> sortedWords,
            SimpleWriter outFile, String fileName) {
    }

    /**
     * Check to see if a given character is a separator.
     *
     * @param test
     *            the string to be checked
     *
     * @return true if the character is a separator
     */
    private static boolean isSeparator(String test) {
        boolean result = false;

        for (int i = 0; i < SEPARATORS.length(); i++) {
            if (test.charAt(0) == SEPARATORS.charAt(i)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Sort words in the map in the order of ({@code} Comparator2), and sort the
     * first number of ({@code} size) in the order of ( {@code} Comparator1()).
     *
     * @param map
     *            the map of words to be sorted
     * @param size
     *            the number of words in the cloud
     *
     * @return the SortingMachine in alphabetic order
     */
    private static SortingMachine<Map.Pair<String, Integer>> sortWords(
            Map<String, Integer> map, int size) {
        SortingMachine<Map.Pair<String, Integer>> sortedAlphWords = new SortingMachine3<Map.Pair<String, Integer>>(
                new Comparator1());
        SortingMachine<Map.Pair<String, Integer>> sortedNumWords = new SortingMachine3<Map.Pair<String, Integer>>(
                new Comparator2());
        //Create temporary map
        Map<String, Integer> temp = map.newInstance();
        temp.transferFrom(map);
        //Sorts words based on the number of times they occur and put them in temp
        while (temp.size() > 0) {
            Pair<String, Integer> p = temp.removeAny();
            sortedNumWords.add(p);
            map.add(p.key(), p.value());
        }
        sortedNumWords.changeToExtractionMode();

        //removes a pair from the sorted words sorted by size
        //and adds the pair to the words sorted by alphabetical order
        for (int i = 0; sortedNumWords.size() > 0 && i < size; i++) {
            Map.Pair<String, Integer> pair = sortedNumWords.removeFirst();
            sortedAlphWords.add(pair);
            //finds the most occurring word
            if (i == 0) {
                maxNum = pair.value();
                //find the least occurring word
            } else if (i == size - 1) {
                minNum = pair.value();
            }
        }
        sortedAlphWords.changeToExtractionMode();
        return sortedAlphWords;
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures
     *
     *          <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     *          </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {

        int count = 0;
        char returnedPiece = 'k';
        String returned = "";
        if (isSeparator(Character.toString(text.charAt(position)))) {
            while (count < text.substring(position, text.length()).length()) {
                returnedPiece = text.charAt(position + count);
                if (isSeparator(
                        Character.toString(text.charAt(position + count)))) {
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        } else {
            while (count < text.substring(position, text.length()).length()) {
                returnedPiece = text.charAt(position + count);
                if (!isSeparator(
                        Character.toString(text.charAt(position + count)))) {
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        }
        return returned;
    }

    /**
     * Read the words from a file and add them into a map with the word and its
     * occurred frequency.
     *
     * @param inFile
     *            the file to be read from
     *
     * @return the map with the word and frequency
     */
    private static Map<String, Integer> readFromFile(SimpleReader file) {

        Map<String, Integer> map = new Map2<String, Integer>();
        String term = "";
        String nexTerm = "";
        int index = 0;
        int count = 0;
        term = file.nextLine();
        //TODO still need to tweak this while loop condition (remove condition)
        while (count <= 15) {
            index = 0;
            while (index < term.length()) {
                nexTerm = nextWordOrSeparator(term, index);
                index += nexTerm.length();
                if (!isSeparator(nexTerm)) {
                    if (map.hasKey(nexTerm)) {
                        Pair<String, Integer> tempPair = map.remove(nexTerm);
                        int tempValue = tempPair.value();
                        map.replaceValue(nexTerm, tempValue + 1);
                    } else {
                        map.add(nexTerm, 1);
                    }
                }
            }
            term = file.nextLine();
            count++;
        }
        return map;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        int size = 100;
        out.print("Enter the name of the input file: ");
        String inFileName = in.nextLine();
        out.print("Enter the name of the output file: ");
        String outFileName = in.nextLine();

        SimpleReader inFile = new SimpleReader1L(inFileName);
        SimpleWriter outFile = new SimpleWriter1L(outFileName);
        Map<String, Integer> wordMap = readFromFile(inFile);
        // Report an error if the size is more than words
        SortingMachine<Pair<String, Integer>> sortedWords = sortWords(wordMap,
                size);

        in.close();
        out.close();
        inFile.close();
        outFile.close();
    }

}
