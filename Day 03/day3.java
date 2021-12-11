import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day3 {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Steli\\Desktop\\day_3.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[] numOf1s = new int[12];
            int[] numOf0s = new int[12];
            while(scanner.hasNext()) {
                String bits = scanner.nextLine();
                for(int i=0; i < 12; i++) {
                    if(bits.charAt(i) == '1') numOf1s[i]++;
                    else numOf0s[i]++;
                }
            }
            int[] mostCommonBits = new int[12];
            int[] leastCommonBits = new int[12];
            for(int i=0; i < 12; i++) {
                if(numOf1s[i] > numOf0s[i]) {
                    mostCommonBits[i] = 1;
                    leastCommonBits[i] = 0;
                } else {
                    mostCommonBits[i] = 0;
                    leastCommonBits[i] = 1;
                }
            }
            long gammaRate = 0L;
            long epsilonRate = 0L;
            for (int i=0; i < 12; i++) {
                gammaRate += mostCommonBits[i] == 1? Math.pow(2.0, (double)11-i) : 0L;
                epsilonRate += leastCommonBits[i] == 1? Math.pow(2.0, (double)11-i) : 0L;
            }
            System.out.println(gammaRate * epsilonRate);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
