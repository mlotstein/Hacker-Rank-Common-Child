import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        // Read in input consisting of 2 lines
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String strLine;

            List<Short> word1, word2;
            Set<Short> uniq_chars = new HashSet<>();
            //Read the first word of input and convert to Short to save memory later
            // Fancy Java 8 way of converting from a string to an array of characters to a stream before casting to
            // short and collecting in a list. Other more efficient methods exist, but I want to practice using streams
            strLine = br.readLine();
            word1 = Arrays.stream(strLine.split("")).map(s -> (short)Character.getNumericValue(s.charAt(0))).collect(Collectors.toList());
            strLine = br.readLine();
            word2 = Arrays.stream(strLine.split("")).map(s ->(short)Character.getNumericValue(s.charAt(0))).collect(Collectors.toList());
            // 1) Build set of unique characters in each word, take intersection
            uniq_chars.addAll(word1);
            // Find the intersection
            uniq_chars.retainAll(word2);
            // 2) Remove all but the common unique characters.
            // The preprocessing reduces Time/Space complexity, as the removed characters are guaranteed not to be part
            // the longest common subsequence
            word1.stream().filter(uniq_chars::contains).collect(Collectors.toList());
            word2.stream().filter(uniq_chars::contains).collect(Collectors.toList());
            // 4) After converting to each word to an array of shorts to save memory, pPerform Levenshtein edit distance
            //    with no substitution. The value in the bottom right should be the answer
            Short[] word1_a = word1.toArray(new Short[word1.size()]), word2_a = word2.toArray(new Short[word2.size()]);
            System.out.println(NWScore(word1_a, word2_a));
            // Modified edit distance with no substitution


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Modified Needleman-Wunsch, with no penalties for deletion or insertion and with substitution forbidden.
     * Uses O(s1*s2) time and space. Can be improved to use linear space using Hirschberg algo.
     * @param s1
     * @param s2
     * @return
     */
    public static short NWScore (Short[] s1, Short[] s2) {
        int numRows = s1.length, numCols = s2.length;
        short[][] score = new short[numRows + 1][numCols + 1];
        for (int r = 1; r <= numRows; r++) {
            for (int c = 1; c <= numCols; c++) {
                if (s1[r - 1].equals(s2[c - 1])) {
                    score[r][c] = (short)(score[r - 1][c - 1] + 1);
                } else {
                    score[r][c] = (short)Math.max(score[r - 1][c], score[r][c - 1]);
                }
            }
        }

        return score[numRows][numCols];
    }
}