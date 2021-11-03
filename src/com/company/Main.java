// Copyright (C) 2021 Jarmo Hurri

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.company;

public class Main {
  public static void main (String[] args) {
    final int POPULATION_SIZE = 10000;
    final int NUM_ELITES = 5;
    final Selection selection = new TournamentSelection(1000, 2);// select fittest 1/param of population
    final Crossover crossover = new PMX (); // partially mapped crossover

    // switch two random cities on the average in in 1/param individuals
    final Mutation mutation = new SwitchMutation (20); 

    final TerminationRule terminationRule = new TerminationRuleBestMid ();

    TSPGeneticAlgorithm ga = new TSPGeneticAlgorithm (POPULATION_SIZE,
                                                      NUM_ELITES,
                                                      selection,
                                                      crossover,
                                                      mutation,
                                                      terminationRule);
    ga.run ();
  }
}
