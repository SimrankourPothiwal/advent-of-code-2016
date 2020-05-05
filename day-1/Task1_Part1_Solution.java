import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.abs;

/**
 *No Time for a Taxicab
 */
public class Task1_Part1_Solution {
    public static void main(String[] args) {
        try {
            String[] data = parseDataFile("src/input.txt");

            System.out.println(calculateDistanceFromStart(data));
        } catch (IOException e) { // As File reader can fail
            e.printStackTrace();
        }
    }

    /**
     * Calculate distance traveled for a given Array of Strings
     * @param data Array of Strings
     * @return Travel distance
     */
    private static int calculateDistanceFromStart(String[] data) {
        int facingDirection = 0; // Determines which direction you are facing. 0 = North, 1 = East, 2 = South , 3 = West
        int[] coordinates = new int[] {0, 0}; // Starting position, always 0, 0
        int parseDistance; // Used to get the integer part of the data

        for (String s : data) {
            // Did we go Right or left?
            if (s.charAt(0) == 'R')
                facingDirection += 5; // Rotate clockwise ( 1 + 4)
            else
                facingDirection += 3; // Rotate anti-clockwise ( -1 + 4)

            parseDistance = Integer.parseInt(s.substring(1)); // Get the distance part of the String
            switch (facingDirection % 4) { // Mod positive number by 4 to get NESW value
                case 0: // Case of North, move Y coord in positive direction
                    coordinates[1] += parseDistance;
                    break;
                case 1: // Case of East, move X coord in positive direction
                    coordinates[0] += parseDistance;
                    break;
                case 2: // Case of South, move Y coord in negative direction
                    coordinates[1] -= parseDistance;
                    break;
                case 3: // Case of West, move X coord in negative direction
                    coordinates[0] -= parseDistance;
                    break;
            }
        }
        return (abs(coordinates[0]) + abs(coordinates[1])); // Add total of X + Y (positive) to get distance traveled, since city grid system is 1, 1 movements all we need to do is total X Y
    }


    /**
     * @param path - Path relative to the directory that contains directions
     * @return - An Array of Strings containing all your directions with commas and white space removed
     * @throws IOException Throw Exception to the top layer of logic
     */
    private static String[] parseDataFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String data = br.readLine();
        String[] parsedData = data.split(", ");
        br.close();
        return parsedData;
    }
}
