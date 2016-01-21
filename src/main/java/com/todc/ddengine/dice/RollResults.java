package com.todc.ddengine.dice;


import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
class RollResults {


    // ----------------------------------------------------- Instance Variables


    private Dice dice;
    private int[] rolls;


    // ----------------------------------------------------------- Constructors


    public RollResults(Dice dice, int[] rolls) {
        this.dice = dice;
        this.rolls = rolls;
    }


    // --------------------------------------------------------- Public Methods


    public int sum() {
        return IntStream.of(rolls).sum() + dice.getModifier();
    }

    public int min() {
        return IntStream.of(rolls).min().getAsInt();
    }

    public int max() {
        return IntStream.of(rolls).max().getAsInt();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dice = " + dice);

        sb.append(", rolls = ");

        // format rolls as: [R1, R2, R3, Rn]
        String formattedResults = IntStream.of(rolls)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));

        sb.append(formattedResults);
        sb.append(", modifier = " + dice.getModifier());
        sb.append(", sum = " + sum());
        sb.append(", min = " + min());
        sb.append(", max = " + max());

        return sb.toString();
    }

}
