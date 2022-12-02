package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day02 implements AocSolver {

    public long part1() throws IOException {
        var lines = getInput();

        long score = 0;
        for (String line: lines) {
            String[] parts = line.split(" ");

            score += parts[1].charAt(0) - 'X' + 1;

            score += switch (parts[0]) {
                case "A" -> switch(parts[1]) {
                    case "X" -> 3;
                    case "Y" -> 6;
                    default -> 0;
                };
                case "B" -> switch (parts[1]) {
                    case "Y" -> 3;
                    case "Z" -> 6;
                    default -> 0;
                };
                case "C" -> switch(parts[1]) {
                    case "Z" -> 3;
                    case "X" -> 6;
                    default -> 0;
                };
                default -> 0;
            };

        }

        return score;
    }

    public long part2() throws IOException {
        var lines = getInput();

        long score = 0;
        for (String line: lines) {
            String[] parts = line.split(" ");

            int what = switch (parts[1]) {
                case "X" -> -1;
                case "Z" -> 1;
                default -> 0;
            };

            int opponent = parts[0].charAt(0) - 'A';
            int move = Math.floorMod(opponent + what, 3);

            score += move + 1;

            score += switch (parts[1]) {
                case "X" -> 0;
                case "Y" -> 3;
                default -> 6;
            };
        }

        return score;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day2.txt"));
    }
}
