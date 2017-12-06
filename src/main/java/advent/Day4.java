package advent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-06 at 1:29 PM
 */
public class Day4 {

    public static void main(String... args) {
        try {
            List<String> lines = DayUtils.readLines("day_four_input.txt");

            System.out.println(String.format("Part one: %s", partOne(lines)));
            System.out.println(String.format("Part two: %s", partTwo(lines)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int partOne(List<String> lines) {
        return (int) lines.stream().filter(line -> Stream.of(line.split(" ")).
                noneMatch(word -> Stream.of(line.split(" ")).filter(word::equals).count() > 1)).count();
    }

    private static int partTwo(List<String> lines) {
        return (int) lines.stream().filter(line -> Stream.of(line.split(" ")).
                noneMatch(word -> Stream.of(line.split(" ")).filter(other -> word.equals(other) || isAnagram(word, other)).count() > 1)).count();
    }

    private static boolean isAnagram(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        return first.chars().allMatch(firstValue -> second.chars().filter(secondValue -> firstValue == secondValue).count() == first.chars().filter(f -> f == firstValue).count());
    }
}
