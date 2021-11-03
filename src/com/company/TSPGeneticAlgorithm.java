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

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Arrays;

public class TSPGeneticAlgorithm {
  // if no mutation is desired, parameter mutation can be null
  public TSPGeneticAlgorithm (int populationSize,
                              int numElites,
                              Selection selection,
                              Crossover crossover,
                              Mutation mutation,
                              TerminationRule terminationRule)
  {
    POPULATION_SIZE = populationSize;
    NUM_ELITES = numElites;
    this.selection = selection;
    this.crossover = crossover;
    this.mutation = mutation;
    this.terminationRule = terminationRule;

    // TSP constants
    STARTCITY = 'X';
    VISITED_CITIES = "ABCDEFGHIJKLMNOPQRST".toCharArray ();
    NUM_VISITED_CITIES = VISITED_CITIES.length;

    CITIES = new char [VISITED_CITIES.length + 1];
    CITIES [0] = STARTCITY;
    for (int i = 1; i < CITIES.length; i++)
      CITIES [i] = VISITED_CITIES [i - 1];
    NUM_CITIES = CITIES.length;

    // distances from IB document
    final int[][] DISTS =
      {{0, 94, 76, 141, 91, 60, 120, 145, 91, 74, 90, 55, 145, 108, 41, 49, 33, 151, 69, 111, 24},
       {94, 0, 156, 231, 64, 93, 108, 68, 37, 150, 130, 57, 233, 26, 62, 140, 61, 229, 120, 57, 109},
       {76, 156, 0, 80, 167, 133, 124, 216, 137, 114, 154, 100, 141, 161, 116, 37, 100, 169, 49, 185, 84},
       {141, 231, 80, 0, 229, 185, 201, 286, 216, 139, 192, 178, 113, 239, 182, 92, 171, 155, 128, 251, 137},
       {91, 64, 167, 229, 0, 49, 163, 65, 96, 114, 76, 93, 200, 91, 51, 139, 72, 185, 148, 26, 92},
       {60, 93, 133, 185, 49, 0, 165, 115, 112, 65, 39, 91, 151, 117, 39, 99, 61, 139, 128, 75, 49},
       {120, 108, 124, 201, 163, 165, 0, 173, 71, 194, 203, 74, 254, 90, 127, 136, 104, 269, 75, 163, 144},
       {145, 68, 216, 286, 65, 115, 173, 0, 103, 179, 139, 123, 265, 83, 104, 194, 116, 250, 186, 39, 152},
       {91, 37, 137, 216, 96, 112, 71, 103, 0, 160, 151, 39, 236, 25, 75, 130, 61, 239, 95, 93, 112},
       {74, 150, 114, 139, 114, 65, 194, 179, 160, 0, 54, 127, 86, 171, 89, 77, 99, 80, 134, 140, 50},
       {90, 130, 154, 192, 76, 39, 203, 139, 151, 54, 0, 129, 133, 155, 78, 117, 99, 111, 159, 101, 71},
       {55, 57, 100, 178, 93, 91, 74, 123, 39, 127, 129, 0, 199, 61, 53, 91, 30, 206, 63, 101, 78},
       {145, 233, 141, 113, 200, 151, 254, 265, 236, 86, 133, 199, 0, 251, 171, 118, 176, 46, 182, 226, 125},
       {108, 26, 161, 239, 91, 117, 90, 83, 25, 171, 155, 61, 251, 0, 83, 151, 75, 251, 119, 81, 127},
       {41, 62, 116, 182, 51, 39, 127, 104, 75, 89, 78, 53, 171, 83, 0, 90, 24, 168, 99, 69, 49},
       {49, 140, 37, 92, 139, 99, 136, 194, 130, 77, 117, 91, 118, 151, 90, 0, 80, 139, 65, 159, 50},
       {33, 61, 100, 171, 72, 61, 104, 116, 61, 99, 99, 30, 176, 75, 24, 80, 0, 179, 76, 86, 52},
       {151, 229, 169, 155, 185, 139, 269, 250, 239, 80, 111, 206, 46, 251, 168, 139, 179, 0, 202, 211, 128},
       {69, 120, 49, 128, 148, 128, 75, 186, 95, 134, 159, 63, 182, 119, 99, 65, 76, 202, 0, 161, 90},
       {111, 57, 185, 251, 26, 75, 163, 39, 93, 140, 101, 101, 226, 81, 69, 159, 86, 211, 161, 0, 115},
       {24, 109, 84, 137, 92, 49, 144, 152, 112, 50, 71, 78, 125, 127, 49, 50, 52, 128, 90, 115, 0}};

    // associative map of maps for easy access to city-city distances
    // using city ids
    distances = new HashMap <> ();
    for (int i = 0; i < NUM_CITIES; i++) {
      Map<Character, Integer> cityDistances = new HashMap<> ();
      for (int j = 0; j < NUM_CITIES; j++)
        cityDistances.put (CITIES [j], DISTS [i][j]);
      distances.put (CITIES [i], cityDistances);
    }

    // make this map immutable
    distances = Collections.unmodifiableMap (distances); 
  }
  
  void run () {
    char[][] sortedPopulation = new char [POPULATION_SIZE][];
    int[] sortedFitnesses = new int [POPULATION_SIZE];
    evaluate (initializePopulation (), sortedPopulation, sortedFitnesses);

    boolean terminate = false;
    int generation = 0;
    while (!terminate)
    {
      generation++;

      // diagnostic stats printed for every generation
      System.out.print ("gen [ " + generation + " ] fit [ " + sortedFitnesses [0] + " ] ");
      System.out.print ("best [ " + routeToString (sortedPopulation [0]) + " ] ");
      System.out.println ("mid [ " + midValue (sortedFitnesses) + " ]");

      // selection and crossover
      char[][] sortedParents = selection.select (sortedPopulation, sortedFitnesses);
      char[][] offspring = reproduce (sortedParents, crossover, POPULATION_SIZE, NUM_ELITES);

      // mutation if so desired
      if (mutation != null)
        for (int i = NUM_ELITES; i < POPULATION_SIZE; i++) // elites not mutated
          mutation.mutate (offspring [i]);

      // evaluation of new generation
      char[][] sortedOffspring = new char [POPULATION_SIZE][];
      int[] sortedOffspringFitnesses = new int [POPULATION_SIZE];
      evaluate (offspring, sortedOffspring, sortedOffspringFitnesses);

      // check for termination
      terminate = terminationRule.terminates (sortedFitnesses, sortedOffspringFitnesses);

      // current offsprings form population
      sortedPopulation = sortedOffspring;
      sortedFitnesses = sortedOffspringFitnesses;
    }
  }

  // return population with random permutations of visited cities
  private char[][] initializePopulation ()
  {
    char[][] population = new char [POPULATION_SIZE][];
    List<Character> visitedCities = new ArrayList<> ();
    for (char c : VISITED_CITIES)
      visitedCities.add (c);
    
    for (int i = 0; i < POPULATION_SIZE; i++)
    {
      Collections.shuffle (visitedCities);
      population [i] = new char [NUM_VISITED_CITIES];
      int j = 0;
      for (char c : visitedCities)
        population [i][j++] = c;
    }

    return population;
  }

  // evaluate, sort and return sorted population and sorted fitnesses
  // of routes in the population (in 2nd and 3rd parameter); note that
  // fitness is the opposite of route length (negative); to minimize
  // route length, we can maximize this fitness measure
  private void evaluate (char[][] population, char[][] sortedPopulation, int[] sortedFitnesses)
  {
    Integer[] fitnesses = new Integer [population.length];
    
    for (int i = 0; i < population.length; i++)
    {
      char[] route = population [i];
      fitnesses [i] = 0;
      
      // add distances from and to start city
      fitnesses [i] -= distances.get (STARTCITY).get (route [0]);
      fitnesses [i] -= distances.get (STARTCITY).get (route [route.length - 1]);
      
      // add city to city distances
      for (int j = 0; j < route.length - 1; j++)
        fitnesses [i] -= distances.get (route [j]).get (route [j + 1]);
    }
    Integer[] sortedIndices = sortIndices (fitnesses);
    for (int i = 0; i < population.length; i++)
    {
      int currentIndex = sortedIndices [i];
      sortedPopulation [i] = population [currentIndex];
      sortedFitnesses [i] = fitnesses [currentIndex];
    }
  }

  private char[][] reproduce (char[][] parents,
                              Crossover crossover,
                              int populationSize,
                              int numElites) {
    char[][] offspring = new char [populationSize][];
    int numOffspring;

    // select elites for next round
    for (numOffspring = 0; numOffspring < numElites && numOffspring < populationSize; numOffspring++)
      offspring [numOffspring] = parents [numOffspring];
    
    int numParents = parents.length;
    while (numOffspring < populationSize)
    {
      int[] parentIndices = new int [2];
      RandomUtils.randomIntegerPair (numParents, parentIndices);
      offspring [numOffspring++] = crossover.crossover (parents [parentIndices [0]],
                                                        parents [parentIndices [1]]);
    }

    return offspring;
  }
  
  private String routeToString (char[] route)
  {
    StringBuffer strBuf = new StringBuffer ();
    strBuf.append (STARTCITY);
    for (char c : route)
      strBuf.append (c);
    strBuf.append (STARTCITY);

    return strBuf.toString ();
  }

  // quick and dirty "median"
  public static int midValue (int[] array)
  {
    return array [array.length / 2];
  }
  
  // returns an array ind of indices which sorts the fitness array so
  // that fitnesses[ind[0]] > fitnesses[ind[1]] > fitnesses[ind[2]]
  // etc.
  private Integer[] sortIndices (Integer[] fitnesses)
  {
    ArrayIndexComparator comparator = new ArrayIndexComparator (fitnesses);
    Integer[] indices = comparator.createIndexArray ();
    
    // sort in descending order
    Arrays.sort (indices, comparator.reversed ()); 
    
    return indices;
  }

  private Selection selection;
  private Crossover crossover;
  private Mutation mutation;
  private TerminationRule terminationRule;
  final int POPULATION_SIZE;
  final int NUM_ELITES;
  final char STARTCITY;
  final char[] VISITED_CITIES;
  final char[] CITIES;
  final int NUM_CITIES;
  final int NUM_VISITED_CITIES;
  Map<Character, Map<Character, Integer>> distances;
}

// comparator for array of indices based on values from the array the
// indices refer to (for index sorting based on array contents)
class ArrayIndexComparator implements Comparator<Integer>
{
  public ArrayIndexComparator (Integer[] array)
  {
    this.array = array;
  }
  
  public Integer[] createIndexArray ()
  {
    Integer[] indices = new Integer [array.length];
    for (int i = 0; i < array.length; i++)
      indices [i] = i;
    return indices;
  }
  
  @Override
  public int compare (Integer index1, Integer index2)
  {
    return array [index1].compareTo (array [index2]);
  }
  
  private final Integer[] array;
}
