package problems;

import java.io.File;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ListSorter {
    private PriorityQueue<Integer> heap1, heap2;
    private HashMap<Integer, Integer> counter;
    private int listDistance, listSimilarity;

    public ListSorter(String address) {
        heap1 = new PriorityQueue<Integer>(1000);
        heap2 = new PriorityQueue<Integer>(1000);
        counter = new HashMap<Integer, Integer>();
        listDistance = 0;
        listSimilarity = 0;
        parseInput(address);
        calculateListInformation();
    }

    public int getListDistance() {
        return this.listDistance;
    }

    public int getListSimilarity() {
        return this.listSimilarity;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                addToHeaps(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addToHeaps(String str) {
        String[] arr = str.split(" +");
        int a = Integer.parseInt(arr[0]), b = Integer.parseInt(arr[1]);
        heap1.add(a);
        heap2.add(b);
        counter.put(b, counter.getOrDefault(b, 0) + 1);
    }

    private void calculateListInformation() {

        while (heap1.size() > 0) {
            int a = heap1.remove(), b = heap2.remove();
            listDistance += Math.abs(a - b);
            if (counter.containsKey(a)) {
                listSimilarity += counter.get(a) * a;
            }
        }
    }
}