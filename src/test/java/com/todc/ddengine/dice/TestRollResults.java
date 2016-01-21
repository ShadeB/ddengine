package com.todc.ddengine.dice;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestRollResults {


    // -------------------------------------------------------------- Constants


    private int[] ROLLS_2d6 = new int[] { 1, 2 };

    private Dice DICE_2d6 = Dice.create().quantity(2).sides(6);
    private Dice DICE_2d6_plus1 = Dice.create().quantity(2).sides(6).modifier(1);
    private Dice DICE_2d6_minus1 = Dice.create().quantity(2).sides(6).modifier(-1);


    // ------------------------------------------------------------------ Tests


    @Test
    public void testConstructor() {
        RollResults rollResults = new RollResults(DICE_2d6, ROLLS_2d6);

        assertNotNull(rollResults);
        assertTrue(rollResults.rolls().length == 2);
    }

    @Test
    public void testSum() {
        RollResults rollResults = new RollResults(DICE_2d6, ROLLS_2d6);

        assertEquals(3, rollResults.sum());
    }

    @Test
    public void testSumWithPositiveModifier() {
        RollResults rollResults = new RollResults(DICE_2d6_plus1, ROLLS_2d6);

        assertEquals(4, rollResults.sum());
    }

    @Test
    public void testSumWithNegativeModifier() {
        RollResults rollResults = new RollResults(DICE_2d6_minus1, ROLLS_2d6);

        assertEquals(2, rollResults.sum());
    }

    @Test
    public void testMin() {
        RollResults rollResults = new RollResults(DICE_2d6, ROLLS_2d6);

        assertEquals(1, rollResults.min());
    }

    @Test
    public void testMax() {
        RollResults rollResults = new RollResults(DICE_2d6, ROLLS_2d6);

        assertEquals(2, rollResults.max());
    }

    @Test
    public void testToString() {
        RollResults rollResults = new RollResults(DICE_2d6, ROLLS_2d6);

        assertEquals("dice = 2d6, rolls = [1, 2], modifier = 0, sum = 3, min = 1, max = 2", rollResults.toString());
    }

}
