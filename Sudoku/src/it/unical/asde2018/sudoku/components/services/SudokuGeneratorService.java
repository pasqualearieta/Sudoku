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
		SudokuHandler sudokuEASY = new SudokuHandler(Difficulty.EASY);
		SudokuHandler sudokuMEDIUM = new SudokuHandler(Difficulty.MEDIUM);
		SudokuHandler sudokuHARD = new SudokuHandler(Difficulty.HARD);

		sudokusQueue.get(Difficulty.EASY).add(new SudokuPuzzles(sudokuEASY.getSudokuToSolve(), sudokuEASY.getSudokuSolved()));
		sudokusQueue.get(Difficulty.MEDIUM).add(new SudokuPuzzles(sudokuMEDIUM.getSudokuToSolve(), sudokuMEDIUM.getSudokuSolved()));
		sudokusQueue.get(Difficulty.HARD).add(new SudokuPuzzles(sudokuHARD.getSudokuToSolve(), sudokuHARD.getSudokuSolved()));

	}

	public Map<Difficulty, Queue<SudokuPuzzles>> getSudokusQueue() {
		return sudokusQueue;
	}

}
