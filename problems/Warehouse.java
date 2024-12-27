package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Warehouse {
    char[][] map, wideMap;
    ArrayList<Character> instructions;
    int[] start, wideStart;
    int gpsCoordSum, wideGpsCoordSum;

    public Warehouse(String address) {
        parseInput(address);
        this.start = findStart();
        followInstructions();
        gpsCoordSum = findGpsCoordSum();
    }
    
    public int getGpsCoordSum() {
        return this.gpsCoordSum;
    }

    public int getWideGpsCoordSum() {
        return this.wideGpsCoordSum;
    }

    private void parseInput(String address) {
        ArrayList<String> mapArr = new ArrayList<>(), instructionArr = new ArrayList<>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);
            boolean part1 = true;

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();

                if (str.isEmpty()) {
                    part1 = false;
                }

                if (part1) {
                    mapArr.add(str);
                } else {
                    instructionArr.add(str);
                }
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.map = new char[mapArr.size()][];
        for (int i = 0; i < mapArr.size(); i++) {
            map[i] = mapArr.get(i).toCharArray();
        }

        this.instructions = new ArrayList<>();
        for (String str : instructionArr) {
            char[] arr = str.toCharArray();
            for (char c : arr) {
                instructions.add(c);
            }
        }
    }

    private int[] findStart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == '@') {
                    return new int[] {i, j};
                }
            }
        }

        return null;
    }

    private void followInstructions() {
        int[] position = start.clone();

        for (Character inst : instructions) {
            if (canMove(position, inst)) {
                moveChar(position, inst, '@');
                switch (inst) {
                    case '<':
                        position[1]--;
                        break;
                    case '^':
                        position[0]--;
                        break;
                    case '>':
                        position[1]++;
                        break;
                    case 'v':
                    default:
                        position[0]++;
                        break;
                }
            }
        }
    }

    private boolean canMove(int[] position, Character instruction) {
        int x = position[0], y = position[1];

        switch (instruction) {
            case '<':
                y--;
                break;
            case '^':
                x--;
                break;
            case '>':
                y++;
                break;
            case 'v':
            default:
                x++;
                break;
        }

        switch (map[x][y]) {
            case '.':
                return true;
            case 'O':
                if (canMove(new int[] {x, y}, instruction)) {
                    moveChar(new int[] {x, y}, instruction, 'O');
                    return true;
                } else {
                    return false;
                }
            case '#':
            default:
                return false;
        }
    }

    private void moveChar(int[] position, Character instruction, char item) {
        int x = position[0], y = position[1];

        map[x][y] = '.';

        switch (instruction) {
            case '<':
                y--;
                break;
            case '^':
                x--;
                break;
            case '>':
                y++;
                break;
            case 'v':
            default:
                x++;
                break;
        }

        map[x][y] = item;
    }

    private int findGpsCoordSum() {
        int ans = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 'O') {
                    ans += (i * 100 + j);
                }
            }
        }

        return ans;
    }
}