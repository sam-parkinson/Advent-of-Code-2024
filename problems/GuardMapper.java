package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GuardMapper {
    private int[][] directions = {
        {-1,0}, {0,1}, {1,0}, {0,-1}
    };

    private char[][] map;
    private int[] start;
    private int visitedCount, loopCount;

    public GuardMapper(String address) {
        visitedCount = 0;
        loopCount = 0;
        parseInput(address);
        findStart();
        trackGuard();
        for (char[] line : map) {
            System.out.println(line);
        }
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
        int x = start[0], y = start[1], dir = 0;

        map[x][y] = 'X';
        visitedCount++;
        while (isInbounds(x, y)) {
            dir = checkForTurn(x, y, dir);

            x += directions[dir][0];
            y += directions[dir][1];

            if (map[x][y] != 'X') {
                visitedCount++;
                map[x][y] = 'X';
                /* if (!(start[0] == x + directions[dir][0] && start[1] == y + directions[dir][1])) {
                    loopCount += checkLoop(x, y, (dir + 1) % 4);
                } */
            }/*  else if (!(start[0] == x + directions[dir][0] && start[1] == y + directions[dir][1])) {
                loopCount += checkLoop(x, y, (dir + 1) % 4);
            } */

            if (!(start[0] == x + directions[dir][0] && start[1] == y + directions[dir][1])) {
                loopCount += checkLoop(x, y, (dir + 1) % 4);
            }
        }
    }

    private int checkLoop(int a, int b, int dir) {
        int x = a + directions[dir][0];
        int y = b + directions[dir][1];

        while (isInbounds(x, y) && !(x == a && y == b)) {
            dir = checkForTurn(x, y, dir);
            
            x += directions[dir][0];
            y += directions[dir][1];
        }

        
        return (x == a && y == b) ? 1 : 0;
        // see if you can navigate similar to how you have previously navigated
        // if you can, and you get to starting x, y in the correct direction, increment loopCount
    }

    private boolean isInbounds(int x, int y) {
        return x > 0 && y > 0 && x < map.length - 1 && y < map[0].length - 1;
    }

    private int checkForTurn(int x, int y, int dir) {
        while (map[x + directions[dir][0]][y + directions[dir][1]] == '#') {
            dir = (dir + 1) % directions.length;
        }

        return dir;
    }

    
}

