package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day03 implements AocSolver {

    public Object part1() throws IOException {
        var lines = getInput();

        long sum = 0;

        for (String line: lines) {
            String first = line.substring(0, line.length() / 2);
            String second = line.substring(line.length() / 2) + 1;

            for (int i = 0; i < first.length(); i++) {
                char toCheck = first.charAt(i);
                if (second.indexOf(toCheck) != -1) {
                    sum += (toCheck >='a' && toCheck <= 'z') ? toCheck - 'a' + 1 : toCheck - 'A' + 27;
                    break;
                }
            }
        }

        return sum;
    }

    public Object part2() throws IOException {
        var lines = getInput();
        long sum = 0;

        for (int i = 0; i < lines.size(); i+=3) {
            String l1 = lines.get(i);
            String l2 = lines.get(i + 1);
            String l3 = lines.get(i + 2);

            for (int j = 0; j < l1.length(); j++) {
                char toCheck = l1.charAt(j);

                if (l2.indexOf(toCheck) != -1 && l3.indexOf(toCheck) != -1) {
                    sum += (toCheck >='a' && toCheck <= 'z') ? toCheck - 'a' + 1 : toCheck - 'A' + 27;
                    break;
                }
            }
        }

        return sum;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day3.txt"));
    }
}
