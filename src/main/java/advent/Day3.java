package advent;

import javafx.scene.control.Tooltip;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Jason MacKeigan on 2017-12-03 at 12:58 AM
 */
public class Day3 {

    public static void main(String... args) {
        int[][] grid = spiral(10_000, 10_000, 325_490);

        System.out.println(String.format("Manhatten distance is: %s", manhattenDistance(grid, 1, 325489)));
    }

    private static int manhattenDistance(int[][] grid, int from, int to) {
        Point start = locationOf(grid, from);

        Point end = locationOf(grid, to);

        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }

    private static Point locationOf(int[][] grid, int value) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == value) {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalArgumentException(String.format("Value %s does not exist in grid.", value));
    }

    private static int[][] spiral(int rows, int columns, int value) {
        // a grid that will allow us to find the manhatten distance of two points.
        int[][] grid = new int[rows][columns];

        // the x position in the grid
        int x = rows / 2;

        // the y position in the grid
        int y = columns / 2;

        // the number of movements at the same increments until it's increased by 1
        int movementsUntilIncrease = 1;

        // the number of increments per movement
        int incrementsPerMovement = 1;

        int movementsUntilIncrement = 2;

        Direction direction = Direction.EAST;

        int valueForPosition = 1;

        while (valueForPosition < value) {
            switch (direction) {
                case NORTH:
                    y++;
                    break;
                case EAST:
                    x++;
                    break;
                case SOUTH:
                    y--;
                    break;
                case WEST:
                    x--;
                    break;
            }
            grid[x][y] = valueForPosition++;

            if (--movementsUntilIncrease == 0) {
                movementsUntilIncrease = incrementsPerMovement;
                direction = direction.next();
                movementsUntilIncrement--;
            }

            if (movementsUntilIncrement == 0) {
                movementsUntilIncrement = 2;
                incrementsPerMovement++;
            }
        }

        return grid;
    }

    private enum Direction {
        NORTH(0, 3), EAST(1, 0), SOUTH(2, 1), WEST(3, 2);

        private final int index;

        private final int next;

        Direction(int index, int next) {
            this.index = index;
            this.next = next;
        }

        Direction next() {
            return Stream.of(values()).filter(d -> d.index == next).findAny().orElse(null);
        }
    }
}
