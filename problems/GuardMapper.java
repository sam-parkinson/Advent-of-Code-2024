package problems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class GuardMapper {
    private int[][] directions = {
        {-1,0}, {0,1}, {1,0}, {0,-1}
    };

    private char[][] map;
    private int[] start;
    private int visitedCount, loopCount, turnCount;
    private HashSet<Integer> visited, turns;

    public GuardMapper(String address) {
        visitedCount = 0;
        loopCount = 0;
        turnCount = 0;
        visited = new HashSet<Integer>();
        turns = new HashSet<Integer>();
        parseInput(address);
        findStart();
        trackGuard();
        System.out.println(visited.size());
        checkForLoops();
        /* for (char[] line : map) {
            System.out.println(line);
        } */
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
            // replace hash setup with: bit shift x 8 bits to the left, add y -- will be unique;
            visited.add(makePointMask(x, y));
            dir = checkForTurn(x, y, dir, guardPath);
            
            x += directions[dir][0];
            y += directions[dir][1];

            if (guardPath[x][y] == '.') {
                visitedCount++;
                guardPath[x][y] = String.valueOf(dir).charAt(0);
            }
            
        }
    }

    private void checkForLoops() {
        for (Integer point : visited) {
            if (hasLoop(point)) {
                loopCount++;
            } 
        }
    }

    private boolean hasLoop(Integer point) {
        int a = point >> 8, b = point & 255;
        
        char[][] guardPath = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                guardPath[i][j] = map[i][j];
            }
        }

        int x = start[0], y = start[1], dir = 0;
        int p = map.length, q = map[0].length;

        int nextX = a + directions[dir][0], nextY = b + directions[dir][1];

        if (guardPath[nextX][nextY] == '^') {
            return false;
        }
        
        //dir = (dir + 1) % 4;

        guardPath[nextX][nextY] = '#';
        int count = 0;
        while (isInbounds(x, y) && count < (4*p*q)) {
            dir = checkForTurn(x, y, dir, guardPath);
            
            x += directions[dir][0];
            y += directions[dir][1];

            if (!turns.add(makeTurnMask(x, y, dir))) {
                return true;
            };
            
            count++;
        }
        return count == (4*p*q);
    }

    private boolean isInbounds(int x, int y) {
        return x > 0 && y > 0 && x < map.length - 1 && y < map[0].length - 1;
    }

    private boolean isLoop(Integer point) {
        return turns.contains(point);
    }

    private int checkForTurn(int x, int y, int dir, char[][] map) {
        while (map[x + directions[dir][0]][y + directions[dir][1]] == '#') {
            dir = (dir + 1) % directions.length;
            turnCount++;
        }
        return dir;
    }

    private Integer makePointMask(int x, int y) {
        return (x << 8) | y;
    }

    private Integer makeTurnMask(int x, int y, int dir) {
        return ((x << 8) | y << 1) | dir ;
    }

}

// part 2 guesses:

// 163: too low
// 353: too low
// 388: no commentary
// 654: no commentary
// 1269: no commentary
// 1543: no commentary