package nl.codeengineer.aoc.aoc2022;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day11 implements AocSolver {

    public Object part1() throws IOException {
        var monkeys = getInput();
        return calcMonkeyBusiness(monkeys, 20,true);
    }

    public Object part2() throws IOException {
        var monkeys = getInput();
        return calcMonkeyBusiness(monkeys, 10000 ,false);
    }

    private long calcMonkeyBusiness(Monkey[] monkeys, int numRounds, boolean useRelief) {
        long[] numInspected = new long[monkeys.length];

       long allDiv = 1;
        if (!useRelief) {
           // Calc the number which we can use as module for all monkeys, e.g. multiply the dividers of all monkeys
           for (Monkey m : monkeys) {
                allDiv *= m.testDivider;
            }
       }

        for (int num = 0; num < numRounds; num++) {
            for (int i = 0; i < monkeys.length; i++) {

                while (!monkeys[i].items.isEmpty()) {
                    numInspected[i]++;

                    long wLevel = monkeys[i].items.removeFirst();

                    switch (monkeys[i].operation) {
                        case ADD_NUM -> wLevel += monkeys[i].operand;
                        case ADD_SELF -> wLevel += wLevel;
                        case MUL_NUM -> wLevel *= monkeys[i].operand;
                        case MUL_SELF -> wLevel *= wLevel;
                    }

                    if (useRelief) {
                        wLevel = wLevel / 3;
                    } else {
                        wLevel = wLevel % allDiv;
                    }
                    if (wLevel % monkeys[i].testDivider == 0) {
                        monkeys[monkeys[i].monkeyTrue].items.add(wLevel);
                    } else {
                        monkeys[monkeys[i].monkeyFalse].items.add(wLevel);
                    }

                }
            }
        }

        Arrays.sort(numInspected);

        return numInspected[numInspected.length - 1] * numInspected[numInspected.length - 2];
    }

    public Monkey[] getInput() throws IOException {
        var br =  Files.newBufferedReader(Path.of("inputs", "day11.txt"));

        List<Monkey> monkeys = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null && line.startsWith("Monkey")) {
            String startItems = br.readLine().split(": ")[1];
            String operations = br.readLine().split(": ")[1];
            String test = br.readLine().split(": ")[1];
            String testTrue = br.readLine().split(": ")[1];
            String testFalse = br.readLine().split(": ")[1];

            Deque<Long> items = new LinkedList<>();
            for (String intItem: startItems.split(",")) {
                items.add(Long.parseLong(intItem.trim()));
            }

            String[] opParts = operations.split(" ");
            if (!"new".equals(opParts[0]) && !"old".equals(opParts[2])) {
                throw new UnsupportedOperationException("Unsupported operation");
            }

            Operation op;
            int operand = 0;
            if ("old".equals(opParts[4])) {
                op = "*".equals(opParts[3]) ? Operation.MUL_SELF : Operation.ADD_SELF;
            } else {
                op = "*".equals(opParts[3]) ? Operation.MUL_NUM : Operation.ADD_NUM;
                operand = Integer.parseInt(opParts[4]);
            }

            int testDivider = Integer.parseInt(test.split(" ")[2]);
            int monkeyTrue = Integer.parseInt(testTrue.split(" ")[3]);
            int monkeyFalse = Integer.parseInt(testFalse.split(" ")[3]);

            monkeys.add(new Monkey(items, op, operand, testDivider, monkeyTrue, monkeyFalse));

            br.readLine();
        }

        return monkeys.toArray(Monkey[]::new);
    }


    public enum Operation {
        ADD_NUM,
        MUL_NUM,
        ADD_SELF,
        MUL_SELF

    }
    public static record Monkey(Deque<Long> items, Operation operation, long operand, long testDivider, int monkeyTrue, int monkeyFalse){}
}
