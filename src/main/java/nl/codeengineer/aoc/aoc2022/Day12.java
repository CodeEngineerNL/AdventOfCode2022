package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12 implements AocSolver {
    private Point start;
    private Point end;

    private boolean[][] visited;


    public Object part1() throws IOException {
        return solveSteps1(getInput());
    }

    public Object part2() throws IOException {
        return solveSteps2(getInput());
    }

    public long solveSteps1(char[][] map) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.y][start.x] = true;

        while (!queue.isEmpty()) {
            Point item = queue.remove();

            if (item.x == end.x && item.y == end.y) {
                return getDepth(item);
            }

            addPoint(item.x - 1, item.y, item, queue, map, false);
            addPoint(item.x + 1, item.y, item, queue, map, false);
            addPoint(item.x, item.y + 1, item, queue, map, false);
            addPoint(item.x, item.y - 1, item, queue, map, false);
        }

        return 0;
    }

    public long solveSteps2(char[][] map) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(end);
        visited[end.y][end.x] = true;

        while (!queue.isEmpty()) {
            Point item = queue.remove();

            if (map[item.y][item.x] == 'a') {
                return getDepth(item);
            }

            addPoint(item.x - 1, item.y, item, queue, map, true);
            addPoint(item.x + 1, item.y, item, queue, map, true);
            addPoint(item.x, item.y + 1, item, queue, map, true);
            addPoint(item.x, item.y - 1, item, queue, map, true);
        }

        return 0;
    }

    private int getDepth(Point item) {
        int depth = 0;
        while (item.prev != null) {
            depth++;
            item = item.prev;
        }
        return depth;
    }

    public void addPoint(int x, int y, Point prevPoint, Queue<Point> queue, char[][] map, boolean downOnly) {
        if  (!isValid(x, y, map)
                || visited[y][x]
                || (downOnly && map[prevPoint.y][prevPoint.x] -  map[y][x] > 1)
                || (!downOnly && map[y][x] - map[prevPoint.y][prevPoint.x] > 1))  {
            return;
        }

        queue.add(new Point(x, y, prevPoint));
        visited[y][x] = true;
    }

    public boolean isValid(int x, int y, char[][] map) {
        return (x >= 0 && y >= 0 && x < map[0].length && y < map.length );
    }

    public char[][] getInput() throws IOException {
        List<String> input = Files.readAllLines(Path.of("inputs", "day12.txt"));

        char[][] result = new char[input.size()][input.get(0).length()];
        this.visited = new boolean[result.length][result[0].length];

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                result[y][x] = c;

                if (line.charAt(x) == 'S') {
                    start = new Point(x, y, null);
                    result[y][x] = 'a';
                } else if (line.charAt(x) == 'E') {
                    end = new Point(x, y, null);
                    result[y][x] = 'z';
                }
            }
        }

        return result;
    }

    public record Point(int x, int y, Point prev) {}
}
