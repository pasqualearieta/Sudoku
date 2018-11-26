package it.unical.asde2018.sudoku.logic;

import it.unical.asde2018.sudoku.model.Match;
import it.unical.asde2018.sudoku.model.User;

public class Room {

	private Match match;

	private boolean visible = true;
	
	private String sudokuSolved;
	private String sudokuToSolve;

	private User creator;

	public Room(Match match, User creator) {
		setMatch(match);

		setCreator(creator);
	}

	public Match getMatch() {
		return match;
	}
	

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

}
