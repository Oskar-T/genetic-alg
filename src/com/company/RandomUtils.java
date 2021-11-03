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

import java.util.Random;

public class RandomUtils
{
  // generate a pair of distinct random integers in range
  // [0,...,upper-1], returning them in 2nd parameter
  static public void randomIntegerPair (int upper, int[] ints)
  {
    ints [0] = randomGenerator.nextInt (upper);
    int r = randomGenerator.nextInt (upper - 1);
    ints [1] = (r + ints [0] + 1) % upper;
  }

  // same as above, but returned indices are in increasing order
  static public void randomSortedIntegerPair (int upper, int[] ints)
  {
    int[] pair = new int [2];
    randomIntegerPair (upper, pair);
    ints [0] = Math.min (pair [0], pair [1]);
    ints [1] = Math.max (pair [0], pair [1]);
  }

  public static Random randomGenerator = new Random ();
}
