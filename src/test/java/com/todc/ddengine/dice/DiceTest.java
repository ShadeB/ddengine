package com.todc.ddengine.dice;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class DiceTest {

    @Test
    public void testNoArgConstructor() {
        Dice dice = new Dice();
        assertEquals(0, dice.getQuantity());
        assertEquals(0, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testQtyAndSidesConstructor() {
        Dice dice = new Dice(2, 6);
        assertEquals(2, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testQtyAndSidesAndModifierConstructor() {
        Dice dice = new Dice(2, 6, 1);
        assertEquals(2, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(1, dice.getModifier());
    }

    @Test
    public void testCreate() {
        Dice dice = Dice.create();
        assertEquals(0, dice.getQuantity());
        assertEquals(0, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testQuantity() {
        Dice dice = Dice.create().quantity(2);
        assertEquals(2, dice.getQuantity());
        assertEquals(0, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testSides() {
        Dice dice = Dice.create().sides(6);
        assertEquals(0, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testModifier() {
        Dice dice = Dice.create().modifier(1);
        assertEquals(0, dice.getQuantity());
        assertEquals(0, dice.getSides());
        assertEquals(1, dice.getModifier());
    }

    @Test
    public void testFluentAPI() {
        Dice dice = Dice.create().quantity(2).sides(6).modifier(1);
        assertEquals(2, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(1, dice.getModifier());
    }

    @Test
    public void testEmptyToString() {
        Dice dice = new Dice();
        assertEquals("0d0", dice.toString());
    }

    @Test
    public void testToStringNoModifier() {
        Dice dice = Dice.create().quantity(1).sides(6);
        assertEquals("1d6", dice.toString());
    }

    @Test
    public void testToStringWithPositiveModifier() {
        Dice dice = Dice.create().quantity(1).sides(6).modifier(2);
        assertEquals("1d6+2", dice.toString());
    }

    @Test
    public void testToStringWithNegativeModifier() {
        Dice dice = Dice.create().quantity(1).sides(6).modifier(-2);
        assertEquals("1d6-2", dice.toString());
    }

    @Test
    public void testNoOpRoll() {
        // seed the RNG with numbers that are 1 less than the dice roll we expect.
        // nextInt(i) will return a number between 0..i-1, so we have to add 1 to the result.
        Random rng = new TestRandomNumberGenerator(new int[] { 2 });

        Dice dice = Dice.create().quantity(1).sides(6);
        dice.setRandomNumberGenerator(rng);

        RollResults rollResults = dice.roll();

        assertNotNull(rollResults);
        assertArrayEquals(new int[] { 3 }, rollResults.rolls());
    }

    @Test
    public void testDiceNotationRoll() {
        RollResults rollResults = Dice.roll("2d6");

        assertNotNull(rollResults);
        assertNotNull(rollResults.rolls());
        assertTrue(rollResults.rolls().length == 2);
    }

}
