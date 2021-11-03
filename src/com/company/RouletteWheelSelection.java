package com.company;

import java.util.Random;

public class RouletteWheelSelection extends Selection{

    public RouletteWheelSelection (int amountOfSpins) {
        this.amountOfSpins = amountOfSpins;
    }

    public char[][] select (char[][] population,
                            int[] fitnesses) {

        int minFitness = fitnesses[0];
        for(int i = 0; i < fitnesses.length; i++) {
                if(minFitness > fitnesses[i]) {
                    minFitness = fitnesses[i];
                }
        } // find the minimum value of the fitness

        int[] positiveFitnesses = new int[fitnesses.length];
        for(int i = 0; i < positiveFitnesses.length; i++) {
            positiveFitnesses[i] = (minFitness * -1) + fitnesses[i];
        }

        int sumOfFitnesses = 0;
        for(int i = 0; i < fitnesses.length; i++) {
            sumOfFitnesses = sumOfFitnesses + positiveFitnesses[i];
        }

        double[] probabilityToBeSelected = new double[fitnesses.length];

        double sumOfProb = 0;
        for(int i = 0; i < positiveFitnesses.length; i++) {
            sumOfProb = ((double) (positiveFitnesses[i])/sumOfFitnesses) + sumOfProb;
            probabilityToBeSelected[i] = sumOfProb;
        }

        char[][] selected = new char[amountOfSpins][];
        for(int i = 0; i < amountOfSpins; i++) {
            Random rnd = new Random();
            double p = rnd.nextDouble();
            int x;
            for(x = 0; x < probabilityToBeSelected.length && probabilityToBeSelected[x] <= p; x++);
            selected[i] = population[x];
        }

        return selected;
    }

    private int amountOfSpins;

}

