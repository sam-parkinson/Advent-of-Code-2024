package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class WordFinder {
    private int[][] directions = {
        {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}
    };
    private char[] xmas = {'X','M','A','S'};
    
    private char[][] grid;
    private int xmasCounter, crossMasCounter;

    public WordFinder(String address) {
        xmasCounter = 0;
        crossMasCounter = 0;
        parseInput(address);
        findXmasCount();
    }

    public int getXmasCounter() {
        return this.xmasCounter;
    }

    public int getCrossMasCounter() {
        return this.crossMasCounter;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);
            ArrayList<char[]> gridList = new ArrayList<>();
            while (stdin.hasNextLine()) {
                gridList.add(stdin.nextLine().toCharArray());
            }

            stdin.close();
            
            this.grid = new char[gridList.size()][];
            for (int i = 0; i < gridList.size(); i++) {
                grid[i] = gridList.get(i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void findXmasCount() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 'X')
                    checkIfXmas(i, j);
                if (grid[i][j] == 'A' && i > 0 && i < grid.length - 1 && j > 0 && j < grid[i].length - 1)
                    checkifCrossMas(i, j);
            }
        }
    }

    private void checkIfXmas(int i, int j) {
        for (int[] direction : directions) {
            int letter = 1, x = i + direction[0], y = j + direction[1];
            while (letter < 4 && x < grid.length && x >= 0 && y < grid[i].length
                && y >= 0 && xmas[letter] == grid[x][y]) {

                letter++;
                x += direction[0];
                y += direction[1];
            }
            if (letter == 4) {
                xmasCounter++;
            }
        }       
    }

    private void checkifCrossMas(int i, int j) {
        if (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'M' && grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'S') {
            crossMasCounter++;
            return;
        }

        if (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'S' && grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'M') {
            crossMasCounter++;
            return;
        }

        if (grid[i-1][j-1] == 'S' && grid[i-1][j+1] == 'M' && grid[i+1][j-1] == 'S' && grid[i+1][j+1] == 'M') {
            crossMasCounter++;
            return;
        }

        if (grid[i-1][j-1] == 'M' && grid[i-1][j+1] == 'S' && grid[i+1][j-1] == 'M' && grid[i+1][j+1] == 'S') {
            crossMasCounter++;
            return;
        }
    }
}