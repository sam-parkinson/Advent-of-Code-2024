package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
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
        this.treeTimeStamp = findTreeTimeStamp();
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
            int[] pos = advanceRobot(robot, time);

            placeInQuadrant(pos[0], pos[1]);
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

    private int findTreeTimeStamp() {
        boolean foundSet = false;
        int i = 0;
        int[] maxTime = {0, 0};
        while (!foundSet && i < HEIGHT * WIDTH) {
            HashSet<Integer> set = new HashSet<>();
            
            for (int[] robot : robots) {
                set.add(makeRobotInt(robot, i));
            }

            if (set.size() > maxTime[0]) {
                maxTime[0] = set.size();
                maxTime[1] = i;
            }

            if (set.size() == robots.size()) {
                foundSet = true;
            }
            i++;

        }

        return maxTime[1];
    }

    private int makeRobotInt(int[] robot, int t) {
        int[] pos = advanceRobot(robot, t);

        return pos[0] * 1000 + pos[1];
    }

    private int[] advanceRobot(int[] robot, int t) {
        int x = (robot[0] + (robot[2] * t)) % WIDTH;
        int y = (robot[1] + (robot[3] * t)) % HEIGHT;

        if (x < 0) {
            x += WIDTH;
        }

        if (y < 0) {
            y += HEIGHT;
        }

        return new int[] {x, y};
    }
}
