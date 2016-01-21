package com.todc.ddengine.dice;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.junit.Assert.*;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class DiceNotationParserTest {

    @Test
    public void testSimpleParse() {
        Dice dice = DiceNotationParser.parse("1d6");

        assertEquals(1, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(0, dice.getModifier());
    }

    @Test
    public void testParseWithPositiveModifier() {
        Dice dice = DiceNotationParser.parse("2d6+1");

        assertEquals(2, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(1, dice.getModifier());
    }

    @Test
    public void testParseWithNegativeModifier() {
        Dice dice = DiceNotationParser.parse("2d6-1");

        assertEquals(2, dice.getQuantity());
        assertEquals(6, dice.getSides());
        assertEquals(-1, dice.getModifier());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParse() {
        DiceNotationParser.parse("invalid dice notation string");
    }

}
