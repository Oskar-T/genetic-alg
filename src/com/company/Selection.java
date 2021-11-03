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


// class for selecting parents for next generation
public abstract class Selection
{
  // selection from population using fitnesses, with population sorted
  // according to fitnesses; needs to be implemented by all concrete
  // classes
  protected abstract char[][] select (char[][] population,
                                      int[] fitnesses);

}
