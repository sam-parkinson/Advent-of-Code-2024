package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class HikingGuide {
    private int[][] directions = {
        {0, 1}, {1, 0}, {-1, 0}, {0, -1}
    };
    private int[][] map;
    private ArrayList<int[]>trailheads;
    private int trailheadScoreSum, trailheadRatingSum;

    public HikingGuide(String address) {
        trailheads = new ArrayList<int[]>();
        trailheadScoreSum = 0;
        trailheadRatingSum = 0;
        parseInput(address);
        followTrails();
    }

    public int getTrailheadScoreSum() {
        return this.trailheadScoreSum;
    }

    public int getTrailheadRatingSum() {
        return this.trailheadRatingSum;
    }

    private void parseInput(String address) {
        ArrayList<char[]> input = new ArrayList<char[]>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                input.add(stdin.nextLine().toCharArray());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        map = new int[input.size()][];
        for (int i = 0; i < map.length; i++) {
            char[] charArr = input.get(i);
            map[i] = new int[charArr.length];
            for (int j = 0; j < charArr.length; j++) {
                map[i][j] = Character.getNumericValue(charArr[j]);
                if (map[i][j] == 0) {
                    trailheads.add(new int[] {i, j, 0, 0});
                }
            }
        }
    }

    private void followTrails() {
        for (int[] trailhead : trailheads) {
            findTrailSum(trailhead);
            findTrailRating(trailhead);
            trailheadScoreSum += trailhead[2];
            trailheadRatingSum += trailhead[3];
        }
    }

    private void findTrailSum(int[] trailhead) {
        int score = 0;
        HashSet<Integer> visited = new HashSet<Integer>();
        Queue<int[]> queue = new LinkedList<int[]>();

        visited.add(((trailhead[0] + 1) << 8) | (trailhead[1] + 1));
        queue.add(new int[] {trailhead[0], trailhead[1]});

        while (!queue.isEmpty()) {
            int[] position = queue.remove();
            int x = position[0], y = position[1], c = map[x][y];
            if (c == 9) {
                score++;
            } else {
                for (int[] direction : directions) {
                    int nextX = x + direction[0], nextY = y + direction[1];
                    if (isValid(nextX, nextY) && map[nextX][nextY] - c == 1) {
                        if (visited.add(((nextX + 1) << 8) | (nextY + 1))) {
                            queue.add(new int[] {nextX, nextY});
                        }
                    }
                }
            }
        }

        trailhead[2] = score;
    }

    private void findTrailRating(int[] trailhead) {
        int rating = 0;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(new int[] {trailhead[0], trailhead[1]});

        while (!queue.isEmpty()) {
            int[] position = queue.remove();
            int x = position[0], y = position[1], c = map[x][y];
            if (c == 9) {
                rating++;
            } else {
                for (int[] direction : directions) {
                    int nextX = x + direction[0], nextY = y + direction[1];
                    if (isValid(nextX, nextY) && map[nextX][nextY] - c == 1) {                        
                        queue.add(new int[] {nextX, nextY});                        
                    }
                }
            }
        }

        trailhead[3] = rating;
    }

    private boolean isValid(int i, int j) {
        return (i >= 0 && j >= 0 && i < map.length && j < map[i].length);
    }
}