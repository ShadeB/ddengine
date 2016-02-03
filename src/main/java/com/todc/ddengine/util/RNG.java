package com.todc.ddengine.util;

import java.util.List;
import java.util.Random;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class RNG extends Random {

    public Object item(List list) {
        int randomIndx = nextInt(list.size());
        return list.get(randomIndx);
    }

    public Object item(Object[] array) {
        int randomIndx = nextInt(array.length);
        return array[randomIndx];
    }

    /**
     * Returns true if a random int chosen between 1 and [chance] was 1.
     * In other words, simulates the success of a one-in-X chance roll.
     */
    public boolean oneIn(int chance) {
        return nextInt(chance) == 0;
    }

}
