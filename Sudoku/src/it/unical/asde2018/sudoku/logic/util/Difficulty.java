package it.unical.asde2018.sudoku.logic.util;

public enum Difficulty {
	EASY(32), MEDIUM(42), HARD(52);

	private final int numberToRemove;

	Difficulty(int numberToRemove) {
		this.numberToRemove = numberToRemove;
	}

	public int getNumberToRemove() {
		return numberToRemove;
	}
}
