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


// terminates if no increase in fitness of best and no increase in
// "median" fitness; assumes fitness arrays are in decreasing order
public class TerminationRuleBestMid extends TerminationRule
{
  public boolean terminates (int[] parentFitnesses, int[] offspringFitnesses)
  {
    return offspringFitnesses [0] <= parentFitnesses [0]
      && TSPGeneticAlgorithm.midValue (offspringFitnesses)
      <= TSPGeneticAlgorithm.midValue (parentFitnesses);
  }
}
