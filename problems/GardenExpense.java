package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GardenExpense {
    private int[][] directions = {
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };
    private char[][] gardenMap;
    private boolean[][] visited;
    private int perimPrice;

    public GardenExpense(String address) {
        parseInput(address);
        visited = new boolean[gardenMap.length][gardenMap[0].length];
        perimPrice = 0;
        calculatePrices();
    }

    public int getPerimPrice() {
        return this.perimPrice;
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                strArr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        gardenMap = new char[strArr.size()][];

        for (int i = 0; i < gardenMap.length; i++) {
            gardenMap[i] = strArr.get(i).toCharArray();
        }
    }

    private void calculatePrices() {
        for (int i = 0; i < gardenMap.length; i++) {
            for (int j = 0; j < gardenMap[i].length; j++) {
                if (!visited[i][j]) {
                    calculatePrice(i, j);
                }
            }
        }
    }

    private void calculatePrice(int a, int b) {
        int area = 0, perimeter = 0;
        boolean[][] inRegion = new boolean[gardenMap.length][gardenMap[0].length];

        char c = gardenMap[a][b];
        

        Queue<int[]> queue = new LinkedList<>();
        queue.add (new int[] {a, b});

        while (!queue.isEmpty()) {
            int[] coords = queue.remove();
            int x = coords[0], y = coords[1];

            if (gardenMap[x][y] == c && visited[x][y] == false) {
                visited[x][y] = true;
                inRegion[x][y] = true;

                area++;
                int perimAdd = 4;
                for (int[] dir : directions) {
                    int nX = x + dir[0], nY = y + dir[1];
                    if (isInbounds(nX, nY)) {
                        if (inRegion[nX][nY] && gardenMap[nX][nY] == c) {
                            perimAdd -= 2;
                        }

                        if (!visited[nX][nY]) {
                            queue.add(new int[] {nX, nY});
                        }                        
                    }
                }
                perimeter += perimAdd;
            }
        }

        perimPrice += area * perimeter;
    }

    private boolean isInbounds(int x, int y) {
        return x >= 0 && y >= 0 && x < gardenMap.length && y < gardenMap[0].length;
    }
}