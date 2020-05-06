import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Security Through Obscurity
 */
public class Task4_SubQue2_Solution {

    public static void main(String[] args) {
        try {
            List<String> data = parseDataFile("src/data.txt");
            List<Room> rooms = parseData(data);
            rooms.stream().filter(Task4_SubQue2_Solution::isValidRoom).forEach(Task4_SubQue2_Solution::rotateCharacters);

            rooms.stream().filter(room -> room.message.contains("northpole")).forEach(room -> System.out.println(room.sectorId));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void rotateCharacters(Room room) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < room.message.length(); ++i) {
            if (Character.isLetter(room.message.charAt(i))) {
                int offset = room.message.charAt(i) - 'a';
                sb.append((char) ((offset + room.sectorId) % 26 + 'a'));
            } else {
                sb.append(' ');
            }
        }
        room.message = sb.toString();
    }

    private static boolean isValidRoom(Room room) {
        List<Map.Entry<Character, Integer>> sortedMap = new ArrayList<>(room.charFrequency.entrySet());
        Collections.sort(sortedMap, new CustomComparator<>());
        for (int i = 0; i < room.checkSum.length; ++i) {
            if (!(sortedMap.get(i).getKey().equals(room.checkSum[i])))
                return false;
        }
        return true;
    }

    private static List<Room> parseData(List<String> data) {
        List<Room> rooms = new ArrayList<>();
        int frequency, idCounter;
        int sectorId[] = new int[3];
        StringBuilder numberBuilder = new StringBuilder();
        int substring = -1;

        for (String s : data) {
            idCounter = 0;
            Room room = new Room();
            for (int i = 0; i < s.length(); ++i) {
                if (Character.isLetter(s.charAt(i))) {
                    frequency = room.charFrequency.containsKey(s.charAt(i)) ? room.charFrequency.get(s.charAt(i)) : 0;
                    room.charFrequency.put(s.charAt(i), frequency + 1);
                } else if (Character.isDigit(s.charAt(i))) {
                    if (substring == -1)
                        substring = i;

                    sectorId[idCounter] = Character.getNumericValue(s.charAt(i));
                    ++idCounter;
                } else if (s.charAt(i) == '[') {
                    room.checkSum = new char[]{s.charAt(i + 1), s.charAt(i + 2), s.charAt(i + 3), s.charAt(i + 4), s.charAt(i + 5)};
                    break;
                }
            }

            room.message = s.substring(0, substring - 1);

            for (int num : sectorId) {
                numberBuilder.append(num);
            }
            room.sectorId = Integer.parseInt(numberBuilder.toString());
            numberBuilder.setLength(0); //reset the StringBuilder without using the Garbage Collector
            substring = -1;

            rooms.add(room);
        }

        return rooms;
    }

    private static List<String> parseDataFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<String> list = new ArrayList<>();
        String data;
        while((data = br.readLine()) != null) {
            list.add(data);
        }
        br.close();
        return list;
    }

    private static class CustomComparator<K extends Comparable<? super K>,
            V extends Comparable<? super V>>
            implements Comparator<Map.Entry<K, V>> {

        public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
            int cmp1 = a.getValue().compareTo(b.getValue());
            if (cmp1 != 0) {
                return -cmp1;
            } else {
                return a.getKey().compareTo(b.getKey());
            }
        }

    }

    private static class Room {
        HashMap<Character, Integer> charFrequency = new HashMap();
        int sectorId = 0;
        char[] checkSum = new char[5];
        String message = "";
    }
}