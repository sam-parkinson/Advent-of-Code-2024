package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class PageSorter {
    private ArrayList<Integer[]> initialPages;
    private HashSet<String> orderSet;

    private int sumOfCorrectPages, sumOfIncorrectPages;

    public PageSorter(String address) {
        sumOfCorrectPages = 0;
        parseInput(address);
        checkPages();
    }

    public int getSumOfCorrectPages() {
        return this.sumOfCorrectPages;
    }

    public int getSumOfIncorrectPages() {
        return this.sumOfIncorrectPages;
    }

    private void parseInput(String address) {
        initialPages = new ArrayList<Integer[]>();
        orderSet = new HashSet<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            boolean part1 = true;

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                if (str.length() == 0) {
                    part1 = false;
                } else if (part1 == true) {
                    orderSet.add(str);
                } else {
                    initialPages.add(splitInputLine(str, ","));
                }
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Integer[] splitInputLine(String str, String delimiter) {
        String[] strArr = str.split(delimiter);
        Integer[] intArr = new Integer[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.parseInt(strArr[i]);
        }

        return intArr;
    }

    private void checkPages() {
        for (Integer[] page : initialPages) {
            if (checkPage(page)) {
                sumOfCorrectPages += page[page.length / 2];
            } else {
                sumOfIncorrectPages += processIncorrectPage(page);
            }       
        }
    }

    private boolean checkPage(Integer[] page) {
        for (int i = 0; i < page.length - 1; i++) {
            if (!orderSet.contains(makeInstruction(page[i], page[i + 1]))) {
                return false;   
            }       
        }
        
        return true;
    }

    private int processIncorrectPage(Integer[] page) {
        Integer[] clone = page.clone();
        HashMap<Integer, Integer> pageCount = countPossiblePages(page);

        Arrays.sort(clone, new Comparator<Integer> () {
            public int compare(Integer a, Integer b) {
                return pageCount.get(b) - pageCount.get(a);
            }
        });

        return clone[clone.length / 2];
    }

    private HashMap<Integer, Integer> countPossiblePages(Integer[] page) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Integer i : page) {
            map.put(i, 0);
        }

        for (int i = 0; i < page.length; i++) {
            for (int j = 0; j < page.length; j++) {
                if (i != j) {
                    int a = page[i], b = page[j];
                    String str = makeInstruction(a, b);
                    if (orderSet.contains(str)) {
                        map.put(a, map.get(a) + 1);
                    }
                }
            }
        }

        return map;
    }

    private String makeInstruction(int a, int b) {
        StringBuilder sb = new StringBuilder("");
        sb.append(a);
        sb.append('|');
        sb.append(b);

        return sb.toString();
    }
}
