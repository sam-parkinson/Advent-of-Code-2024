package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelChecker {
    private ArrayList<ArrayList<Integer>> levels;
    private int safeLevelsCount, dampenedLevelsCount;

    public LevelChecker(String address) {
        levels = new ArrayList<ArrayList<Integer>>(1000);
        safeLevelsCount = 0;
        dampenedLevelsCount = 0;
        parseInput(address);
        reviewLevels();
    }

    public int getSafeLevelsCount() {
        return this.safeLevelsCount;
    }

    public int getDampenedLevelsCount() {
        return this.dampenedLevelsCount;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                levels.add(parseLine(stdin.nextLine()));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ArrayList<Integer> parseLine(String str) {
        String[] strArr = str.split(" ");
        ArrayList<Integer> intArr = new ArrayList<Integer>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            intArr.add(Integer.parseInt(strArr[i]));
        }
        return intArr;
    }

    private void reviewLevels() {
        for (ArrayList<Integer> level : levels) {
            if (checkLevelSafety(level)) {
                safeLevelsCount++;
                dampenedLevelsCount++;
            } else if (checkDampenedLevelSafety(level)) {
                dampenedLevelsCount++;
            }
        }
    }
    
    private boolean checkLevelSafety(ArrayList<Integer> level) {
        boolean isNegative = level.get(0) - level.get(1) < 0;
        for (int i = 0; i < level.size() - 1; i++) {
            Integer a = level.get(i), b = level.get(i + 1);
            
            int diff = Math.abs(a - b);
            boolean stillNegative = a - b < 0;

            if (diff < 1 || diff > 3 || isNegative != stillNegative) {
                return false;
            } 
        }
        return true;
    }

    private boolean checkDampenedLevelSafety(ArrayList<Integer> level) {
        boolean isDampenable = false;
        int i = 0;
        while (isDampenable == false && i < level.size()) {
            ArrayList<Integer> trimmed = new ArrayList<>(level);
            trimmed.remove(i);
            isDampenable = checkLevelSafety(trimmed);
            i++;
        }

        return isDampenable;
    }
}
