import problems.*;

public class Driver {
    public static void main(String[] args) {
        ListSorter listSorter = new ListSorter("inputs/day1.txt");
        LevelChecker levelChecker = new LevelChecker("inputs/day2.txt");
        InstructionFinder instructionFinder = new InstructionFinder("inputs/day3.txt");
        WordFinder wordFinder = new WordFinder("inputs/day4.txt");
        PageSorter pageSorter = new PageSorter("inputs/day5.txt");
        GuardMapper guardMapper = new GuardMapper("inputs/day6.txt");
        BridgeCalibrator bridgeCalibrator = new BridgeCalibrator("inputs/day7.txt");
        AntinodeFinder antinodeFinder = new AntinodeFinder("inputs/day8.txt");
        ChecksumFinder checksumFinder = new ChecksumFinder("inputs/day9.txt");
        HikingGuide hikingGuide = new HikingGuide("inputs/day10.txt");
        StoneCounter stoneCounter = new StoneCounter("inputs/day11.txt");
        GardenExpense gardenExpense = new GardenExpense("inputs/day12.txt");
        ClawGame clawGame = new ClawGame("inputs/day13.txt");
        RobotTracker robotTracker = new RobotTracker("inputs/day14.txt");
        Warehouse warehouse = new Warehouse("inputs/day15.txt");
        ReindeerMaze reindeerMaze = new ReindeerMaze("inputs/day16.txt");
        OpcodeComputer opcodeComputer = new OpcodeComputer("inputs/day17.txt");

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

        System.out.println();
        System.out.println("Problem 7.1: " + bridgeCalibrator.getTotalCalibrationResult());
        System.out.println("Problem 7.2: " + bridgeCalibrator.getConcatCalibrationResult());

        System.out.println();
        System.out.println("Problem 8.1: " + antinodeFinder.getSimpleAntinodeCount());
        System.out.println("Problem 8.2: " + antinodeFinder.getResonantAntinodeCount());

        System.out.println();
        System.out.println("Problem 9.1: " + checksumFinder.getChecksum());
        System.out.println("Problem 9.2: " + checksumFinder.getTrueChecksum());

        System.out.println();
        System.out.println("Problem 10.1: " + hikingGuide.getTrailheadScoreSum());
        System.out.println("Problem 10.2: " + hikingGuide.getTrailheadRatingSum());
        
        System.out.println();
        System.out.println("Problem 11.1: " + stoneCounter.getCountAfter25());
        System.out.println("Problem 11.2: " + stoneCounter.getCountAfter75());

        System.out.println();
        System.out.println("Problem 12.1: " + gardenExpense.getPerimPrice());
        System.out.println("Problem 12.2: " + gardenExpense.getSidesPrice());

        System.out.println();
        System.out.println("Problem 13.1: " + clawGame.getTotalMinCost());
        System.out.println("Problem 13.2: " + clawGame.getTotalTrueMinCost());

        System.out.println();
        System.out.println("Problem 14.1: " + robotTracker.getTotalSafetyFactor());
        System.out.println("Problem 14.2: " + robotTracker.getTreeTimeStamp());

        System.out.println();
        System.out.println("Problem 15.1: " + warehouse.getGpsCoordSum());
        System.out.println("Problem 15.2: " + warehouse.getWideGpsCoordSum());

        System.out.println();
        System.out.println("Problem 16.1: " + reindeerMaze.getMinScore());
        System.out.println("Problem 16.2: " + reindeerMaze.getSeatCount());

        System.out.println();
        System.out.println("Problem 17.1: " + opcodeComputer.getOutputString());
        System.out.println("Problem 17.2: ");
    }
}