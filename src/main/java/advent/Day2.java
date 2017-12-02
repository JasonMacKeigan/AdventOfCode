package advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-02 at 1:03 AM
 */
public class Day2 {

    public static void main(String... args) {
        partOne();

        partTwo();
    }

    static void partOne() {
        try {
            List<String> rows = Files.readAllLines(Paths.get("day_two_input.txt"));

            int sum = 0;

            for (String row : rows) {
                String[] values = row.split("\t");

                int max = Stream.of(values).map(Integer::parseInt).max(Integer::compare).orElse(0);

                int min = Stream.of(values).map(Integer::parseInt).min(Integer::compare).orElse(0);

                sum += max - min;
            }

            System.out.println("The sum is: " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void partTwo() {
        try {
            List<String> rows = Files.readAllLines(Paths.get("day_two_input.txt"));

            int sum = 0;

            for (String row : rows) {
                String[] values = row.split("\t");

                List<Integer> valuesAsIntegers = Stream.of(values).map(Integer::parseInt).collect(Collectors.toList());

                outside:
                for (int valueAsInteger : valuesAsIntegers) {
                    for (int otherValueAsInteger : valuesAsIntegers) {
                        if (valueAsInteger == otherValueAsInteger) {
                            continue;
                        }
                        double result = (double) valueAsInteger / (double) otherValueAsInteger;

                        if (result % 1 == 0 ) {
                            sum += result;

                            continue outside;
                        }
                    }
                }
            }

            System.out.println("The sum is: " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
