package com.todc.ddengine.dice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class DiceNotationParser {


    // -------------------------------------------------------------- Constants


    private static Pattern pattern = Pattern.compile("(\\d+)(d)(\\d+)(([\\+\\-\\*/])(\\d+))*");


    // --------------------------------------------------------- Public Methods


    /**
     * Parses a string formatted in dice notation (e.g. 2d6+1), converting it
     * into a corresponding Dice object.
     *
     * @param s Input string
     * @return Corresponding Dice object
     */
    public static Dice parse(String s)
            throws IllegalArgumentException {
        String filteredString = s.replaceAll("\\s", "");
        Matcher matcher = pattern.matcher(filteredString);

        if (matcher.matches()) {
            int qty = Integer.parseInt(matcher.group(1));
            int sides = Integer.parseInt(matcher.group(3));
            int modifier = Integer.parseInt(matcher.group(4));

            return new Dice(qty, sides, modifier);
        }

        throw new IllegalArgumentException("Invalid dice notation: " + s);
    }


}
