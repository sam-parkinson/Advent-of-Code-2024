package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GuardMapper {
    private int[][] directions = {
        {-1,0}, {0,1}, {1,0}, {0,-1}
    };

    private char[][] map;
    private int[] start;
    private int visitedCount, loopCount;
    private HashMap<Integer, int[]> visited;

    public GuardMapper(String address) {
        visitedCount = 0;
        loopCount = 0;
        visited = new HashMap<Integer, int[]>();
        parseInput(address);
        findStart();
        trackGuard();
        checkForLoops();
    }

    public int getVisitedCount() {
        return this.visitedCount;
    }

    public int getLoopCount() {
        return this.loopCount;
    }

    private void parseInput(String address) {
        ArrayList<String> arr = new ArrayList<>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                arr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        map = new char[arr.size()][arr.get(0).length()];
        for (int i = 0; i < arr.size(); i++) {
            map[i] = arr.get(i).toCharArray();
        }
    }

    private void findStart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '^') {
                    start = new int[] {i, j};
                    return;
                }
            }
        }
    }

    private void trackGuard() {
        char[][] guardPath = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                guardPath[i][j] = map[i][j];
            }
        }

        int x = start[0], y = start[1], dir = 0;

        guardPath[x][y] = String.valueOf(dir).charAt(0);

        visitedCount++;
        while (isInbounds(x, y)) {
            dir = checkForTurn(x, y, dir, guardPath);
            
            x += directions[dir][0];
            y += directions[dir][1];

            if (guardPath[x][y] == '.') {
                visitedCount++;
                guardPath[x][y] = String.valueOf(dir).charAt(0);
            }
            visited.put(makePointMask(x, y), new int[] {x, y});
        }
        
    }

    private void checkForLoops() {
        for (int[] point : visited.values()) {
            if (hasLoop(point)) {
                loopCount++;
            } 
        }
    }

    private boolean hasLoop(int[] point) {
        int a = point[0], b = point[1];

        int x = start[0], y = start[1], dir = 0;
        int p = map.length, q = map[0].length;

        if (map[a][b] == '^') {
            return false;
        }

        map[a][b] = '#';
        int count = 0;
        while (isInbounds(x, y) && count < (4*p*q)) {
            dir = checkForTurn(x, y, dir, map);
            x += directions[dir][0];
            y += directions[dir][1];
            count++;
        }
        map[a][b] = '.';
        return count == (4*p*q);
    }

    private boolean isInbounds(int x, int y) {
        return x > 0 && y > 0 && x < map.length - 1 && y < map[0].length - 1;
    }

    private int checkForTurn(int x, int y, int dir, char[][] map) {
        while (map[x + directions[dir][0]][y + directions[dir][1]] == '#') {
            dir = (dir + 1) % directions.length;
        }
        return dir;
    }

    private Integer makePointMask(int x, int y) {
        return (x << 8) | y;
    }
}

// part 2 guesses:

// 163: too low
// 353: too low
// 388: no commentary
// 654: no commentary
// 1269: no commentary
// 1543: no commentary