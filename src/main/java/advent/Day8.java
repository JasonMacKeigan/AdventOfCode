package advent;

import org.apache.commons.math3.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-10 at 2:04 AM
 */
public class Day8 {

    public static void main(String... args) {
        //ev dec -705 if cag != 2

        try {
            System.out.println(String.format("Part one=%s", partOne(DayUtils.readLines("day_eight_input.txt"))));
            System.out.println(String.format("Part two=%s", partTwo(DayUtils.readLines("day_eight_input.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Pair<List<Register>, Integer> registersFrom(List<String> lines) {
        List<Register> registers = new ArrayList<>();

        int max = 0;

        for (String line : lines) {
            String[] split = line.split(" ");

            String name = split[0];

            Instruction instruction = split[1].equals("dec") ? Instruction.DECREMENT : Instruction.INCREMENT;

            int offset = Integer.parseInt(split[2]);

            String dependent = split[4];

            Operator operator = Stream.of(Operator.values()).filter(o -> o.symbol.equals(split[5])).findAny().orElse(null);

            if (operator == null) {
                throw new RuntimeException("Cannot find operator; " + split[5]);
            }
            int condition = Integer.parseInt(split[6]);

            Register register = registers.stream().filter(r -> r.name.equals(name)).findAny().orElse(new Register(name));

            if (registers.stream().noneMatch(r -> r.name.equals(name))) {
                registers.add(register);
            }
            Register dependentRegister = registers.stream().filter(r -> r.name.equals(dependent)).findAny().orElse(new Register(dependent));

            if (registers.stream().noneMatch(r -> r.name.equals(dependent))) {
                registers.add(dependentRegister);
            }
            if (operator.test(dependentRegister.value, condition)) {
                register.instruct(instruction, offset);
            }

            if (max < register.value) {
                max = register.value;
            }
        }

        return Pair.create(registers, max);
    }

    public static int partOne(List<String> lines) {
        Register register = registersFrom(lines).getFirst().stream().max(Comparator.comparingInt(r -> r.value)).orElse(null);

        if (register == null) {
            throw new IllegalStateException("Cannot find maximum registersFrom value.");
        }
        return register.value;
    }

    public static int partTwo(List<String> lines) {
        return registersFrom(lines).getValue();
    }

    static class Register {

        private final String name;

        private int value;

        Register(String name) {
            this.name = name;
        }

        void instruct(Instruction instruction, int offset) {
            if (instruction == Instruction.INCREMENT) {
                value += offset;
            } else if (instruction == Instruction.DECREMENT) {
                value -= offset;
            }
        }
    }

    enum Operator {
        LESS_THAN("<") {
            @Override
            boolean test(int first, int second) {
                return first < second;
            }
        },

        GREATER_THAN(">") {
            @Override
            boolean test(int first, int second) {
                return first > second;
            }
        },

        EQUAL_TO("==") {
            @Override
            boolean test(int first, int second) {
                return first == second;
            }
        },

        LESS_THAN_EQUAL_TO("<=") {
            @Override
            boolean test(int first, int second) {
                return first <= second;
            }
        },

        GREATER_THAN_EQUAL_TO(">=") {
            @Override
            boolean test(int first, int second) {
                return first >= second;
            }
        },

        NOT_EQUAL_TO("!=") {
            @Override
            boolean test(int first, int second) {
                return first != second;
            }
        };

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        abstract boolean test(int first, int second);
    }

    enum Instruction {
        INCREMENT,

        DECREMENT
    }
}
