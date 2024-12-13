package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class AntinodeFinder {
    private char[][] map;
    private HashMap<Character, ArrayList<int[]>> nodesMap;
    private HashSet<String> simpleAntinodes;
    private HashSet<String> resonantAntinodes;

    public AntinodeFinder(String address) {
        nodesMap = new HashMap<Character, ArrayList<int[]>>();
        simpleAntinodes = new HashSet<String>();
        resonantAntinodes = new HashSet<String>();
        parseInput(address);
        makeNodesMap();
        findAntinodes();
    }

    public int getSimpleAntinodeCount() {
        return this.simpleAntinodes.size();
    }

    public int getResonantAntinodeCount() {
        return this.resonantAntinodes.size();
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                strArr.add(stdin.next());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        map = new char[strArr.size()][];
        for (int i = 0; i < map.length; i++) {
            map[i] = strArr.get(i).toCharArray(); 
        }
    }

    private void makeNodesMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != '.') {
                    addCoordsToNodeMap(i, j, map[i][j]);
                    addToResonantAntinodes(i, j);
                }
            }
        }
    }

    private void addCoordsToNodeMap(int x, int y, char c) {
        nodesMap.putIfAbsent(c, new ArrayList<int[]>());

        ArrayList<int[]> arr = nodesMap.get(c);

        arr.add(new int[] {x, y});
    }

    private void findAntinodes() {
        for (Character c : nodesMap.keySet()) {
            ArrayList<int[]> list = nodesMap.get(c);
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    addAntinode(list.get(i), list.get(j), c);
                }
            }
        }
    }

    private void addAntinode(int[] arr1, int[] arr2, char c) {
        int x1 = arr1[0], y1 = arr1[1], x2 = arr2[0], y2 = arr2[1];

        int xSlope = Math.abs(x1 - x2), ySlope = Math.abs(y1 - y2);

        int a1, b1, a2, b2;
        if (y1 < y2) {
            a1 = x1 - xSlope;
            b1 = y1 - ySlope;
            a2 = x2 + xSlope;
            b2 = y2 + ySlope;
        } 
        else {
            a1 = x1 - xSlope;
            b1 = y1 + ySlope;
            a2 = x2 + xSlope;
            b2 = y2 - ySlope;
        }

        if (isInbounds(a1, b1)) {
            addToSimpleAntinodes(a1, b1);
        }

        if (isInbounds(a2, b2)) {
            addToSimpleAntinodes(a2, b2);
        }
        
        // add new methods here -- keep moving in direction of slope until you go out of bounds
    }

    private boolean isInbounds(int a, int b) {
        return a >= 0 && a < map[0].length && b >= 0 && b < map.length;
    }

    private void addToSimpleAntinodes(int a, int b) {
        StringBuilder key = new StringBuilder();
        key.append(a);
        key.append(',');
        key.append(b);
        simpleAntinodes.add(key.toString());
        resonantAntinodes.add(key.toString());
    }

    private void addToResonantAntinodes(int a, int b) {
        StringBuilder key = new StringBuilder();
        key.append(a);
        key.append(',');
        key.append(b);
        resonantAntinodes.add(key.toString());
    }
}
