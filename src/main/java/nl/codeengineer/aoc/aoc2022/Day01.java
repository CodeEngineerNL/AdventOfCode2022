package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day01 implements AocSolver {

    public Object part1() throws IOException {
        var lines = getInput();

        int total = 0;
        int max = 0;
        for (String line: lines) {
            if (line.isBlank()) {
                if (total > max) {
                    max = total;
                }
                total = 0;
            } else {
                total += Integer.parseInt(line);
            }

        }

        return max;
    }

    public Object part2() throws IOException {
        var lines = getInput();

        List<Integer> result = new ArrayList<>();
        int total = 0;

        for (String line: lines) {
            if (line.isBlank()) {
                result.add(total);
                total = 0;
            } else {
                total += Integer.parseInt(line);
            }
        }
        if (total > 0) {
            result.add(total);
        }

        result.sort(Integer::compare);
        if (result.size() > 2) {
            int size = result.size();
            return result.get(size - 1) + result.get(size - 2) + result.get(size - 3);
        }

        return 0;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day1.txt"));
    }
}
