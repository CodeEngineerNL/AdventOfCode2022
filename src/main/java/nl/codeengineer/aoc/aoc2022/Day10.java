package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day10 implements AocSolver {

    public Object part1() throws IOException {
        var lines = getInput();

        long cycles = 0;
        long x = 1;
        long sum = 0;

        for (String line: lines) {
            String[] instruction = line.split(" ", 2);

            switch (instruction[0]) {
                case "noop" -> {
                    cycles++;
                    sum += calcSignalStrength(cycles, x);
                }
                case "addx" -> {
                    for (int i = 0; i < 2; i++) {
                        cycles++;
                        sum+= calcSignalStrength(cycles, x);

                    }
                    x += Integer.parseInt(instruction[1]);

                }
            }

            if (cycles > 220) {
                break;
            }
        }

        return sum;
    }

    public Object part2() throws IOException {
        var lines = getInput();

        int cycles = 0;
        int x = 1;

        int[][] screenBuf = new int[6][40];

        for (String line: lines) {
            String[] instruction = line.split(" ", 2);

            switch (instruction[0]) {
                case "noop" -> {
                    cycles++;
                    drawPixels(cycles, x, screenBuf);
                }
                case "addx" -> {
                    for (int i = 0; i < 2; i++) {
                        cycles++;
                        drawPixels(cycles, x, screenBuf);

                    }
                    x += Integer.parseInt(instruction[1]);
                }
            }

            if (cycles > 240) {
                break;
            }
        }

        printBuf(screenBuf);
        return 0;
    }

    private long calcSignalStrength(long cycle, long x) {
        if ((cycle + 20) % 40 == 0) {
            return cycle * x;
        }

        return 0;
    }

    private void drawPixels(int cycles, int x, int[][] screenBuf) {
        int bufY = (cycles - 1) / 40;
        int bufX = (cycles - 1) % 40;

        if (bufX >= x - 1 && bufX <= x+ 1) {
            screenBuf[bufY][bufX] = 1;
        }
    }

    private void printBuf(int[][] screenBuf) {
        for (int y = 0; y < screenBuf.length; y++) {
            for (int x = 0; x < screenBuf[y].length; x++) {
                System.out.print(screenBuf[y][x] == 0 ? " " : "#");
            }
            System.out.println();
        }
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day10.txt"));
    }

}
