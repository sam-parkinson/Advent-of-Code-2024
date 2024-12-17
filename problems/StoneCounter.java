package problems;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class StoneCounter {
    private class Stone {
        private long value;
        private long[] children;

        private Stone(long value) {
            this.value = value;
            this.children = findChildren();
        }

        private long[] findChildren() {
            if (value == 0) {
                return new long[] { 1 };
            } else {
                String strVal = Long.toString(value);

                if (strVal.length() > 0 && strVal.length() % 2 == 0) {
                    long a = Long.parseLong(strVal.substring(0, strVal.length() / 2));
                    long b = Long.parseLong(strVal.substring(strVal.length() / 2));

                    return new long[] { a, b };
                }

                return new long[] { value * 2024 };
            }
        }
    }
    private long[] startingArr;
    private HashMap<Long, Stone> stoneMap;
    private HashMap<Long, Long> stoneCount;

    private long countAfter25, countAfter75;


    public StoneCounter(String address) {
        stoneMap = new HashMap<>();
        stoneCount = new HashMap<>();
        parseInput(address);
        setInitialStoneState();
        
        countAfter25 = findStoneCount(25);
        countAfter75 = findStoneCount(50);
    }

    public long getCountAfter25() {
        return this.countAfter25;
    }

    public long getCountAfter75() {
        return this.countAfter75;
    }

    private void parseInput(String address) {
        String[] strArr = new String[0];
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            strArr = stdin.nextLine().split(" ");

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        startingArr = new long[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            startingArr[i] = Long.parseLong(strArr[i]);
        }
    }

    private void setInitialStoneState() {
        for (long stone : startingArr) {
            stoneMap.put(stone, new Stone(stone));
            stoneCount.put(stone, (long) 1);
        }
    }

    private long findStoneCount(int loops) {
        Queue<Long[]> queue = new LinkedList<Long[]>();

        for (int i = 0; i < loops; i++) {
            for (Long value : stoneCount.keySet()) {
                long count = stoneCount.get(value);
                stoneCount.put(value, (long) 0);

                Stone stone = stoneMap.get(value);

                for (long child : stone.children) {
                    if (!stoneMap.containsKey(child)) {
                        stoneMap.put(child, new Stone(child));
                    }

                    queue.add(new Long[] {child, count});
                }
            }

            while (!queue.isEmpty()) {
                Long[] add = queue.remove();

                stoneCount.put(add[0], stoneCount.getOrDefault(add[0], (long) 0) + add[1]);
            }
        }

        long count = 0;

        for (Long stone : stoneCount.keySet()) {
            count += stoneCount.get(stone);
        }

        return count;
    }
}