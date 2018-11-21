package it.unical.asde2018.sudoku.logic;

import it.unical.asde2018.sudoku.logic.util.Difficulty;

public class SudokuHandler {

	private Difficulty difficulty;
	private Generator generator;

	private SudokuGrid sudokuSolved;
	private SudokuGrid sudokuToSolve;

	public SudokuHandler(Difficulty difficulty) {
		setGenerator(new Generator());
		setDifficulty(difficulty);
		generatePuzzle();
	}

	private void generatePuzzle() {
		getGenerator().generateSudokuPuzzle(getDifficulty());

		sudokuSolved = getGenerator().getSudokuSolved();
		sudokuToSolve = getGenerator().getSudokuToSolve();
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public String getSudokuSolved() {
		return sudokuSolved.toString();
	}

	public String getSudokuToSolve() {
		return sudokuToSolve.toString();
	}
}
