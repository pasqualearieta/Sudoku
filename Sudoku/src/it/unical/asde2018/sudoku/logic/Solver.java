package it.unical.asde2018.sudoku.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solver {

	private Map<Integer, Map<Integer, Set<Integer>>> testedNumbers = new HashMap<>(SudokuGrid.getSIZE());

	private SudokuGrid sudoku;

	private int currentRow = 0;
	private int currentColumn = 0;

	private boolean doBacktrack = false;

	Solver(SudokuGrid sudoku) {

		for (int i = 0; i < SudokuGrid.getSIZE(); i++) {
			testedNumbers.put(i, new HashMap<Integer, Set<Integer>>(SudokuGrid.getSIZE()));
			for (int j = 0; j < SudokuGrid.getSIZE(); j++) {
				testedNumbers.get(i).put(j, new HashSet<Integer>(SudokuGrid.getSIZE()));
			}
		}

		this.sudoku = sudoku;
	}

	private boolean isValid(int x, int y, int num) {
		// First we check in the column
		for (int i = 0; i < SudokuGrid.getSIZE(); i++) {
			if (i == y)
				continue; // Jump to next iteration if we are on our cell, ignoring any further instruction

			if (sudoku.getNumberInsideACell(x, i) == num)
				return false;
		}

		// Then we check the rows
		for (int i = 0; i < SudokuGrid.getSIZE(); i++) {
			if (i == x)
				continue; // Jump to next iteration if we are on our cell, ignoring any further instruction

			if (sudoku.getNumberInsideACell(i, y) == num)
				return false;
		}

		int region = sudoku.getRegion(currentRow, currentColumn);

		for (int i = 0; i < SudokuGrid.getSIZE(); i++)
			for (int j = 0; j < SudokuGrid.getSIZE(); j++)
				if (region == sudoku.getRegion(i, j))
					if (i != x && j != y)
						if (sudoku.getNumberInsideACell(i, j) == num)
							return false;

		return true;
	}

	public boolean evaluateCell() {
		if (sudoku.isNumberLocked(currentRow, currentColumn)) {
			if (doBacktrack)
				return backtrack();
			doBacktrack = false;

			return moveCurrentCell();
		} else {
			if (sudoku.getNumberInsideACell(currentRow, currentColumn) == 0) {
				int casual_number = (int) (Math.random() * SudokuGrid.getSIZE() + 1);
				sudoku.setNumberInsideACell(currentRow, currentColumn, casual_number);
				testedNumbers.get(currentColumn).get(currentRow).add(casual_number);
				return false;
			}

			if (isValid(currentRow, currentColumn, sudoku.getNumberInsideACell(currentRow, currentColumn)) && !doBacktrack)
				return moveCurrentCell();
			else {
				if (testedNumbers.get(currentColumn).get(currentRow).size() == SudokuGrid.getSIZE()) {
					doBacktrack = true;
					sudoku.setNumberInsideACell(currentRow, currentColumn, 0);
					testedNumbers.get(currentColumn).get(currentRow).clear();
					return backtrack();
				} else {
					doBacktrack = false;
					int casualNumber;
					while (true) {
						casualNumber = (int) (Math.random() * SudokuGrid.getSIZE() + 1);
						if (!testedNumbers.get(currentColumn).get(currentRow).contains(casualNumber)) {
							break;
						}
					}
					sudoku.setNumberInsideACell(currentRow, currentColumn, casualNumber);
					testedNumbers.get(currentColumn).get(currentRow).add(casualNumber);
				}
			}
		}

		return false;
	}

	private boolean moveCurrentCell() {
		if (currentRow == SudokuGrid.getSIZE() - 1) {
			if (currentColumn == SudokuGrid.getSIZE() - 1)
				return true;

			currentRow = 0;
			currentColumn++;
		} else {
			currentRow++;
		}
		return false;
	}

	private boolean backtrack() {
		if (currentRow == 0) {
			currentRow = SudokuGrid.getSIZE() - 1;
			currentColumn--;
		} else {
			currentRow--;
		}
		return false;
	}

	public void solveSudoku() {
		while (!evaluateCell()) {
		}
	}

}
