package com.todc.ddengine;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class Dice {


    // ----------------------------------------------------- Instance Variables


    private int quantity = 1;
    private int sides = 6;

    private int[] results = new int[] {};


    // ----------------------------------------------------------- Constructors


    public Dice(int sides, int qty) {
        this.sides = sides;
        this.quantity = qty;
    }


    // --------------------------------------------------------------- Mutators


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }


    // ---------------------------------------------------------- Class Methods


    public static Dice sides(int sides) {
        return new Dice(sides, 1);
    }


    // --------------------------------------------------------- Public Methods


    public Dice quantity(int qty) {
        quantity = qty;
        return this;
    }

    public Results roll() {
        return roll(quantity);
    }

    public Results roll(int qty) {
        results = new int[qty];

        for (int i=0; i<qty; i++) {
            results[i] = rollDie();
        }

        return new Results(results);
    }


    // -------------------------------------------------------- Private Methods


    private int rollDie() {
        return (int)(Math.random() * sides) + 1;
    }


    public static void main(String... args) throws Exception {
        Dice dice = Dice.sides(6).quantity(4);

        System.out.println(dice.roll());
        System.out.println(dice.roll());
        System.out.println(dice.roll());
        System.out.println(dice.roll());
        System.out.println(dice.roll());
        System.out.println(dice.roll());
    }

}

class Results {

    private int[] results;

    public Results(int[] results) {
        this.results = results;
    }

    public int sum() {
        return IntStream.of(results).sum();
    }

    public int min() {
        return IntStream.of(results).min().getAsInt();
    }

    public int max() {
        return IntStream.of(results).max().getAsInt();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Roll Results: ");

        String formattedResults = IntStream.of(results)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));

        sb.append(formattedResults);
        sb.append(", sum = " + sum());
        sb.append(", min = " + min());
        sb.append(", max = " + max());

        return sb.toString();
    }

}