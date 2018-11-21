package it.unical.asde2018.sudoku.logic;

import it.unical.asde2018.sudoku.logic.util.Difficulty;

public class Generator {

	private static final int UNICITY_ITERATIONS = 5;

	private SudokuGrid sudokuSolved;
	private SudokuGrid sudokuToSolve;
	private boolean sudokuFound = false;

	public SudokuGrid getSudokuSolved() {
		return sudokuSolved;
	}

	public SudokuGrid getSudokuToSolve() {
		return sudokuToSolve;
	}

	public void generateSudokuPuzzle(Difficulty difficulty) {

		sudokuFound = false;

		while (!sudokuFound) {

			sudokuSolved = new SudokuGrid();
			Solver solver = new Solver(sudokuSolved);
			solver.solveSudoku();

			for (int i = 0; i < SudokuGrid.getSIZE(); i++) {
				for (int j = 0; j < SudokuGrid.getSIZE(); j++) {
					sudokuSolved.setNumberLocked(i, j, true);
				}
			}

			int removedNumber = 0;

			sudokuToSolve = new SudokuGrid(sudokuSolved);
			deleteNumber();
			removedNumber++;

			while (true) {
				if (verifyUnicity()) {
					if (removedNumber == difficulty.getNumberToRemove()) {
						sudokuFound = true;
						break;
					} else {
						deleteNumber();
						removedNumber++;
					}
				} else
					break;
			}
		}
	}

	public boolean verifyUnicity() {
		SudokuGrid[] possibleSolution = new SudokuGrid[UNICITY_ITERATIONS];

		for (int i = 0; i < possibleSolution.length; i++) {
			SudokuGrid grid = new SudokuGrid(sudokuToSolve);
			Solver solver = new Solver(grid);
			solver.solveSudoku();
			possibleSolution[i] = grid;
		}

		for (int i = 1; i < possibleSolution.length; i++)
			for (int j = 0; j < SudokuGrid.getSIZE(); j++)
				for (int k = 0; k < SudokuGrid.getSIZE(); k++)
					if (possibleSolution[i].getNumberInsideACell(j, k) != possibleSolution[0].getNumberInsideACell(j, k))
						return false;

		return true;
	}

	public void deleteNumber() {
		boolean deleted = false;
		while (!deleted) {
			int x = (int) (Math.random() * SudokuGrid.getSIZE());
			int y = (int) (Math.random() * SudokuGrid.getSIZE());
			if (sudokuToSolve.getNumberInsideACell(x, y) != 0) {
				sudokuToSolve.setNumberInsideACell(x, y, 0);
				sudokuToSolve.setNumberLocked(x, y, false);
				deleted = true;
			}
		}
	}
}
