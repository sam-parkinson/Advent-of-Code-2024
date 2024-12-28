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
        this.start = findStart(this.map);
        this.wideStart = findStart(this.wideMap);
        followInstructions();
        gpsCoordSum = findGpsCoordSum();
        wideGpsCoordSum = findWideGpsCoordSum();
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
        this.wideMap = new char[mapArr.size()][];
        for (int i = 0; i < mapArr.size(); i++) {
            char[] mapRow = mapArr.get(i).toCharArray();
            map[i] = mapRow;
            wideMap[i] = makeWideMapRow(mapRow);
        }

        this.instructions = new ArrayList<>();
        for (String str : instructionArr) {
            char[] arr = str.toCharArray();
            for (char c : arr) {
                instructions.add(c);
            }
        }
    }

    private char[] makeWideMapRow(char[] mapRow) {
        char[] wideRow = new char[mapRow.length * 2];

        for (int i = 0; i < mapRow.length; i++) {
            switch(mapRow[i]) {
                case '#':
                    wideRow[i * 2] = '#';
                    wideRow[i * 2 + 1] = '#';
                    break;
                case 'O':
                    wideRow[i * 2] = '[';
                    wideRow[i * 2 + 1] = ']';
                    break;
                case '@':
                    wideRow[i * 2] = '@';
                    wideRow[i * 2 + 1] = '.';
                    break;
                case '.':
                default:
                    wideRow[i * 2] = '.';
                    wideRow[i * 2 + 1] = '.';
                    break;
            }
        }

        return wideRow;
    }

    private int[] findStart(char[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == '@') {
                    return new int[] {i, j};
                }
            }
        }

        return null;
    }

    private void followInstructions() {
        int[] position = start.clone();
        int[] widePosition = wideStart.clone();

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

            if (canWideMove(widePosition, inst)) {
                moveWideChar(widePosition, inst);
                switch (inst) {
                    case '<':
                        widePosition[1]--;
                        break;
                    case '^':
                        widePosition[0]--;
                        break;
                    case '>':
                        widePosition[1]++;
                        break;
                    case 'v':
                    default:
                        widePosition[0]++;
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

    private boolean canWideMove(int[] position, Character instruction) {
        int x = position[0], y = position[1];

        if (instruction == '<' || instruction == '>') {
            return wideHorizontal(x, y, instruction);
        } else {
            return wideVertical(x, y, instruction);
        }
    }

    private boolean wideHorizontal(int x, int y, Character instruction) {
        int move = instruction == '<' ? -1 : 1;
        
        while (wideMap[x][y] != '.' && wideMap[x][y] != '#') {
            y += move;
        }

        return wideMap[x][y] == '.';
    }

    private boolean wideVertical(int x, int y, Character instruction) {
        int move = instruction == '^' ? -1 : 1;
        x += move;
        switch (wideMap[x][y]) {
            case '.':
                return true;
            case '[':
                if (wideVertical(x, y, instruction) && wideVertical(x, y + 1, instruction)) {
                    return true;
                } else {
                    return false;
                }
            case ']':
                if (wideVertical(x, y, instruction) && wideVertical(x, y - 1, instruction)) {
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

    private void moveWideChar(int[] position, Character instruction) {
        int x = position[0], y = position[1];

        char c = wideMap[x][y];
        
        if (instruction == '<' || instruction == '>') {
            int move = instruction == '<' ? -1 : 1;
            if (wideMap[x][y + move] != '.') {
                moveWideChar(new int[] {x, y + move}, instruction);
            }
            wideMap[x][y] = '.';
            wideMap[x][y + move] = c;
        } else {
            int move = instruction == '^' ? -1 : 1;
            if (wideMap[x + move][y] == '[') {
                moveWideChar(new int[] {x + move, y}, instruction);
                moveWideChar(new int[] {x + move, y + 1}, instruction);
            } else if (wideMap[x + move][y] ==']') {
                moveWideChar(new int[] {x + move, y}, instruction);
                moveWideChar(new int[] {x + move, y - 1}, instruction);
            }
            wideMap[x][y] = '.';
            wideMap[x + move][y] = c;
        }
    }
    
    private int findGpsCoordSum() {
        int ans = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'O') {
                    ans += (i * 100 + j);
                }
            }
        }

        return ans;
    }

    private int findWideGpsCoordSum() {
        int ans = 0;

        for (int i = 0; i < wideMap.length; i++) {
            for (int j = 0; j < wideMap[0].length; j++) {
                if (wideMap[i][j] == '[') {
                    ans += (i * 100) + j;
                }
            }
        }

        return ans;
    }
}