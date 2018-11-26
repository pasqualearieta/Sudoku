package it.unical.asde2018.sudoku.logic.util;

public class SudokuPuzzles {

	private String sudokuToSolve;
	private String sudokuSolved;

	public SudokuPuzzles(String sudokuToSolve, String sudokuSolved) {
		super();
		this.setSudokuToSolve(sudokuToSolve);
		this.setSudokuSolved(sudokuSolved);
	}

	public String getSudokuToSolve() {
		return sudokuToSolve;
	}

	public void setSudokuToSolve(String sudokuToSolve) {
		this.sudokuToSolve = sudokuToSolve;
	}

	public String getSudokuSolved() {
		return sudokuSolved;
	}

	public void setSudokuSolved(String sudokuSolved) {
		this.sudokuSolved = sudokuSolved;
	}

}
