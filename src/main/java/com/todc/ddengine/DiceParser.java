package com.todc.ddengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class DiceParser {

    private static Pattern pattern = Pattern.compile("(\\d+)(d)(\\d+)(([\\+\\-\\*\\/])(\\d+))*");

    public static Dice parse(String s) {
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            String quantity = matcher.group(1);
            String sides = matcher.group(3);
            String modifier = matcher.group(4);

            System.out.println("qty = " + quantity);
            System.out.println("sides = " + sides);
            System.out.println("modifier = " + modifier);
        }

        return null;
    }


    public static void main(String... args) throws Exception {
        DiceParser parser = new DiceParser();
        System.out.println(parser.parse("4d6"));
        System.out.println(parser.parse("1d10-1"));
        System.out.println(parser.parse("2d4+4"));
    }


}
