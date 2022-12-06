package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day06 implements AocSolver {

    public Object part1() throws IOException {
        return detect(4, getInput());
    }

    public Object part2() throws IOException {
        return detect(14, getInput());
    }

    public int detect(int length, String line) {
        Deque<Character> deck = new LinkedList<>();

        for (int k = 0; k < line.length(); k++) {
            if (deck.size() == length) {
                deck.removeLast();
            }
            deck.push(line.charAt(k));
            Set<Character> charSet = new HashSet<>(deck);
            if (charSet.size() == length) {
                return k + 1;
            }
        }

        return 0;
    }



    public String getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day6.txt")).get(0);
    }
}
