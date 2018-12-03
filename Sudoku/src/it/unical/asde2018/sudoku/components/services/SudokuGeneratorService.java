package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.logic.SudokuHandler;
import it.unical.asde2018.sudoku.logic.util.Difficulty;
import it.unical.asde2018.sudoku.logic.util.SudokuPuzzles;

@Service
public class SudokuGeneratorService {

	/**
	 * Store for each difficulty a queue that contains sudokus
	 */
	private Map<Difficulty, Queue<SudokuPuzzles>> sudokusQueue;

	private static final int INITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY = 25;

	@PostConstruct
	private void init() {
		sudokusQueue = new HashMap<>();

		if (sudokusQueue.isEmpty()) {
			sudokusQueue.put(Difficulty.EASY, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.MEDIUM, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.HARD, new LinkedBlockingQueue<>());
		}
	}

	/**
	 * Method that return a sudoku of the desired difficulty
	 * 
	 * @param difficulty
	 *            Difficulty of the sudoku
	 * 
	 * @return a desired sudoku
	 */
	public SudokuPuzzles getSudokuByDifficulty(Difficulty difficulty) {
		return sudokusQueue.get(difficulty).poll();
	}

	/**
	 * Method that generate three sudokus, one for each difficulty
	 */
	public void generateSudoku() {
		generateSudokuEasy();
		generateSudokuMedium();
		generateSudokuHard();
	}

	/**
	 * Method that generate an EASY sodoku and put it in the queue of easy
	 * sudokus
	 */
	public void generateSudokuEasy() {
		SudokuHandler sudokuEASY = new SudokuHandler(Difficulty.EASY);
		sudokusQueue.get(Difficulty.EASY).add(new SudokuPuzzles(sudokuEASY.getSudokuToSolve(), sudokuEASY.getSudokuSolved()));
	}

	/**
	 * Method that generate a MEDIUM sodoku and put it in the queue of medium
	 * sudokus
	 */
	public void generateSudokuMedium() {
		SudokuHandler sudokuMEDIUM = new SudokuHandler(Difficulty.MEDIUM);
		sudokusQueue.get(Difficulty.MEDIUM).add(new SudokuPuzzles(sudokuMEDIUM.getSudokuToSolve(), sudokuMEDIUM.getSudokuSolved()));
	}

	/**
	 * Method that generate a HARD sodoku and put it in the queue of hard
	 * sudokus
	 */
	public void generateSudokuHard() {
		SudokuHandler sudokuHARD = new SudokuHandler(Difficulty.HARD);
		sudokusQueue.get(Difficulty.HARD).add(new SudokuPuzzles(sudokuHARD.getSudokuToSolve(), sudokuHARD.getSudokuSolved()));
	}

	/**
	 * 
	 * @return the number of the sudoku easy available
	 */
	public int getNumberOfSudokuEasy() {
		return sudokusQueue.get(Difficulty.EASY).size();
	}

	/**
	 * 
	 * @return the number of the sudoku medium available
	 */
	public int getNumberOfSudokuMedium() {
		return sudokusQueue.get(Difficulty.MEDIUM).size();
	}

	/**
	 * 
	 * @return the number of the sudoku hard available
	 */
	public int getNumberOfSudokuHard() {
		return sudokusQueue.get(Difficulty.HARD).size();
	}

	/**
	 * @return the total number of the available sudokus
	 */
	public int getTotalNumberOfSudokuAvailable() {
		int numberEasy = sudokusQueue.get(Difficulty.EASY).size();
		int numberMedium = sudokusQueue.get(Difficulty.MEDIUM).size();
		int numberHard = sudokusQueue.get(Difficulty.HARD).size();

		return numberEasy + numberMedium + numberHard;
	}

	/**
	 * 
	 * @return map of the sudokus
	 */
	public Map<Difficulty, Queue<SudokuPuzzles>> getSudokusQueue() {
		return sudokusQueue;
	}

	public int getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY() {
		return INITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY;
	}

}
