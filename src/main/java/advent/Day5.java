package advent;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Jason MacKeigan on 2017-12-06 at 3:01 PM
 */
public class Day5 {

    public static void main(String... args) {
        try {
            List<Integer> lines = DayUtils.readLines("day_five_input.txt").stream().map(line -> Integer.parseInt(line.trim())).collect(Collectors.toList());

            System.out.println("Part 1: " + partOne(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int partOne(List<Integer> values) {
        int position = 0;

        int jumps = 0;

        while (position > -1 && position < values.size()) {
            int valueAt = values.get(position);

            values.set(position, valueAt + 1);

            position += valueAt;

            jumps++;
        }
        return jumps;
    }

    private static int partTwo(List<Integer> values) {
        int position = 0;

        int jumps = 0;

        while (position > -1 && position < values.size()) {
            int valueAt = values.get(position);

            values.set(position, valueAt >= 3 ? valueAt - 1 : valueAt + 1);

            position += valueAt;

            jumps++;
        }
        return jumps;
    }
}
