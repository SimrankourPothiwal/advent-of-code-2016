import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;

public class Solution1 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/input.txt")); // Simple bufferedReader to take in your data.
            String[] data = br.readLine().split(", "); // Read the file and remove all whitespace.
            int[] location = new int[] {0, 0};
            int direction = 0;
            int parseDistance;

            for (String s:data) {
                if (s.charAt(0) == 'R')
                    direction += 5;
                else
                    direction += 3;

                parseDistance = Integer.parseInt(s.substring(1));
                direction = direction % 4;
                if (direction == 0)
                    location[1] += parseDistance;
                if (direction == 1)
                    location[0] += parseDistance;
                if (direction == 2)
                    location[1] -= parseDistance;
                if (direction == 3)
                    location[0] -= parseDistance;
            }
            System.out.println((abs(location[0]) + abs(location[1])));
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
