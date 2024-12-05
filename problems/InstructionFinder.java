package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

public class InstructionFinder {
    private Pattern basicPattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)|don't\\(\\)|do\\(\\)", Pattern.CASE_INSENSITIVE);
    
    private ArrayList<String> rawStrings;
    private ArrayList<Integer[]> multiplyInstructions, ignoredInstructions;
    private int summedProducts, trueSummedProducts;
    private boolean proceed;

    public InstructionFinder(String address) {
        rawStrings = new ArrayList<String>();
        multiplyInstructions = new ArrayList<Integer[]>();
        ignoredInstructions = new ArrayList<Integer[]>();
        summedProducts = 0;
        trueSummedProducts = 0;
        proceed = true;
        parseInput(address);
        findMultiplyInstructions();
        findSummedProducts();
    }

    public int getSummedProducts() {
        return this.summedProducts;
    }

    public int getTrueSummedProducts() {
        return this.trueSummedProducts;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                rawStrings.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void findMultiplyInstructions() {
        for (String str : rawStrings) {
            processLine(str);
        }
    }

    private void processLine(String str) {
        Matcher matcher = basicPattern.matcher(str);
        while (matcher.find()) {
            String substr = str.substring(matcher.start(), matcher.end());
            if (substr.charAt(0) == 'm') {
                String[] numStr = substr.substring(4, substr.length() - 1).split(",");
                Integer[] nums = {Integer.parseInt(numStr[0]), Integer.parseInt(numStr[1])};
                if (proceed == true) {
                    multiplyInstructions.add(nums);
                } else {
                    ignoredInstructions.add(nums);
                }
                
            } else if (substr.length() == 4) {
                proceed = true;
            } else if (substr.length() == 7) {
                proceed = false;
            }
        }
    }

    private void findSummedProducts() {
        for (Integer[] arr : multiplyInstructions) {
            trueSummedProducts += (arr[0] * arr[1]);
        }
        summedProducts = trueSummedProducts;
        for (Integer[] arr : ignoredInstructions) {
            summedProducts += (arr[0] * arr[1]);
        }
    }
}