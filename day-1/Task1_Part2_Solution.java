import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

/**
 *No Time for a Taxicab
 */
public class Task1_Part2_Solution {
    public static void main(String[] args) {
        try {
            String[] data = parseDataFile("src/input.txt");

            System.out.println(calculateDistanceOfVisitedTwice(data));
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
            // See if we go right or left?
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
     * Calculate distance of first location visited twice for a given Array of Strings
     *
     * @param data Array of Strings
     * @return Travel distance to node visted twice
     */
    private static int calculateDistanceOfVisitedTwice(String[] data) {
        int facingDirection = 0; // Determines which direction you are facing. 0 = North, 1 = East, 2 = South , 3 = West
        int[] coordinates = new int[]{0, 0}; // Starting position, always 0, 0
        int parseDistance; // Used to get the integer part of the data
        List<Integer[]> visited = new ArrayList<>();

        visited.add(new Integer[]{coordinates[0], coordinates[1]});

        for (String s : data) {
            // See if we go right or left?
            if (s.charAt(0) == 'R')
                facingDirection += 5; // Rotate clockwise ( 1 + 4)
            else
                facingDirection += 3; // Rotate anti-clockwise ( -1 + 4)

            parseDistance = Integer.parseInt(s.substring(1)); // Get the distance part of the String
            switch (facingDirection % 4) { // Mod positive number by 4 to get NESW value
                case 0: // Case of North, move Y coord in positive direction
                    addVisitedPointsOnPositiveY(visited, coordinates, parseDistance);
                    coordinates[1] += parseDistance;
                    break;
                case 1: // Case of East, move X coord in positive direction
                    addVisitedPointsOnPositiveX(visited, coordinates, parseDistance);
                    coordinates[0] += parseDistance;
                    break;
                case 2: // Case of South, move Y coord in negative direction
                    addVisitedPointsOnNegativeY(visited, coordinates, -parseDistance);
                    coordinates[1] -= parseDistance;
                    break;
                case 3: // Case of West, move X coord in negative direction
                    addVisitedPointsOnNegativeX(visited, coordinates, -parseDistance);
                    coordinates[0] -= parseDistance;
                    break;
            }
        }
        int[] firstVisitedTwice = getFirstCoordVisitedTwice(visited); // Spec says that we have already visited all nodes, so do this check after visiting all
        assert firstVisitedTwice != null; // Null check can never be null if correct input though
        return (abs(firstVisitedTwice[0]) + abs(firstVisitedTwice[1])); // Add total of X + Y (positive) to get distance traveled, since city grid system is 1, 1 movements all we need to do is total X Y
    }

    /**
     * Using HashSet to calculate first duplicated visited node in O(N)
     * @param visited List of Integer[] (coordinates)
     * @return Coordinate as int array
     */
    private static int[] getFirstCoordVisitedTwice(List<Integer[]> visited) {
        Set<Coordinate> duplicateCheck = new HashSet<>();
        for (Integer[] coordinate : visited) {
            Coordinate temp = new Coordinate(coordinate[0], coordinate[1]);
            if (duplicateCheck.contains(temp)) {
                return new int[] { coordinate[0], coordinate[1] };
            }
            duplicateCheck.add(temp);
        }
        return null;
    }

    /**
     * Calculate all points traveled on X positive
     * @param visited list of points to update
     * @param start start position
     * @param distance distance traveled
     */
    private static void addVisitedPointsOnPositiveX(List<Integer[]> visited, int[] start, int distance) {
        for (int i = 1; i <= distance; ++i) {
            visited.add(new Integer[]{start[0] + i, start[1]});
        }
    }

    /**
     * Calculate all points traveled on Y positive
     * @param visited list of points to update
     * @param start start position
     * @param distance distance traveled
     */
    private static void addVisitedPointsOnPositiveY(List<Integer[]> visited, int[] start, int distance) {
        for (int i = 1; i <= distance; ++i) {
            visited.add(new Integer[]{start[0], start[1] + i});
        }
    }

    /**
     * Calculate all points traveled on X negative
     * @param visited list of points to update
     * @param start start position
     * @param distance distance traveled
     */
    private static void addVisitedPointsOnNegativeX(List<Integer[]> visited, int[] start, int distance) {
        for (int i = -1; i >= distance; --i) {
            visited.add(new Integer[]{start[0] + i, start[1]});
        }
    }


    /**
     * Calculate all points traveled on Y negative
     * @param visited list of points to update
     * @param start start position
     * @param distance distance traveled
     */
    private static void addVisitedPointsOnNegativeY(List<Integer[]> visited, int[] start, int distance) {
        for (int i = -1; i >= distance; --i) {
            visited.add(new Integer[]{start[0], start[1] + i});
        }
    }

    /**
     * @param data String of data
     * @return parsed data
     */
    private static String[] parseDataString(String data) {
        return data.split(", ");
    }

    /**
     * @param path - Path relative to the directory that contains your directions
     * @return - An Array of Strings containing all your directions with commas and white space removed
     * @throws IOException Throw Exception to the top layer of logic
     */
    private static String[] parseDataFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path)); // Simple bufferedReader to take in your data.
        String data = br.readLine(); // Read the file and remove all whitespace.
        String[] parsedData = data.split(", ");
        br.close();
        return parsedData;
    }

    private static class Coordinate {

        private int x;
        private int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int result = x;
            return 31 * result + y;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Coordinate))
                return false;
            if (o == this)
                return true;

            Coordinate c = (Coordinate) o;
            return this.x == c.x && this.y == c.y;
        }
    }
}
