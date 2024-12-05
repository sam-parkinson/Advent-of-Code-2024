import problems.*;

public class Driver {
    public static void main(String[] args) {
        ListSorter listSorter = new ListSorter("inputs/day1.txt");
        LevelChecker levelChecker = new LevelChecker("inputs/day2.txt");

        System.out.println("Advent of Code 2024");

        System.out.println();
        System.out.println("Problem 1.1: " + listSorter.getListDistance());
        System.out.println("Problem 1.2: " + listSorter.getListSimilarity());

        System.out.println();
        System.out.println("Problem 2.1: " + levelChecker.getSafeLevelsCount());
        System.out.println("Problem 2.2: " + levelChecker.getDampenedLevelsCount());
    }
}
