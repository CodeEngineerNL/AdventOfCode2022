package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day08 implements AocSolver {

    public Object part1() throws IOException {
        int[][] heigthMap = getInput();

        int count = 0;
        int height = heigthMap.length;
        int width =  heigthMap[0].length;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x <width - 1; x++) {
                if (isVisible(x, y, heigthMap)) {
                    count++;
                }
            }
        }

        return count + height * 2 +width * 2 - 4;
    }

    public Object part2() throws IOException {
        int[][] heigthMap = getInput();
        long score = 0;

        for (int y = 1; y < heigthMap.length; y++) {
            for (int x = 1; x < heigthMap[0].length; x++) {
                long newScore = getScore(x, y, heigthMap);
                if (newScore > score) {
                    score = newScore;
                }
            }
        }

        return score;
    }

    public boolean isVisible(int x, int y, int[][] heightMap) {
        // up
        int yray = y - 1;
        while (yray >= 0 && heightMap[yray][x] < heightMap[y][x]) {
            yray--;
        }
        if (yray < 0) {
            return true;
        }

        // down
        yray  =y + 1;
        while (yray < heightMap.length && heightMap[yray][x] < heightMap[y][x]) {
            yray++;
        }
        if (yray >= heightMap.length) {
            return true;
        }

        // left
        int xray = x -1;
        while (xray >= 0 && heightMap[y][xray] < heightMap[y][x]) {
            xray--;
        }
        if (xray < 0) {
            return true;
        }

        xray = x + 1;
        while (xray < heightMap[0].length && heightMap[y][xray] < heightMap[y][x]) {
            xray++;
        }

        return (xray >= heightMap[0].length);
    }

    public long getScore(int x, int y, int[][] heightMap) {
        // up
        int yray = y - 1;
        while (yray >= 0 && heightMap[yray][x] < heightMap[y][x]) {
            yray--;
        }
        long score = yray < 0 ? y : y - yray;

        // down
        yray  =y + 1;
        while (yray < heightMap.length && heightMap[yray][x] < heightMap[y][x]) {
            yray++;
        }
        score *= yray >= heightMap.length ? yray - y  - 1: yray - y;

        // left
        int xray = x -1;
        while (xray >= 0 && heightMap[y][xray] < heightMap[y][x]) {
            xray--;
        }

        score *= xray < 0 ? x : x - xray;

        // right
        xray = x + 1;
        while (xray < heightMap[0].length && heightMap[y][xray] < heightMap[y][x]) {
            xray++;
        }

        score *= xray >= heightMap[0].length ? xray- x - 1 : xray -x;

        return score;
    }

    public int[][] getInput() throws IOException {
        var lines =  Files.readAllLines(Path.of("inputs", "day8.txt"));
        int height = lines.size();
        int width = lines.get(0).length();

        int[][] map = new int[height][width];

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x <line.length(); x++) {
                map[y][x] = Integer.parseInt("" + line.charAt(x));
            }
        }

        return map;
    }

}
