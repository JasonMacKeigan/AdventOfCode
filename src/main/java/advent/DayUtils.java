package advent;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Jason MacKeigan on 2017-12-03 at 12:41 AM
 */
public class DayUtils {

    private DayUtils() {

    }

    public static List<String> readLines(String input) throws IOException {
        return Resources.readLines(Resources.getResource(input), Charset.defaultCharset());
    }

    public static String readLine(String input) throws IOException {
        List<String> lines = readLines(input);

        if (lines.isEmpty()) {
            throw new IllegalStateException("No lines in file.");
        }
        return lines.get(0);
    }
}
