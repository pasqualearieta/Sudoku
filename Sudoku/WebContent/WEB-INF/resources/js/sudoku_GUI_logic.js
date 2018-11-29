var data = new Array(9);
var totalNumber = 81;
var totalNumberInTheGrid = 0;
var insertedNumber = 0;
var opponent_result = 0;
var ended = false;
var quit = false;
var barSelector;
var bar;
var percentage = 0;

$(document).ready(function() {
	
	// init component
	barSelector = document.querySelector(".ldBar");
	bar = new ldBar(barSelector);
	updateBar();
	var d = new Date();
	$("#startingTime").val(d.getTime());
	// flow function
	retrieveOpponentStatus();
	main();

	$("#exit").on('click', function() {
		$("#resultModal").modal('hide');
		window.location.href = "./review";
	});

	$("#disconnected").on('click', function() {
		$("#resultModal").modal('hide');	
	});

	$("#quitGame").on('click', function() {
		quit = true;
		$.ajax({
			type: "POST",
			url : "leaveMatchBeforeEnd",
			success : function(result) {
				window.location.href = "./";
			},

		});
	});
});

function main() {
	var puzzle = $("#sudokuPuzzle").val();
	var sudoku = new Sudoku();
	sudoku.importPuzzle(puzzle);
	checkEndGame();
}

function retrieveOpponentStatus() {
	if (quit == false && ended == false) {
		$.ajax({
			type: "POST",
			url : "requestEvent",
			data : {
				number_inserted : insertedNumber
			},
			success : function(result) {
				if (result != -1 && result != 400 && result != undefined && result != "") {
					
					var previous_result = opponent_result;
					var current_result = result;

					if (current_result != previous_result) {
						opponent_result = current_result;
						percentage = Math.round(current_result);

						setTimeout(() => {
							updateBar();
						}, 1000);
					}
				}

				if (result == 400) {
					$("#opponentStatus").hide();
					updateModal("Disconnected User", "Your Opponent was disconnected", "disconnected");
				}

				else {
					setTimeout(function() {
						retrieveOpponentStatus();
					}, 3000)
				}
			},

		});
	}
}

function checkEndGame() {

	var puzzle = this.getPuzzleArrayStr();

	if (totalNumberInTheGrid == 81 && ended == false) {
		$.ajax({
			type: "POST",
			url : "checkEndGame",
			data : {
				puzzle : puzzle
			},
			success : function(result) {
				if (result != "WRONG") {
					ended = true;
					updateModal("Game Result", result, "exit");
				} else {
					$("#wrongSudoku").fadeIn();
			}
			},

		});
	}
	if (ended == false) {
		setTimeout(function() {	
			var d = new Date();
			$("#time").html(timeConversion(d.getTime() - $("#startingTime").val()));
			checkEndGame();
		}, 1000)
	}
	if(totalNumberInTheGrid < 81)
		$("#wrongSudoku").fadeOut();

}

// Sudoku Grid function
function Sudoku() {
	var $domobj = $("#sudoku");
	$domobj
			.addClass("col-lg-8 col-lg-offset-4 col-md-8 col-md-offset-4 col-xs-12");
	var $table = $("<table>").addClass("sudokuTable");
	var _self = this;
	var selectedCell = new Vec2d();
	var selectedRow = 0;
	var selectedCol = 0;
	var selectedBox = 0;
	var selectedNum = '';

	$domobj.append($table);

	for (var y = 0; y < data.length; y++) {
		var curRow = $("<tr>");
		$table.append(curRow);
		data[y] = new Array(9);

		for (var x = 0; x < data[y].length; x++) {
			var cell = $("<td>");
			cell.addClass("sudokuCell");

			if (x % 3 === 2 && x !== 8)
				cell.addClass("hspace");
			if (y % 3 === 2 && y !== 8)
				cell.addClass("vspace");

			curRow.append(cell);
			var numberCell = $("<div>").addClass("sudokuNumberCell");
			cell.append(numberCell);

			data[y][x] = new Cell(new Vec2d(x, y), numberCell, this); // create
			// vertical
			// column
		}

	}

	$(document).on("keydown", function(e) {
		var evt = e || window.event;

		// Prevent backspace from navigating back.
		if (e.which === 8 && !$(e.target).is("input, textarea"))
			e.preventDefault();

		switch (evt.which) {
		case 37: // left
			_self.moveSelected(-1, 0);
			break;

		case 38: // up
			_self.moveSelected(0, -1);
			break;

		case 39: // right
			_self.moveSelected(1, 0);
			break;

		case 40: // down
			_self.moveSelected(0, 1);
			break;

		case 8: // backspace
		case 46: // delete
		case 48: // zero
			_self.clearSelectedValue();
			break;

		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57: // 1-9
			_self.setSelectedValue(evt.keyCode - 48);
			break;
		}
		console.log(checkAllNumberInserted());
	});

	this.importPuzzle = function(puzzleStr) {
		var curRow = 0;
		var curCol = 0;

		for (var i = 0; i < puzzleStr.length; i++) {
			curChar = puzzleStr[i];
			if (curChar >= 1 && curChar <= 9) // is digit 1-9
			{
				var num = curChar;
				data[curRow][curCol].setValue(parseInt(num));
				data[curRow][curCol].setLocked(true);
				curCol++;
			} else if (curChar === '.') {
				data[curRow][curCol].setValue('');
				data[curRow][curCol].setLocked(false);
				curCol++;
			} else if (curChar === '\n') {
				curCol = 0;
				curRow++;
			}
		}
	};

	this.moveSelected = function(xOffset, yOffset) {
		var newX = (selectedCell.x + xOffset) % 9;
		while (newX < 0)
			newX += 9; // go in the opposite cell (if i'm at the border left
		// cell i go in the right border cell)
		var newY = (selectedCell.y + yOffset) % 9;
		while (newY < 0)
			newY += 9; // go in the opposite cell (same reasoning for the
		// column)
		this.setSelected(newX, newY);
	};

	this.setSelectedValue = function(val) {
		if (data[selectedCell.y][selectedCell.x].setValue(val)) {
			deselectNum(selectedNum); // deselect all the number that are not
			// equal to the number inserted (in the
			// cell selected everything is
			// highlighted blue, while for the other
			// equal number in the matric the border
			// are highlighted blue)
			selectedNum = val;
			selectNum(selectedNum);
			data[selectedCell.y][selectedCell.x].select();
			checkSelectedCellForErrors();
		}
	};

	function getBoxNum(x, y) {
		var boxX = Math.floor(x / 3);
		var boxY = Math.floor(y / 3);
		return boxY * 3 + boxX;
	}

	function boxNumToCoords(num) {
		var boxX = num % 3;
		var boxY = Math.floor(num / 3);
		return new Vec2d(boxX * 3, boxY * 3);
	}

	function checkRowForErrors(row) {
		var nums = [];
		var error = false;

		for (var col = 0; col < 9; col++) {
			var curVal = data[row][col].value;
			if (curVal !== '' && nums.indexOf(curVal) !== -1) {
				error = true;
				break;
			} else
				nums.push(curVal);
		}

		if (error)
			for (var col = 0; col < 9; col++)
				data[row][col].addError();

		return error;
	}

	function checkColForErrors(col) {
		var nums = [];
		var error = false;

		for (var row = 0; row < 9; row++) {
			var curVal = data[row][col].value;
			if (curVal !== '' && nums.indexOf(curVal) !== -1) {
				error = true;
				break;
			} else
				nums.push(curVal);
		}

		if (error)
			for (var row = 0; row < 9; row++)
				data[row][col].addError();

		return error;
	}

	function checkBoxForErrors(box) {
		var nums = [];
		var error = false;
		var boxCoords = boxNumToCoords(box);

		for (var row = boxCoords.y; row < boxCoords.y + 3; row++) {
			for (var col = boxCoords.x; col < boxCoords.x + 3; col++) {
				var curVal = data[row][col].value;
				if (curVal !== '' && nums.indexOf(curVal) !== -1) {
					error = true;
					break;
				} else
					nums.push(curVal);
			}
		}

		if (error)
			for (var row = boxCoords.y; row < boxCoords.y + 3; row++)
				for (var col = boxCoords.x; col < boxCoords.x + 3; col++)
					data[row][col].addError();

		return error;
	}

	function clearBoardErrors() {
		for (var row = 0; row < 9; row++)
			for (var col = 0; col < 9; col++)
				data[row][col].removeError();
	}

	function checkSelectedCellForErrors() {
		clearBoardErrors();
		checkRowForErrors(selectedRow);
		checkColForErrors(selectedCol);
		checkBoxForErrors(selectedBox);
	}

	function deselectRow(row) {
		for (var x = 0; x < data[row].length; x++)
			data[row][x].deselect();
	}
	function deselectColumn(col) {
		for (var y = 0; y < data.length; y++)
			data[y][col].deselect();
	}
	function deselectBox(box) {
		var boxCoords = boxNumToCoords(box);
		for (var y = boxCoords.y; y < boxCoords.y + 3; y++)
			for (var x = boxCoords.x; x < boxCoords.x + 3; x++)
				data[y][x].deselect();
	}
	function deselectNum(num) {
		for (var y = 0; y < 9; y++)
			for (var x = 0; x < 9; x++)
				if (data[y][x].value === num && num !== '')
					data[y][x].deselect();
	}
	function selectRow(row) {
		if (data[selectedCell.y][selectedCell.x].locked === false)
			for (var x = 0; x < data[row].length; x++)
				data[row][x].secondarySelect();

	}
	function selectColumn(col) {
		if (data[selectedCell.y][selectedCell.x].locked === false)
			for (var y = 0; y < data.length; y++)
				data[y][col].secondarySelect();
	}
	function selectBox(box) {
		if (data[selectedCell.y][selectedCell.x].locked === false) {
			var boxCoords = boxNumToCoords(box);
			for (var y = boxCoords.y; y < boxCoords.y + 3; y++)
				for (var x = boxCoords.x; x < boxCoords.x + 3; x++)
					data[y][x].secondarySelect();
		}
	}
	function selectNum(num) {
		for (var y = 0; y < 9; y++)
			for (var x = 0; x < 9; x++)
				if (data[y][x].value === num && num !== '')
					data[y][x].sameNumberSelect();
	}

	this.setSelected = function(xPos, yPos) {
		// deselect all row, column, box
		deselectRow(selectedRow);
		deselectColumn(selectedCol);
		deselectBox(selectedBox);
		deselectNum(selectedNum);

		// update selected
		selectedRow = yPos;
		selectedCol = xPos;
		selectedBox = getBoxNum(xPos, yPos);
		selectedNum = data[yPos][xPos].value;
		selectedCell.set(xPos, yPos);

		// reselect
		selectRow(selectedRow);
		selectColumn(selectedCol);
		selectBox(selectedBox);
		selectNum(selectedNum);
		data[yPos][xPos].select();
	};

	this.clearSelectedValue = function() {
		if (data[selectedCell.y][selectedCell.x].clearValue()) {
			deselectNum(selectedNum);
			selectedNum = '';
			checkSelectedCellForErrors();
		}
	};

	this.setSelected(0, 0);
}

function Cell(pos, current_cell, parent, locked, value) {
	this.value = typeof value !== 'undefined' ? value : '';
	this.locked = typeof locked !== 'undefined' ? locked : false;

	var $current_cell = current_cell;
	var parent = parent;
	$current_cell.append($("<div>"));

	$current_cell.click(function() {
		parent.setSelected(pos.x, pos.y);
	});

	var $number = $($current_cell.find("div")[0]);
	$number.addClass("num");
	this.deselect = function() {
		$current_cell.removeClass("selectedCell secondarySelected sameNumber");
	};
	this.select = function() {
		$current_cell.removeClass("secondarySelected sameNumber").addClass(
				"selectedCell");
	};
	this.secondarySelect = function() {
		$current_cell.addClass("secondarySelected");
	};
	this.sameNumberSelect = function() {
		$current_cell.addClass("sameNumber");
	};
	this.setValue = function(val) {
		if (val >= 1 && val <= 9 && this.locked === false) {
			this.value = val;
			$number.text(this.value);
			return true;
		}
		return false;
	};
	this.setLocked = function(locked) {
		this.locked = locked;
		if (locked)
			$current_cell.addClass("locked");
		else
			$current_cell.removeClass("locked");
	};
	this.setLocked(this.locked);
	this.clearValue = function() {
		if (this.locked === false) {
			this.value = '';
			$number.text(this.value);
			return true;
		}
		return false;
	};
	this.addError = function() {
		$current_cell.addClass("error");
	}
	this.removeError = function() {
		$current_cell.removeClass("error");
	}
	this.setValue(this.value);
}

// data[y][x] = new Cell(new Vec2d(x, y), numberCell, this); //create vertical
// column
/** ******** Vec2d Class ********* */
function Vec2d(newX, newY) {
	this.x = typeof newX !== 'undefined' ? newX : 0;
	this.y = typeof newY !== 'undefined' ? newY : 0;
}

/** ******** Vec2d Methods ********* */
Vec2d.prototype.valueOf = function() {
	return {
		x : this.x,
		y : this.y
	};
};

Vec2d.prototype.getOffset = function(x, y) {
	return new Vec2d(this.x + x, this.y + y);
};

Vec2d.prototype.set = function(x, y) {
	this.x = x;
	this.y = y;
};
/** ******** Vec2d Static Methods ********* */
Vec2d.add = function(v1, v2) {
	return new Vec2d(v1.x + v2.x, v1.y + v2.y);
};

this.checkAllNumberInserted = function() {

	insertedNumber = 0;
	totalNumberInTheGrid = 0;
	for (var i = 0; i < data.length; i++)
		for (var j = 0; j < data[i].length; j++)
			if (data[i][j].value !== '') {
				totalNumberInTheGrid++;
				if (data[i][j].locked === false)
					insertedNumber++;
			}
}

this.getPuzzleArrayStr = function() {
	var txt = "";

	for (var i = 0; i < data.length; i++) {
		for (var j = 0; j < data[i].length; j++) {
			if (data[i][j].value !== '')
				txt += (data[i][j].value);
			else {
				txt += '.';
			}
		}
		if (i < data.length - 1)
			txt += "\n";
	}
	return txt;
};
// END SUDOKU GRID FUNCTION

function updateBar(){
    $(".ldBar-label").hide();
	$("#status").html(percentage + "%");
	bar.set(percentage);
 }
 
 function updateModal(title, message, button){
	 	$("#modalTitle").html(title);
		$("#exit").hide();
		$("#disconnected").hide();
		$("#"+button).show();
		$("#resultModal").modal('show');
		$("#resultStatus").html(message);
 }
 

 function timeConversion(millisec) {

 	var seconds = (millisec / 1000).toFixed(1);

 	var minutes = (millisec / (1000 * 60)).toFixed(1);

 	var hours = (millisec / (1000 * 60 * 60)).toFixed(1);

 	var days = (millisec / (1000 * 60 * 60 * 24)).toFixed(1);

 	if (seconds < 60) {
 		return seconds + " Sec";
 	} else if (minutes < 60) {
 		return minutes + " Min";
 	} else if (hours < 24) {
 		return hours + " Hrs";
 	} else {
 		return days + " Days"
 	}
 }





