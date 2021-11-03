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

public class SwitchMutation extends Mutation
{
  SwitchMutation (int frequency)
  {
    assert frequency > 0;
    this.frequency = frequency;
  }

  public void mutate (char[] individual)
  {
    // we mutate on the average in a properion of 1/frequency of
    // individuals; here a random int in [0,...,frequency-1] is taken,
    // and mutation happens if the int equals 0 (the checked value
    // could be anything, 0 is selected arbitrarily here)
    if (RandomUtils.randomGenerator.nextInt (frequency) == 0)
    {
      int[] switchedIndices = new int [2];
      RandomUtils.randomIntegerPair (individual.length, switchedIndices);
      char tmp = individual [switchedIndices [0]];
      individual [switchedIndices [0]] = individual [switchedIndices [1]];
      individual [switchedIndices [1]] = tmp;
    }
  }
  
  private int frequency;
}
