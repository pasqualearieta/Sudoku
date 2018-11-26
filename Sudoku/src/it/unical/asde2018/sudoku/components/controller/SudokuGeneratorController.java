package it.unical.asde2018.sudoku.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import it.unical.asde2018.sudoku.components.services.SudokuGeneratorService;

@Controller
public class SudokuGeneratorController {

	@Autowired
	private SudokuGeneratorService sudokuGeneratorService;

	// execute this method every 30 seconds
	@Scheduled(fixedDelay = 30000)
	public void generate() {
		sudokuGeneratorService.generateSudoku();
	}
}
