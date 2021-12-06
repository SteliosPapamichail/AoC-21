import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Day2 {
    public static void main(String[] args) {
        File input = new File("C:\\Users\\Steli\\Desktop\\day_2.txt");
        Scanner scanner;
        try {
            int depth = 0;
            int aim = 0;
            int horizontalPos = 0;
            scanner = new Scanner(input);
            while(scanner.hasNext()) {
                String[] dirParams = scanner.nextLine().split(" ");
                int amount = Integer.parseInt(dirParams[1]);
                switch(dirParams[0]) {
                    case "forward": {
                        horizontalPos += amount;
                        depth += aim*amount;
                        break;
                    }
                    case "up": {
                        aim -= amount;
                        break;
                    }
                    case "down": {
                        aim += amount;
                        break;

                    }
                    default: {}
                }
            }
            System.out.println(depth*horizontalPos);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}