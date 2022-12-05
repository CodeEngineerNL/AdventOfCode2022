package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day05 implements AocSolver {

    public Object part1() throws IOException {
        BufferedReader br = Files.newBufferedReader(Path.of("inputs", "day5.txt"));
        List<Deque<Character>> stacks = parseInput(br);
        Scanner scanner = new Scanner(br);

        while (scanner.hasNext()) {
            scanner.next("move");
            int num = scanner.nextInt() ;
            scanner.next("from");
            int from = scanner.nextInt() - 1;
            scanner.next("to") ;
            int to = scanner.nextInt() - 1;


            for (int i = 0; i < num; i++) {
                stacks.get(to).push(stacks.get(from).pop());
            }
        }

        StringBuilder res = new StringBuilder();
        stacks.forEach(s -> res.append(s.peek()));

        return res;
    }

    public Object part2() throws IOException {
        BufferedReader br = Files.newBufferedReader(Path.of("inputs", "day5.txt"));
        List<Deque<Character>> stacks = parseInput(br);
        Scanner scanner = new Scanner(br);

        while (scanner.hasNext()) {
            scanner.next("move");
            int num = scanner.nextInt() ;
            scanner.next("from");
            int from = scanner.nextInt() - 1;
            scanner.next("to") ;
            int to = scanner.nextInt() - 1;

            Deque<Character> temp = new ArrayDeque<>();
            for (int i = 0; i < num; i++) {
                temp.push(stacks.get(from).pop());
            }
            for (int i = 0; i < num; i++) {
                stacks.get(to).push(temp.pop());
            }
        }

        StringBuilder res = new StringBuilder();
        stacks.forEach(s -> res.append(s.peek()));

        return res;
    }

    public List<Deque<Character>> parseInput(BufferedReader br) throws IOException {
        boolean found = false;

        List<String> stackInitializer = new ArrayList<>();
        String line = "";
        while (!found) {
            line = br.readLine();
            if (line.startsWith(" 1")) {
                found = true;
            } else {
                stackInitializer.add(line);
            }
        }

        String[] parts = line.trim().split("\\s+");

        List<Deque<Character>> stacks = new ArrayList<>();
        for (int k = 0; k < parts.length; k++) {
            stacks.add(new ArrayDeque<>());
        }

        for (String stackLine: stackInitializer) {
            parseStackLine(stackLine, stacks);
        }

        return stacks;
    }

    private void parseStackLine(String line, List<Deque<Character>> stacks) {
        for (int i = 1; i < line.length(); i += 4) {
            if (line.charAt(i) != ' ') {
                stacks.get((i - 1) / 4).addLast(line.charAt(i));
            }
        }
    }
}
