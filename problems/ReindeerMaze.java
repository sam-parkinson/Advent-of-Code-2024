package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ReindeerMaze {
    private class Reindeer {
        private ArrayList<Integer> path;
        private int position, score, direction;

        private Reindeer(int position, int score, int direction, ArrayList<Integer> path) {
            this.position = position;
            this.score = score;
            this.direction = direction;
            this.path = path;
        }
    }

    private int[][] directions = {
        {0,1}, {1,0}, {0,-1}, {-1, 0}
    };
    private char[][] maze;
    private int[][][] visited;
    private int[] start, end;
    private int minScore, seatCount, endSeat;

    public ReindeerMaze(String address) {
        parseInput(address);
        this.start = new int[] {maze.length - 2, 1};
        this.end = new int[] {1, maze[0].length - 2};
        this.endSeat = makeSeatId(1, maze[0].length - 2);
        makeVisitedArr();
        runMaze(start[0], start[1], 0, 0);
    }

    public int getMinScore() {
        return this.minScore;
    }

    public int getSeatCount() {
        return this.seatCount;
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                strArr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.maze = new char[strArr.size()][];
        for (int i = 0; i < maze.length; i++) {
            maze[i] = strArr.get(i).toCharArray();
        }
    }

    private void makeVisitedArr() {
        this.visited = new int[maze.length][maze[0].length][4];
        for (int[][] seat : visited) {
            for (int[] dir : seat) {
                Arrays.fill(dir, Integer.MAX_VALUE);
            }    
        }
    }

    private void runMaze(int x, int y, int points, int direction) {
        int startId = makeSeatId(x, y);
        Queue<Reindeer> queue = new LinkedList<>();
        ArrayList<Reindeer> reachedEnd = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        path.add(startId);

        Reindeer start = new Reindeer(startId, points, direction, path);
        queue.add(start);

        while (!queue.isEmpty()) {
            Reindeer current = queue.remove();
            int[] pos = reverseSeatId(current.position);
            int a = pos[0], b = pos[1];
            
            if (current.position == endSeat) {
                reachedEnd.add(current);
            } else {
                for (int i = 0; i < directions.length; i++) {
                    
                    int[] dir = directions[i];
                    char nextChar = maze[a + dir[0]][b + dir[1]];

                    if (nextChar == '#') {
                        continue;
                    }

                    int tempScore = current.score;
                    int nextA = a, nextB = b;
                    if (i != current.direction) {
                        tempScore += 1000;
                    } else {
                        tempScore += 1;
                        nextA += dir[0];
                        nextB +=dir[1];
                    }

                    if (visited[nextA][nextB][i] >= tempScore) {
                        visited[nextA][nextB][i] = tempScore;
                        
                        int nextSeat = makeSeatId(nextA, nextB);

                        ArrayList<Integer> newPath = new ArrayList<>(current.path);
                        newPath.add(nextSeat);
    
                        queue.add(new Reindeer(nextSeat, tempScore, i, newPath));
                    }                        
                }
            }
        }

        int min = visited[end[0]][end[1]][0];
        for (int i = 1; i < 4; i++) {
            min = Math.min(min, visited[end[0]][end[1]][i]);
        }
        this.minScore = min;
        HashSet<Integer> seats = new HashSet<>();

        for (Reindeer reindeer : reachedEnd) {
            if (reindeer.score == min) {
                for (int seat : reindeer.path) {
                    seats.add(seat);
                }
            }
        }

        this.seatCount = seats.size();
    }

    private int makeSeatId(int x, int y) {
        return x * 1000 + y;
    }

    private int[] reverseSeatId(int seat) {
        int y = seat % 1000;
        int x = (seat - y) / 1000;

        return new int[] {x, y};
    }
}
