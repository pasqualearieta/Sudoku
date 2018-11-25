package it.unical.asde2018.sudoku.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import it.unical.asde2018.sudoku.logic.SudokuHandler;
import it.unical.asde2018.sudoku.logic.util.Difficulty;

@Service
public class SudokuGeneratorService {

	private Map<Difficulty, Queue<String>> sudokusQueue;

	@PostConstruct
	private void init() {
		sudokusQueue = new HashMap<>();

		if (sudokusQueue.isEmpty()) {
			sudokusQueue.put(Difficulty.EASY, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.MEDIUM, new LinkedBlockingQueue<>());
			sudokusQueue.put(Difficulty.HARD, new LinkedBlockingQueue<>());
		}
	}

	public String getSudokuByDifficulty(Difficulty difficulty) {
		return sudokusQueue.get(difficulty).poll();
	}

	public void generateSudoku() {
		SudokuHandler sudokuEASY = new SudokuHandler(Difficulty.EASY);
		SudokuHandler sudokuMEDIUM = new SudokuHandler(Difficulty.MEDIUM);
		SudokuHandler sudokuHARD = new SudokuHandler(Difficulty.HARD);

		sudokusQueue.get(Difficulty.EASY).add(sudokuEASY.getSudokuToSolve());
		sudokusQueue.get(Difficulty.MEDIUM).add(sudokuMEDIUM.getSudokuToSolve());
		sudokusQueue.get(Difficulty.HARD).add(sudokuHARD.getSudokuToSolve());

	}

	public Map<Difficulty, Queue<String>> getSudokusQueue() {
		return sudokusQueue;
	}

}
