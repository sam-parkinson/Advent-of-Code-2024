import problems.*;

public class Driver {
    public static void main(String[] args) {
        ListSorter listSorter = new ListSorter("inputs/day1.txt");
        LevelChecker levelChecker = new LevelChecker("inputs/day2.txt");
        InstructionFinder instructionFinder = new InstructionFinder("inputs/day3.txt");
        WordFinder wordFinder = new WordFinder("inputs/day4.txt");
        PageSorter pageSorter = new PageSorter("inputs/day5.txt");
        GuardMapper guardMapper = new GuardMapper("inputs/day6.txt");

        System.out.println("Advent of Code 2024");

        System.out.println();
        System.out.println("Problem 1.1: " + listSorter.getListDistance());
        System.out.println("Problem 1.2: " + listSorter.getListSimilarity());

        System.out.println();
        System.out.println("Problem 2.1: " + levelChecker.getSafeLevelsCount());
        System.out.println("Problem 2.2: " + levelChecker.getDampenedLevelsCount());

        System.out.println();
        System.out.println("Problem 3.1: " + instructionFinder.getSummedProducts());
        System.out.println("Problem 3.2: " + instructionFinder.getTrueSummedProducts());

        System.out.println();
        System.out.println("Problem 4.1: " + wordFinder.getXmasCounter());
        System.out.println("Problem 4.2: " + wordFinder.getCrossMasCounter());

        System.out.println();
        System.out.println("Problem 5.1: " + pageSorter.getSumOfCorrectPages());
        System.out.println("Problem 5.2: " + pageSorter.getSumOfIncorrectPages());

        System.out.println();
        System.out.println("Problem 6.1: " + guardMapper.getVisitedCount());
        System.out.println("Problem 6.2: " + guardMapper.getLoopCount());
    }
}


// 1543: no commentary