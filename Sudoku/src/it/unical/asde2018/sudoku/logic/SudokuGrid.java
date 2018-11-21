package it.unical.asde2018.sudoku.logic;

import it.unical.asde2018.sudoku.logic.util.RegionsGrid;

public class SudokuGrid {

	private static final int SIZE = 9;

	private int[][] sudokuGridNumbers;
	private boolean[][] sudokuGridNumbersLocked;

	private RegionsGrid regionsGrid;

	public SudokuGrid() {
		init();
	}

	private void init() {
		sudokuGridNumbers = new int[getSIZE()][getSIZE()];
		sudokuGridNumbersLocked = new boolean[getSIZE()][getSIZE()];
		regionsGrid = new RegionsGrid();
	}

	public SudokuGrid(SudokuGrid otherBoard) {

		init();

		for (int i = 0; i < getSIZE(); i++)
			for (int j = 0; j < getSIZE(); j++) {
				sudokuGridNumbers[i][j] = otherBoard.sudokuGridNumbers[i][j];
				sudokuGridNumbersLocked[i][j] = otherBoard.sudokuGridNumbersLocked[i][j];
			}
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();

		for (int i = 0; i < sudokuGridNumbers.length; i++) {
			for (int j = 0; j < sudokuGridNumbers.length; j++)
				str.append((sudokuGridNumbers[i][j] != 0) ? (sudokuGridNumbers[i][j] + "") : ".");

			if (i < sudokuGridNumbers.length - 1)
				str.append("\n");
		}

		return str.toString();
	}

	public void setNumberInsideACell(int x, int y, int num) {
		sudokuGridNumbers[y][x] = num;
	}

	public void setNumberLocked(int x, int y, boolean locked) {
		sudokuGridNumbersLocked[y][x] = locked;
	}

	public int getNumberInsideACell(int x, int y) {
		return sudokuGridNumbers[y][x];
	}

	public int getRegion(int x, int y) {
		return regionsGrid.getRegion(x, y);
	}

	public boolean isNumberLocked(int x, int y) {
		return sudokuGridNumbersLocked[y][x];
	}

	public boolean isSolved() {
		for (int i = 0; i < getSIZE(); i++)
			for (int j = 0; j < getSIZE(); j++)
				if (getNumberInsideACell(i, j) == 0)
					return false;

		return true;
	}

	public static final int getSIZE() {
		return SIZE;
	}
}
