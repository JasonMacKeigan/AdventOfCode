package advent;

import org.junit.Test;

import java.io.IOException;

import static advent.DayUtils.*;

/**
 * Created by Jason MacKeigan on 2017-12-10 at 3:29 AM
 */
public class Day8Test {

    @Test
    public void assertPartOne() throws IOException {
        int value = Day8.partOne(readLines("day_eight_input_test.txt"));

        assert value == 1;
    }
}
