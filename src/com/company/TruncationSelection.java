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


public class TruncationSelection extends Selection {

  public TruncationSelection (int proportion)
  {
    this.proportion = proportion;
  }
  
  public char[][] select (char[][] population,
                          int[] fitnesses) {
    final int POOL_SIZE = population.length / proportion;
    char[][] selected = new char[POOL_SIZE][];
    for (int i = 0; i < POOL_SIZE; i++) {
      selected[i] = population[i];
    }
    return selected;
  }

  private int proportion;
}
