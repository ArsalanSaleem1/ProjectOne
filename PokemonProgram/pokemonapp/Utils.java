package pokemonapp;

import java.util.Random;

public class Utils {
    private static Random random = new Random();

    /**
     * Generates a random number in the given range.
     *
     * @param min lower bound.
     * @param max upper bound.
     * @return the random number.
     */
    public static int generateNumberInRange(int min, int max) {
        int number = random.nextInt(max - min + 1); // [0, max - min]
        number += min;  // [min, max]
        return number;
    }
}
