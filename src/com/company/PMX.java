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


public class PMX extends Crossover
{
  char[] crossover (char[] parentA, char[] parentB)
  {
    // indices of subsequence copied directly from parent A
    int[] ASubInds = new int [2];
    RandomUtils.randomSortedIntegerPair (parentA.length, ASubInds);
    int AStartInd = ASubInds [0], AEndInd = ASubInds [1];

    char[] offspring = new char [parentA.length];
    int bInd = 0; // index when proceeding from left to right in B
    for (int i = 0; i < offspring.length; i++)
    {
      // if inside A subsequence, copy directly
      if (i >= AStartInd && i <= AEndInd)
        offspring [i] = parentA [i];
      else
      {
        // not inside A subsequence, copy first gene from B not inside
        // A subsequence
        boolean BGeneInA;
        do
        {
          BGeneInA = false;
          for (int j = AStartInd; !BGeneInA && j <= AEndInd; j++)
            if (parentB [bInd] == parentA [j])
              BGeneInA = true;
          if (BGeneInA)
            bInd++;
        } while (BGeneInA);

        offspring [i] = parentB [bInd++];
      }
    }
    
    return offspring;
  }
}
