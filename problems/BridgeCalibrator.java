package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BridgeCalibrator {
    private ArrayList<Long> targets;
    private ArrayList<long[]> termsList;
    private long totalCalibrationResult, concatCalibrationResult;

    public BridgeCalibrator(String address) {
        targets = new ArrayList<Long>();
        termsList = new ArrayList<long[]>();
        
        totalCalibrationResult = 0;
        concatCalibrationResult = 0;

        parseInput(address);
        findTotalCalibrationResult();
    }

    public long getTotalCalibrationResult() {
        return this.totalCalibrationResult;
    }

    public long getConcatCalibrationResult() {
        return this.concatCalibrationResult;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                processLine(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processLine(String str) {
        String[] strArr = str.split(" ");
        String target = strArr[0];
        targets.add(Long.parseLong(target.substring(0, target.length() - 1)));

        long[] equation = new long[strArr.length - 1];
        for (int i = 1; i < strArr.length; i++) {
            equation[i - 1] = Long.parseLong(strArr[i]);
        }
        termsList.add(equation);
    }

    private void findTotalCalibrationResult() {
        for (int i = 0; i < targets.size(); i++) {
            Long target = targets.get(i);
            long[] terms = termsList.get(i);
            if (canBeTrue(target, terms, terms[0], 1)) {
                totalCalibrationResult += target;
                concatCalibrationResult += target;
            } else if(concatCanBeTrue(target, terms, terms[0], 1)) {
                concatCalibrationResult += target;
            }
        }
    }

    private boolean canBeTrue(long target, long[] terms, long total, int i) {
        if (i == terms.length) {
            return total == target;
        }

        if (total > target) {
            return false;
        }

        long add = total + terms[i];
        long multiply = total * terms[i];

        return canBeTrue(target, terms, multiply, i + 1) || canBeTrue(target, terms, add, i + 1);
    }

    private boolean concatCanBeTrue(long target, long[] terms, long total, int i) {
        if (i == terms.length) {
            return total == target;
        }

        if (total > target) {
            return false;
        }

        long add = total + terms[i];
        long multiply = total * terms[i];
        long concat = Long.parseLong(Long.toString(total).concat(Long.toString(terms[i])));

        return concatCanBeTrue(target, terms, multiply, i + 1) || concatCanBeTrue(target, terms, add, i + 1) || concatCanBeTrue(target, terms, concat, i + 1);
    }
}
