package it.unical.asde2018.sudoku.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import it.unical.asde2018.sudoku.components.services.ConnectedUsersService;
import it.unical.asde2018.sudoku.components.services.SudokuGeneratorService;

@Controller
public class SudokuGeneratorController {

	@Autowired
	private SudokuGeneratorService sudokuGeneratorService;
	@Autowired
	private ConnectedUsersService connectedUsers;

	/**
	 * Scheduled method that allow the generation of the sudoku scheme
	 */
	@Scheduled(fixedDelay = 1000)
	public void generate() {

		int numberOfConnectedUsers = connectedUsers.getConnectedUsers().size();
		int numberOfSudokuEasy = sudokuGeneratorService.getNumberOfSudokuEasy();
		int numberOfSudokuMedium = sudokuGeneratorService.getNumberOfSudokuMedium();
		int numberOfSudokuHard = sudokuGeneratorService.getNumberOfSudokuHard();

		if (sudokuGeneratorService.getTotalNumberOfSudokuAvailable() < sudokuGeneratorService.getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY() * 3) {
			sudokuGeneratorService.generateSudoku();

			// System.out.println("SudokuGeneratorController.generate()---IF");
			// System.err.println("SIZE -> E=" + numberOfSudokuEasy + " M=" +
			// numberOfSudokuMedium + " H=" + numberOfSudokuHard);
		} else {
			if (numberOfSudokuEasy <= Math.ceil((double) numberOfConnectedUsers / 2))
				sudokuGeneratorService.generateSudokuEasy();
			if (numberOfSudokuMedium <= Math.ceil((double) numberOfConnectedUsers / 2))
				sudokuGeneratorService.generateSudokuMedium();
			if (numberOfSudokuHard <= Math.ceil((double) numberOfConnectedUsers / 2))
				sudokuGeneratorService.generateSudokuHard();

			// System.out.println("SudokuGeneratorController.generate()---ELSE");
			// System.err.println("SIZE -> E=" + numberOfSudokuEasy + " M=" +
			// numberOfSudokuMedium + " H=" + numberOfSudokuHard);

		}
	}
}
