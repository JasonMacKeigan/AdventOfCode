package advent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-06 at 4:31 PM
 */
public class Day6 {

    public static void main(String... args) {
        try {
            List<Block> blocks = Stream.of(DayUtils.readLine("day_six_input.txt").split("\t")).map(Integer::parseInt).map(Block::new).collect(Collectors.toList());

            System.out.println("Part one: " + partOne(blocks));
            System.out.println("Part two: " + partTwo(blocks));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int partOne(List<Block> blocks) {
        return routines(blocks, 1);
    }

    public static int partTwo(List<Block> blocks) {
        return routines(blocks, 2);
    }

    private static int routines(List<Block> blocks, int matches) {
        Bank bank = new Bank(new ArrayList<>(blocks));

        int routines = 0;

        List<Bank> redistributions = new ArrayList<>();

        while (true) {
            redistributions.add(bank.copy());
            bank.redistribute();
            routines++;

            if (redistributions.stream().anyMatch(redistribution -> redistribution.matches(bank))) {
                if (--matches > 0) {
                    redistributions.clear();
                    routines = 0;
                    continue;
                }
                break;
            }
        }
        return routines;
    }

    static class Bank {
        private final List<Block> blocks;

        Bank(List<Block> blocks) {
            this.blocks = blocks;
        }

        void redistribute() {
            Block max = maxOrNull();

            if (max == null) {
                throw new IllegalStateException("Cannot determine block with maximum value for redistribution.");
            }
            int indexOfMax = blocks.indexOf(max);

            if (indexOfMax == -1) {
                throw new IllegalStateException("Cannot determine index of maximum value for redistribution.");
            }
            int indexOfNext = indexOfMax + 1;

            if (indexOfNext > blocks.size() - 1) {
                indexOfNext = 0;
            }
            int value = max.getValue();

            while (value-- > 0) {
                max.decrementAndGet();

                blocks.get(indexOfNext).increment();

                indexOfNext++;

                if (indexOfNext > blocks.size() - 1) {
                    indexOfNext = 0;
                }
            }

        }

        boolean matches(Bank other) {
            if (other.blocks.size() != blocks.size()) {
                return false;
            }
            for (int index = 0; index < blocks.size(); index++) {
                if (blocks.get(index).getValue() != other.blocks.get(index).getValue()) {
                    return false;
                }
            }
            return true;
        }

        Block maxOrNull() {
            int indexOfMax = -1;

            Block max = null;

            for (int index = blocks.size() - 1; index > -1; index--) {
                Block block = blocks.get(index);

                if (indexOfMax == -1 || max == null) {
                    indexOfMax = index;
                    max = block;
                    continue;
                }

                if (block.value.intValue() >= max.value.intValue()) {
                    indexOfMax = index;
                    max = block;
                }
            }

            return max;
        }

        Bank copy() {
            return new Bank(blocks.stream().map(Block::copy).collect(Collectors.toList()));
        }
    }

    static final class Block {
        AtomicInteger value;

        Block(int value) {
            this.value = new AtomicInteger(value);
        }

        Block copy() {
            return new Block(value.intValue());

        }

        void increment() {
            value.incrementAndGet();
        }

        void decrementAndGet() {
            value.decrementAndGet();
        }

        int getValue() {
            return value.intValue();
        }

        @Override
        public String toString() {
            return "Block{" +
                    "value=" + value +
                    '}';
        }
    }
}
