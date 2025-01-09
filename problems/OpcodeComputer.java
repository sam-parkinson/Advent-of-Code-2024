package problems;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OpcodeComputer {
    private long registerA, registerB, registerC;
    private int[] program;
    private ArrayList<Integer> output;
    private String outputString;

    public OpcodeComputer(String address) {
        this.registerA = 0;
        this.registerB = 0;
        this.registerC = 0;
        this.output = new ArrayList<>();
        parseInput(address);
        runProgram();
        this.outputString = concatenateOutput();
    }

    public String getOutputString() {
        return this.outputString;
    }

    private void parseInput(String address) {
        String[] strArr = new String[0];
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            String a = stdin.nextLine();

            this.registerA = Integer.parseInt(a.split(" ")[2]);

            stdin.nextLine();
            stdin.nextLine();
            stdin.nextLine();

            strArr = stdin.nextLine().split(" ")[1].split(",");

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        this.program = new int[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            this.program[i] = Integer.parseInt(strArr[i]);
        }
    }

    private void runProgram() {
        for (int i = 0; i < program.length; i += 2) {
            int opcode = program[i];
            int operand = program[i + 1];

            switch (opcode) {
                case 0:
                    adv(operand);
                    break;
                case 1:
                    bxl(operand);
                    break;
                case 2:
                    bst(operand);
                    break;
                case 3:
                    i = jnz(operand, i);
                    break;
                case 4:
                    bxc(operand);
                    break;
                case 5:
                    out(operand);
                    break;
                case 6:
                    bdv(operand);
                    break;
                case 7:
                default:
                    cdv(operand);
                    break;
            }
        } 
    }

    private void adv(int operand) {
        long num = this.registerA;
        long den = 1;
        for (long i = 0; i < comboOperand(operand); i++) {
            den *= 2;
        }

        this.registerA = num / den;
    }

    private void bxl(int operand) {
        this.registerB = this.registerB ^ operand;
    }

    private void bst(int operand) {
        this.registerB = comboOperand(operand) % 8;
    }

    private int jnz(int operand, int index) {
        if (registerA == 0) {
            return index;
        } else {
            return operand - 2;
        }
    }

    private void bxc(int operand) {
        this.registerB = this.registerB ^ this.registerC;
    }

    private void out(int operand) {
        this.output.add((int) comboOperand(operand) % 8);
    }

    private void bdv(int operand) {
        long num = this.registerA;
        long den = 1;
        for (long i = 0; i < comboOperand(operand); i++) {
            den *= 2;
        }

        this.registerB = num / den;
    }

    private void cdv(int operand) {
        long num = this.registerA;
        long den = 1;
        for (long i = 0; i < comboOperand(operand); i++) {
            den *= 2;
        }

        this.registerC = num / den;
    }

    private long comboOperand(int operand) {
        if (operand <= 3) {
            return operand;
        }

        if (operand == 4) {
            return this.registerA;
        }

        if (operand == 5) {
            return this.registerB;
        }

        if (operand == 6) {
            return this.registerC;
        }

        return -1;
    }

    private String concatenateOutput() {
        StringBuilder sb = new StringBuilder();

        sb.append(output.get(0));
        for (int i = 1; i < output.size(); i++) {
            sb.append(',');
            sb.append(output.get(i));
        }

        return sb.toString();
    }
}