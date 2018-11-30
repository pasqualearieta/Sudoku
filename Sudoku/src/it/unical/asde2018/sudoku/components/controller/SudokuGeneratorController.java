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

	// execute this method every second
	@Scheduled(fixedDelay = 1000)
	public void generate() {

		int numberOfConnectedUsers = connectedUsers.getConnectedUsers().size();
		int numberOfSudokuEasy = sudokuGeneratorService.getNumberOfSudokuEasy();
		int numberOfSudokuMedium = sudokuGeneratorService.getNumberOfSudokuMedium();
		int numberOfSudokuHard = sudokuGeneratorService.getNumberOfSudokuHard();

		if (numberOfConnectedUsers == 0) {
			if (numberOfSudokuEasy < sudokuGeneratorService.getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY())
				sudokuGeneratorService.generateSudokuEasy();
			if (numberOfSudokuMedium < sudokuGeneratorService.getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY())
				sudokuGeneratorService.generateSudokuMedium();
			if (numberOfSudokuHard < sudokuGeneratorService.getINITIAL_NUMBER_OF_SUDOKU_PER_DIFFICULTY())
				sudokuGeneratorService.generateSudokuHard();
		} else {
			if (numberOfSudokuEasy <= numberOfConnectedUsers / 2)
				sudokuGeneratorService.generateSudokuEasy();
			if (numberOfSudokuMedium <= numberOfConnectedUsers / 2)
				sudokuGeneratorService.generateSudokuMedium();
			if (numberOfSudokuHard <= numberOfConnectedUsers / 2)
				sudokuGeneratorService.generateSudokuHard();
		}
	}
}
