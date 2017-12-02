package advent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jason MacKeigan on 2017-12-01 at 8:09 PM
 *
 * A simple junit test to asset if the values returned based on the input returns
 * the expected result.
 */
public class Day1Test {

    @Test
    public void assertAll() {
        assertPartOne();

        assertPartTwo();
    }

    @Before
    public void assertPartOne() {
        assert Day1.partOne("1111") == 4;
    }

    @After
    public void assertPartTwo() {
        assert Day1.partTwo("1212") == 6;
    }
}
