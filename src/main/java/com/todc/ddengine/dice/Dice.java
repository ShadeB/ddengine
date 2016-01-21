package com.todc.ddengine.dice;


import java.util.Random;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Dice {


    // ----------------------------------------------------- Instance Variables


    private Random rng = new Random();

    private int quantity;
    private int sides;
    private int modifier;


    // ----------------------------------------------------------- Constructors


    public Dice() {
        this(0, 0, 0);
    }

    public Dice(int qty, int sides) {
        this(qty, sides, 0);
    }

    public Dice(int qty, int sides, int modifier) {
        this.quantity = qty;
        this.sides = sides;
        this.modifier = modifier;
    }


    // --------------------------------------------------------------- Mutators


    public Random getRandomNumberGenerator() {
        return rng;
    }

    public void setRandomNumberGenerator(Random rng) {
        this.rng = rng;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSides() {
        return sides;
    }

    public int getModifier() {
        return modifier;
    }


    // ---------------------------------------------------------- Class Methods


    /**
     * Factory class method to use with fluent API.
     *
     * @return New Dice object
     */
    public static Dice create() {
        return new Dice();
    }

    public static RollResults roll(String s) throws IllegalArgumentException {
        return DiceNotationParser.parse(s).roll();
    }


    // --------------------------------------------------------- Public Methods


    public Dice quantity(int qty) {
        this.quantity = qty;
        return this;
    }

    public Dice sides(int sides) {
        this.sides = sides;
        return this;
    }

    public Dice modifier(int modifier) {
        this.modifier = modifier;
        return this;
    }

    public RollResults roll() {
        return roll(quantity);
    }

    public RollResults roll(int qty) {
        int[] rolls = new int[qty];

        for (int i=0; i<qty; i++) {
            rolls[i] = rollDie();
        }

        return new RollResults(this, rolls);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(quantity);
        sb.append("d");
        sb.append(sides);

        if (modifier != 0) {
            sb.append(modifier > 0 ? "+" : "-");
            sb.append(Math.abs(modifier));
        }

        return sb.toString();
    }


    // -------------------------------------------------------- Private Methods


    private int rollDie() {
        return rng.nextInt(sides) + 1;
    }


    public static void main(String... args) throws Exception {
        System.out.println(Dice.create().sides(6).quantity(3).roll());
        System.out.println(Dice.create().sides(6).quantity(4).modifier(2).roll());
        System.out.println(Dice.create().sides(6).quantity(4).modifier(-1).roll());
        System.out.println(new Dice(1, 8).roll());
        System.out.println(new Dice(1, 10, 2).roll());
        System.out.println(Dice.roll("2d6+1"));
        System.out.println(Dice.roll("2d6-2"));
    }

}