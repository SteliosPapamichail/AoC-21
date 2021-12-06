import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Day1 {
    public static void main(String[] args) {
        File input = new File("C:\\Users\\Steli\\Desktop\\day1.txt");
        Scanner inptFile;
        try {
            inptFile = new Scanner(input);
            int[] measurements = new int[3];
            int prevSum = -1;
            int countOfIncreasedReadings = 0;

            while (inptFile.hasNext()) {
                int readingSum = 0;
                if (prevSum == -1) {
                    for (int i = 0; i < 3; i++) {
                        int reading = inptFile.nextInt();
                        measurements[i] = reading;
                        if (!inptFile.hasNext()) System.exit(-1);
                    }
                    prevSum = measurements[0] + measurements[1] + measurements[2];
                } else {
                    int reading = inptFile.nextInt();
                    // shift left
                    measurements[0] = measurements[1];
                    measurements[1] = measurements[2];
                    measurements[2] = reading;
                    readingSum = measurements[0] + measurements[1] + measurements[2];
                    if(readingSum > prevSum) countOfIncreasedReadings++;
                    prevSum = readingSum;
                }
            }
            System.out.println(countOfIncreasedReadings);
            inptFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}