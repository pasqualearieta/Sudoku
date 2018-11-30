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

	private Map<Difficulty, Queue<SudokuPuzzles>> sudokusQueue;

	private static final int INITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY = 30;

	@PostConstruct
	private void init() {
		sudokusQueue = new HashMap<>();

		if (sudokusQueue.isEmpty()) {
			sudokusQueue.put(Difficulty.EASY, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.MEDIUM, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.HARD, new LinkedBlockingQueue<>());
		}
	}

	public SudokuPuzzles getSudokuByDifficulty(Difficulty difficulty) {
		return sudokusQueue.get(difficulty).poll();
	}

	public void generateSudoku() {
		generateSudokuEasy();
		generateSudokuMedium();
		generateSudokuHard();
	}

	public void generateSudokuEasy() {
		SudokuHandler sudokuEASY = new SudokuHandler(Difficulty.EASY);
		sudokusQueue.get(Difficulty.EASY).add(new SudokuPuzzles(sudokuEASY.getSudokuToSolve(), sudokuEASY.getSudokuSolved()));
	}

	public void generateSudokuMedium() {
		SudokuHandler sudokuMEDIUM = new SudokuHandler(Difficulty.MEDIUM);
		sudokusQueue.get(Difficulty.MEDIUM).add(new SudokuPuzzles(sudokuMEDIUM.getSudokuToSolve(), sudokuMEDIUM.getSudokuSolved()));
	}

	public void generateSudokuHard() {
		SudokuHandler sudokuHARD = new SudokuHandler(Difficulty.HARD);
		sudokusQueue.get(Difficulty.HARD).add(new SudokuPuzzles(sudokuHARD.getSudokuToSolve(), sudokuHARD.getSudokuSolved()));
	}

	public int getNumberOfSudokuEasy() {
		return sudokusQueue.get(Difficulty.EASY).size();
	}

	public int getNumberOfSudokuMedium() {
		return sudokusQueue.get(Difficulty.MEDIUM).size();
	}

	public int getNumberOfSudokuHard() {
		return sudokusQueue.get(Difficulty.HARD).size();
	}

	public int getMinNumberOfSudokuAvailable() {
		int numberEasy = sudokusQueue.get(Difficulty.EASY).size();
		int numberMedium = sudokusQueue.get(Difficulty.MEDIUM).size();
		int numberHard = sudokusQueue.get(Difficulty.HARD).size();

		int m = Math.min(numberEasy, numberMedium);

		return Math.min(m, numberHard);
	}

	public int getTotalNumberOfSudokuAvailable() {
		int numberEasy = sudokusQueue.get(Difficulty.EASY).size();
		int numberMedium = sudokusQueue.get(Difficulty.MEDIUM).size();
		int numberHard = sudokusQueue.get(Difficulty.HARD).size();

		return numberEasy + numberMedium + numberHard;
	}

	public Map<Difficulty, Queue<SudokuPuzzles>> getSudokusQueue() {
		return sudokusQueue;
	}

	public int getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY() {
		return INITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY;
	}

}
