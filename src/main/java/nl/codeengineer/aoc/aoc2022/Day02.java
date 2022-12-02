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
            int theirMove = line.charAt(0) - 'A';
            int mymove =  line.charAt(2)- 'X';
            score += mymove + 1;

            score += switch (mymove - theirMove) {
                case 0 -> 3;
                case -2,1 -> 6;
                default -> 0;
            };
        }

        return score;
    }

    public long part2() throws IOException {
        var lines = getInput();

        long score = 0;
        for (String line: lines) {
            int what = line.charAt(2) - 'Y'; // -1,0,1
            int opponent = line.charAt(0) - 'A'; // 0, 1, 2

            /*
            Play Theirs - 1 = lose
            Play theirs = tie
            Play Theirs + 1 = win (modulo 3).
             */
            int move = Math.floorMod(opponent + what, 3);
            score += move + 1;
            score += (what + 1) * 3;
        }

        return score;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day2.txt"));
    }
}
