package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day07 implements AocSolver {

    public Object part1() throws IOException {
       var node = parseTree(getInput());
       sumTree(node);
       List<Entry> nodesToSum = new ArrayList<>();
       findSpecificNodes(100000, node, nodesToSum);
       return nodesToSum.stream().mapToLong(e -> e.size).sum();
    }

    public Object part2() throws IOException {
        var node = parseTree(getInput());
        sumTree(node);
        long reclaimSize = 30000000 - (70000000 - node.size);
        return findSmallestNode(reclaimSize, node, Long.MAX_VALUE);
    }

    public void findSpecificNodes(long maxSize, Entry node, List<Entry> result) {
        if (node.isDir && node.size <= maxSize) {
            result.add(node);
        }

        if (node.isDir) {
            for (Entry child: node.children) {
                findSpecificNodes(maxSize, child, result);
            }
        }
    }

    public long findSmallestNode(long minSize, Entry node, long currentMin) {
        if (node.isDir) {
            if (node.size >=minSize && node.size < currentMin) {
                currentMin = node.size;
            }
            for (Entry child: node.children) {
                currentMin = findSmallestNode(minSize, child, currentMin);
            }
        }

        return currentMin;
    }

    private void printTree(Entry entry, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }

        System.out.printf("- %s (%d)%n", entry.name, entry.size);
        if (entry.isDir) {
            for (Entry child : entry.children) {
                printTree(child, indent + 2);
            }
        }
    }

    public long sumTree(Entry node) {
        if (!node.isDir) {
            return node.size;
        }

        int size = 0;
        for (Entry child: node.children) {
            size += sumTree(child);
        }
        node.size = size;

        return size;
    }

    public Entry parseTree(List<String> lines) {
        Deque<Entry> stack = new ArrayDeque<>();
        int k = 0;

        while (k < lines.size()) {
            String line = lines.get(k);

            if (line.startsWith("$ cd ")){
                String dirName = line.substring(5);

                if (dirName.equals("..")) {
                    if (stack.size() > 1) {
                        stack.pop();
                    }
                } else {
                    Entry entry = new Entry(dirName, true, 0, new ArrayList<>());

                    if (!stack.isEmpty()) {
                        Entry parent = stack.peek();
                        parent.children.add(entry);
                    }
                    stack.push(entry);
                }
                k++;
            } else if (line.startsWith("$ ls")) {
                Entry entry = stack.peek();
                k++;
                while (k < lines.size() && !lines.get(k).startsWith("$")) {
                    if (!lines.get(k).startsWith("dir ")) {
                        String[] parts = lines.get(k).split(" ", 2);
                        Entry file = new Entry(parts[1], false, Long.parseLong(parts[0]), null);
                        entry.children.add(file);
                    }
                    k++;
                }
            }
        }

        return stack.getLast();
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day7.txt"));
    }


    public static class Entry {
        public String name;
        public boolean isDir;
        public long size;
        public List<Entry> children;

        public Entry(String name, boolean isDir, long size, List<Entry> children) {
            this.name = name;
            this.isDir = isDir;
            this.size = size;
            this.children = children;
        }
    }

}
