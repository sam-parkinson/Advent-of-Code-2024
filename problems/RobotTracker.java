package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RobotTracker {
    private final int WIDTH = 101, HEIGHT = 103;

    private ArrayList<int[]> robots;
    private int[] quadrantCount;
    private int totalSafetyFactor, treeTimeStamp;

    public RobotTracker(String address) {
        this.robots = new ArrayList<int[]>();
        this.quadrantCount = new int[4];
        parseInput(address);
        findRobotPositions(100);
        this.totalSafetyFactor = findTotalSafetyFactor();
    }

    public int getTotalSafetyFactor() {
        return this.totalSafetyFactor;
    }

    public int getTreeTimeStamp() {
        return this.treeTimeStamp;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                robots.add(makeRobot(stdin.nextLine()));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int[] makeRobot(String str) {
        String[] strArr = str.split("=|,|\s+");
        int[] robot = new int[4];
        
        robot[0] = Integer.parseInt(strArr[1]);
        robot[1] = Integer.parseInt(strArr[2]);
        robot[2] = Integer.parseInt(strArr[4]);
        robot[3] = Integer.parseInt(strArr[5]);

        return robot;
    }

    private void findRobotPositions(int time) {
        for (int[] robot : robots) {
            int x = (robot[0] + (robot[2] * time)) % WIDTH;
            int y = (robot[1] + (robot[3] * time)) % HEIGHT;

            if (x < 0) {
                x += WIDTH;
            }

            if (y < 0) {
                y += HEIGHT;
            }

            placeInQuadrant(x, y);
        }
    }

    private void placeInQuadrant(int x, int y) {
        if (x < WIDTH / 2 && y < HEIGHT / 2) {
            quadrantCount[0]++;
        } else if (x < WIDTH / 2 && y > HEIGHT / 2) {
            quadrantCount[1]++;
        } else if (x > WIDTH / 2 && y > HEIGHT / 2) {
            quadrantCount[2]++;
        } else if (x > WIDTH / 2 && y < HEIGHT / 2) {
            quadrantCount[3]++;
        }
    }

    private int findTotalSafetyFactor() {
        int ans = 1;
        for (int quad : quadrantCount) {
            ans *= quad;
        }
        return ans;
    }
}
