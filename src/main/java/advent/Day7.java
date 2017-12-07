package advent;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-07 at 12:59 AM
 */
public class Day7 {

    public static void main(String... args) {
        try {
            List<String> lines = DayUtils.readLines("day_seven_input.txt");

            List<Program> programs = new ArrayList<>();

            for (String line : lines) {
                String[] split = line.split("->");

                String name = split[0].trim().split(" ")[0];

                int weight = Integer.parseInt(split[0].trim().split(" ")[1].replaceAll("[()]", ""));

                String[] subnames = split.length == 1 ? null : split[1].trim().split(", ");

                programs.add(new Program(name, weight, subnames));
            }
            Program max = partOne(programs);

            System.out.println("Part one: " + max.name);
            System.out.println("Part two: " + programs.stream().map(Day7::partTwo).min(Integer::compareTo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Program partOne(List<Program> programs) {
        for (Program program : programs) {
            if (program.subnames == null) {
                continue;
            }
            for (String subname : program.subnames) {
                Program subprogramByName = programs.stream().filter(p -> p.name.equals(subname)).findAny().orElse(null);

                if (subprogramByName == null) {
                    continue;
                }
                subprogramByName.parent = program;
                program.subprograms.add(subprogramByName);
            }
        }
        Program max = programs.stream().filter(p -> p.subnames != null && p.subprograms.size() > 0 && programs.stream().noneMatch(o -> o.subprograms.contains(p))).findAny().orElse(null);

        if (max == null) {
            System.out.println("Something went wrong, cannot find top program.");
            return null;
        }
        return max;
    }

    private static int partTwo(Program topProgram) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();

        topProgram.subprograms.forEach(p -> statistics.addValue(p.weight()));

        int median = (int) new Median().evaluate(statistics.getValues());

        Program offset = topProgram.subprograms.stream().filter(s -> s.weight() != median).findAny().orElse(null);

        if (offset == null) {
            return Integer.MAX_VALUE;
        }
        return offset.weight - (offset.weight() - median);
    }

    private static class Program {

        private final String name;

        private final int weight;

        private final String[] subnames;

        private List<Program> subprograms = new ArrayList<>();

        private Program parent;

        private Program(String name, int weight, String[] subnames) {
            this.name = name;
            this.weight = weight;
            this.subnames = subnames;
        }

        public int weight() {
            int weight = this.weight;

            for (Program program : subprograms) {
                weight += program.weight();
            }
            return weight;
        }

    }
}
