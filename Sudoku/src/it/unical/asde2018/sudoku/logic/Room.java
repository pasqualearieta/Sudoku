package it.unical.asde2018.sudoku.logic;

import it.unical.asde2018.sudoku.model.Match;

public class Room {

	private Match match;

	private String sudokuSolved;
	private String sudokuToSolve;

	private Long idCreator;

	public Room(Match match, Long idCreator) {
		setMatch(match);

		setIdCreator(idCreator);

		// TODO bloccante
		SudokuHandler sudokuHandler = new SudokuHandler(getMatch().getDifficulty());

		setSudokuSolved(sudokuHandler.getSudokuSolved());
		setSudokuToSolve(sudokuHandler.getSudokuToSolve());
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getSudokuSolved() {
		return sudokuSolved;
	}

	public void setSudokuSolved(String sudokuSolved) {
		this.sudokuSolved = sudokuSolved;
	}

	public String getSudokuToSolve() {
		return sudokuToSolve;
	}

	public void setSudokuToSolve(String sudokuToSolve) {
		this.sudokuToSolve = sudokuToSolve;
	}

	public Long getIdCreator() {
		return idCreator;
	}

	public void setIdCreator(Long idCreator) {
		this.idCreator = idCreator;
	}

}
