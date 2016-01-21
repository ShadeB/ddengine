package com.todc.ddengine.dice;

import java.util.Random;

/**
 * Random number generator used during testing. Allows us to specify the
 * results to return from {@link #nextInt()}.
 *
 * <p>
 * Seed the RNG with numbers that are 1 less than the dice roll we expect.
 * nextInt(i) will return a number between 0..i-1, so we'll add 1 to the result.
 * </p>
 *
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestRandomNumberGenerator extends Random {


    // ----------------------------------------------------- Instance Variables


    private int[] results = new int[] {};
    private int resultIndex = 0;


    // ----------------------------------------------------------- Constructors


    public TestRandomNumberGenerator(int[] results) {
        this.results = results;
    }


    // --------------------------------------------------------- Public Methods


    @Override
    public int nextInt() {
        System.out.println("TestRandomNumberGenerator > nextInt");

        if (resultIndex+1 > results.length) {
            resultIndex = 0;
        }

        return results[resultIndex++];
    }

    @Override
    public int nextInt(int bounds) {
        return nextInt();
    }
}
