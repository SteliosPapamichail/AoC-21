import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day3_2 {
    public static void main(String[] args) {
        try {
            List<String> fileStream = Files.readAllLines(Paths.get("C:\\Users\\Steli\\Desktop\\day_3.txt"));
            int noOfLines = fileStream.size();
            File file = new File("C:\\Users\\Steli\\Desktop\\day_3.txt");
            Scanner scanner = new Scanner(file);
            String[] contents = new String[noOfLines];

            for (int i = 0; i < noOfLines; i++) {
                contents[i] = scanner.nextLine();
            }
            scanner.close();
            int n = contents[0].toCharArray().length; // num of bits per line

            ArrayList<String> oxygenRating = new ArrayList<>();
            ArrayList<String> co2Rating = new ArrayList<>();

            int numOf1s = 0;
            int numOf0s = 0;

            for (String num : contents) { // calculate most common bit
                if (num.charAt(0) == '1')
                    numOf1s++;
                else
                    numOf0s++;
            }

            // add initial values to the lists
            for (String num : contents) {
                if (numOf1s >= numOf0s) {
                    if (num.charAt(0) == '1')
                        oxygenRating.add(num);
                    else
                        co2Rating.add(num);
                } else {
                    if (num.charAt(0) == '0')
                        oxygenRating.add(num);
                    else
                        co2Rating.add(num);
                }
            }

            findRating(oxygenRating, true,n);
            findRating(co2Rating, false,n);

            long oxygenRatingNum = 0L;
            long co2RatingNum = 0L;

            for (int i = 0; i < n; i++) {
                oxygenRatingNum += oxygenRating.get(0).charAt(i) == '1' ? Math.pow(2.0, (double) (n-1) - i) : 0L;
                co2RatingNum += co2Rating.get(0).charAt(i) == '1' ? Math.pow(2.0, (double) (n-1) - i) : 0L;
            }
            System.out.println(oxygenRatingNum * co2RatingNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters the given list of binary numbers based on the bit-criteria
     * specified by the given boolean value.
     * @param binNums The list of binary numbers to filter
     * @param findOxygenRating True for applying the oxygen-generator filter, false for applying the co2-scrubber filter
     * @param n The number of bits in a word
     */
    static void findRating(ArrayList<String> binNums, boolean findOxygenRating, int n) {
        // filter out invalid nums
        for (int i = 1; i < n; i++) { // for the rest of the bits
            if (binNums.size() == 1)
                break;
            int numOf1s = 0;
            int numOf0s = 0;

            for (String num : binNums) { // calculate most common bit
                if (num.charAt(i) == '1')
                    numOf1s++;
                else
                    numOf0s++;
            }

            ArrayList<String> TBR = new ArrayList<>(); // data to be removed

            if (numOf1s >= numOf0s) {
                for (String s : binNums) {
                    if (findOxygenRating && s.charAt(i) != '1' && TBR.size() < binNums.size() - 1)
                        TBR.add(s);
                    else if (!findOxygenRating && s.charAt(i) != '0' && TBR.size() < binNums.size() - 1)
                        TBR.add(s);
                }
            } else {
                for (String s : binNums) {
                    if (findOxygenRating && s.charAt(i) != '0' && TBR.size() < binNums.size() - 1)
                        TBR.add(s);
                    else if (!findOxygenRating && s.charAt(i) != '1' && TBR.size() < binNums.size() - 1)
                        TBR.add(s);
                }
            }
            binNums.removeAll(TBR);
        }
    }
}
