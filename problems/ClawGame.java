package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ClawGame {
    private class Game {
        int aX, aY, bX, bY, prizeX, prizeY, minTokenCost;
        long truePrizeX, truePrizeY, trueMinTokenCost;

        private Game (int[] buttonA, int[] buttonB, int[] prize) {
            this.aX = buttonA[0];
            this.aY = buttonA[1];
            this.bX = buttonB[0];
            this.bY = buttonB[1];
            this.prizeX = prize[0];
            this.prizeY = prize[1];
            this.truePrizeX = prizeX + 10000000000000L;
            this.truePrizeY = prizeY + 10000000000000L;
        }

        private void findMinCost() {
            this.minTokenCost = playGame();
            this.trueMinTokenCost = playLongGame();
        }

        private int playGame() {
            int denom = Math.abs(aX * bY - aY * bX);

            int numer1 = Math.abs(bX * prizeY - bY * prizeX);
            int numer2 = Math.abs(aX * prizeY - aY * prizeX);

            if (numer1 % denom == 0 && numer2 % denom == 0) {
                int pressA = numer1 / denom;
                int pressB = numer2 / denom;

                if (pressA >= 100 || pressB >= 100) {
                    return Integer.MAX_VALUE;
                }
                return (3 * pressA + pressB);
            }
            return Integer.MAX_VALUE;
        }

        private long playLongGame() {
            long denom = Math.abs(aX * bY - aY * bX);

            long numer1 = Math.abs(bX * truePrizeY - bY * truePrizeX);
            long numer2 = Math.abs(aX * truePrizeY - aY * truePrizeX);

            if (numer1 % denom == 0 && numer2 % denom == 0) {
                long pressA = numer1 / denom;
                long pressB = numer2 / denom;

                return (3 * pressA + pressB);
            }
            return Long.MAX_VALUE;
        }
    }
    private ArrayList<Game> games;
    private int totalMinCost;
    private long totalTrueMinCost;

    public ClawGame(String address) {
        games = new ArrayList<Game>();
        totalMinCost = 0;
        totalTrueMinCost = 0;
        parseInput(address);
        playGames();
    }

    public int getTotalMinCost() {
        return this.totalMinCost;
    }

    public long getTotalTrueMinCost() {
        return this.totalTrueMinCost;
    }

    private void parseInput(String address) {
        ArrayList<String[]> inputArr = new ArrayList<>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            int i = 0;
            String[] arr = new String[3];
            while (stdin.hasNextLine()) {
                if (i < 3) {
                    arr[i] = stdin.nextLine();
                    i++;
                } else {
                    stdin.nextLine();
                    inputArr.add(arr.clone());
                    i = 0;
                    arr = new String[3];
                }
            }
            inputArr.add(arr.clone());

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (String[] strArr : inputArr) {
            int[][] intArr = new int[strArr.length][];
            for (int i = 0; i < intArr.length; i++) {
                String[] str = strArr[i].split("\\+|,|=");
                int[] arr = new int[2];
                arr[0] = Integer.parseInt(str[1]);
                arr[1] = Integer.parseInt(str[3]);
                intArr[i] = arr;
            }

            games.add(new Game(intArr[0], intArr[1], intArr[2]));
        }
    }

    private void playGames() {
        for (Game game : games) {
            game.findMinCost();
            if (game.minTokenCost < Integer.MAX_VALUE) {
                totalMinCost += game.minTokenCost;
            }
            if (game.trueMinTokenCost < Long.MAX_VALUE) {
                totalTrueMinCost += game.trueMinTokenCost;
            }
        }
    }
}