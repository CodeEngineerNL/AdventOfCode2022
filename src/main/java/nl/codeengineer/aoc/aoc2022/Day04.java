package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day04 implements AocSolver {

    public long part1() throws IOException {
        var lines = getInput();
        int count = 0;

        for (String line: lines) {
            String[] parts = line.split("[,-]");

            int start1 = Integer.parseInt(parts[0]);
            int end1 = Integer.parseInt(parts[1]);
            int start2 = Integer.parseInt(parts[2]);
            int end2 = Integer.parseInt(parts[3]);

            if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1)) {
                count++;
            }
        }

        return count;
    }

    public long part2() throws IOException {
        var lines = getInput();
        int count = 0;

        for (String line: lines) {
            String[] parts = line.split("[,-]");

            int start1 = Integer.parseInt(parts[0]);
            int end1 = Integer.parseInt(parts[1]);
            int start2 = Integer.parseInt(parts[2]);
            int end2 = Integer.parseInt(parts[3]);

            if (start1 <= end2 && start2 <= end1 ) {
                count++;
            }
        }

        return count;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day4.txt"));
    }
}
