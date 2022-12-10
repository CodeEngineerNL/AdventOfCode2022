package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09 implements AocSolver {

    public Object part1() throws IOException {
        var lines = getInput();

        Point head = new Point(0,0);
        Point tail = new Point(0, 0);

        Set<String> positions = new HashSet<>();
        positions.add(tail.toString());

        for (String line : lines) {
            char move = line.charAt(0);
            int num = Integer.parseInt(line.substring(2));

            switch (move) {
                case 'R' -> head.x = head.x + num;
                case 'U' -> head.y = head.y + num;
                case 'L' -> head.x = head.x - num;
                case 'D' -> head.y = head.y - num;
                default -> throw new IllegalArgumentException("Invalid move");
            }

            if (!isTouching(head, tail)) {

                if (Math.abs(head.x - tail.x) == 1) {
                    tail.x = head.x;
                } else if (Math.abs(head.y - tail.y) == 1) {
                    tail.y = head.y;
                }

                if (tail.x - head.x == 0) {
                    int start = tail.y;
                    int dir = head.y < tail.y ? - 1 : 1;
                    for (int i = start + dir; i != head.y; i += dir) {
                        tail.y = i;
                        positions.add(tail.toString());
                    }
                } else {
                    int start = tail.x;
                    int dir = head.x > tail.x ? 1 : -1;

                    for (int i = start + dir; i != head.x; i += dir) {
                        tail.x = i;
                        positions.add(tail.toString());
                    }
                }
            }
        }

        return positions.size();
    }

    public boolean isTouching(Point head, Point tail) {
        return Math.abs(head.x - tail.x) <= 1 && Math.abs(head.y - tail.y) <= 1;
    }

    public Object part2() throws IOException {
        var lines = getInput();

        Point[] rope = new Point[10];
        for (int i = 0; i < rope.length; i++) {
             rope[i] = new Point(0, 0);
        }


        Set<String> positions = new HashSet<>();
        positions.add(rope[9].toString());

        for (String line : lines) {
            char move = line.charAt(0);
            int num = Integer.parseInt(line.substring(2));

            for (int i = 0; i < num; i++) {
                doMove(move, rope);
                positions.add(rope[9].toString());
            }

        }

        return positions.size();
    }

    private void doMove(char move, Point[] rope) {
        switch (move) {
            case 'R' -> rope[0].x = rope[0].x + 1;
            case 'U' -> rope[0].y = rope[0].y + 1;
            case 'L' -> rope[0].x = rope[0].x - 1;
            case 'D' -> rope[0].y = rope[0].y - 1;
            default -> throw new IllegalArgumentException("Invalid move");
        }

        for (int r = 1; r < rope.length; r++) {
            Point head = rope[r - 1];
            Point tail = rope[r];

            if (!isTouching(head, tail)) {
                long diffx = head.x - tail.x;
                if (diffx != 0) {
                    tail.x += diffx > 0 ? 1 : -1;
                }

                long diffy = head.y - tail.y;
                if (diffy != 0) {
                    tail.y += diffy > 0 ? 1 : -1;
                }
            }
        }
    }


    private void printPositions(Point[] rope) {
        System.out.println();
        System.out.println("====================");
        System.out.println();

        Point min = new Point(0, 0);
        Point max = new Point(0,0);

        for (Point p: rope) {
            if (p.x > max.x) {
                max.x = p.x;
            }
            if (p.y > max.y) {
                max.y = p.y;
            }

            if (p.x < min.x) {
                min.x = p.x;
            }
            if (p.y < min.y) {
                min.y = p.y;
            }
        }

        int tx = 0;
        int ty = 0;

        if (min.x < 0) {
            tx = -min.x;
        }
        if (min.y < 0) {
            ty = -min.y;
        }

        int width = max.x + tx + 1;
        int height = max.y + ty + 1;
        int[][] grid = new int[height][width];

        for (int i = rope.length - 1; i >=0; i--) {
            grid[rope[i].y + ty][rope[i].x + tx] = i + 1;
        }

        for (int y = grid.length - 1; y >= 0; y--) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(grid[y][x] - 1);
                }

            }
            System.out.println();
        }


    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day9.txt"));
    }

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

}
