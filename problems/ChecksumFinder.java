package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ChecksumFinder {
    private int[] diskMap, fragmentedDisk, compactedDisk;
    private ArrayList<Integer> disk;
    private HashMap<Integer, PriorityQueue<Integer>> spaceMap;
    private long checksum, trueChecksum;

    public ChecksumFinder(String address) {
        disk = new ArrayList<Integer>();
        spaceMap = new HashMap<Integer, PriorityQueue<Integer>>();
        
        parseInput(address);
        mapDisk();

        fragmentedDisk = new int[disk.size()];
        fragmentDisk();

        compactedDisk = new int[disk.size()];
        compactDisk();

        checksum = findChecksum(fragmentedDisk);
        trueChecksum = findChecksum(compactedDisk);
    }

    public long getChecksum() {
        return this.checksum;
    }

    public long getTrueChecksum() {
        return this.trueChecksum;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            diskMap = makeDiskMap(stdin.nextLine().toCharArray());

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int[] makeDiskMap(char[] arr) {
        int[] ans = new int[arr.length];

        for (int i = 0; i < ans.length; i++) {
            ans[i] = Character.getNumericValue(arr[i]);
        }

        return ans;
    }

    private void mapDisk() {
        for (int i = 0; i < diskMap.length; i++) {
            int x = i % 2 == 0 ? i / 2 : -1;
            int start = disk.size();
            for (int j = 0; j < diskMap[i]; j++) {
                disk.add(x);
            }
            if (i % 2 != 0) {
                if (!spaceMap.containsKey(diskMap[i])) {
                    spaceMap.put(diskMap[i], new PriorityQueue<Integer>());
                }

                PriorityQueue<Integer> queue = spaceMap.get(diskMap[i]);
                queue.add(start);
            }
        }
    }

    private void fragmentDisk() {
        Arrays.fill(fragmentedDisk, -1);
        int i = 0, j = disk.size() - 1;

        while (i <= j) {
            while (disk.get(i) != -1 && i <= j) {
                fragmentedDisk[i] = disk.get(i);
                i++;
            }

            while (disk.get(i) == -1 && i <= j) {
                while (disk.get(j) == -1 && i <= j) {
                    j--;
                }

                fragmentedDisk[i] = disk.get(j);
                i++;
                j--;
            }
        }
    }

    private void compactDisk() {
        for (int i = 0; i < compactedDisk.length; i++) {
            compactedDisk[i] = disk.get(i);
        }

        int i = disk.size() - 1;

        while (i >= 0) {
            while (i >= 0 && compactedDisk[i] == -1) {
                i--;
            }

            int j = i;
            int c = compactedDisk[i];
            while (i >= 0 && compactedDisk[i] == c) {
                i--;
            }

            int fileSize = j - i;
            int gapSize = 0;
            Integer index = i;

            for (Integer key : spaceMap.keySet()) {
                if (key >= fileSize && spaceMap.get(key).peek() <= index) {
                    index = spaceMap.get(key).peek();
                    gapSize = key;
                }
            }

            if (gapSize > 0) {
                spaceMap.get(gapSize).poll();
                if (spaceMap.get(gapSize).isEmpty()) {
                    spaceMap.remove(gapSize);
                }

                for (int k = 0; k < fileSize; k++) {
                    compactedDisk[index + k] = c;
                    compactedDisk[i + 1 + k] = -1;
                }

                int newGap = gapSize - fileSize;

                if (!spaceMap.containsKey(newGap)) {
                    spaceMap.put(newGap, new PriorityQueue<Integer>());
                }

                spaceMap.get(newGap).add(index + fileSize);
            }
        }
    }

    private long findChecksum(int[] arr) {
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                ans += arr[i] * i;
            }
        }
        return ans;
    }
}
