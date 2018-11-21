package it.unical.asde2018.sudoku.logic.util;

import it.unical.asde2018.sudoku.logic.SudokuGrid;

public class RegionsGrid {

	int regions[][];

	public RegionsGrid() {

		regions = new int[SudokuGrid.getSIZE()][SudokuGrid.getSIZE()];

		int regionIndex = 1;

		for (int i = 0; i < regions.length; i += 3) {
			for (int j = 0; j < regions.length; j += 3) {
				for (int r = i; r < i + 3; r++) {
					for (int c = j; c < j + 3; c++) {
						regions[r][c] = regionIndex;
					}
				}
				regionIndex++;
			}
		}
	}

	public int getRegion(int x, int y) {
		return regions[y][x];
	}
}
