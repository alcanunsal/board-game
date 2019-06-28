
import java.io.*;
import java.util.*;

public class EAU2016400105 {
	public static void main (String [] args) throws FileNotFoundException {
		Scanner console = new Scanner (System.in); // to get input from the player
		String [] remainings = {"BTSH", "BTSS", "BTRH", "BTRS", "BSSH", "BSSS", "BSRH", "BSRS", "WTSH", "WTSS", "WTRH", "WTRS", "WSSH", "WSSS", "WSRH", "WSRS"}; //to show the players which pieces are available for playing
		String [][] board = new String [4][4]; // initializing the game board
		
		// asking the player if they want to play an ongoing or new game
		if (startTheGame(console) == 2) {
			board = initializeExistingBoard(board); // taking the board from the file
			printBoard(board); // printing the taken board
			
			if(winCheckHor(board) || winCheckVer(board) ||  winCheckDiag (board)) { // if the last game played has ended, this initializes an empty board
				System.out.println("The game has already finished!");
				System.out.println();
				initializeEmptyBoard(board, remainings);
			}
		}
		else  { // if the player wants to start a new game, this initializes an empty board
			initializeEmptyBoard(board, remainings);
		}
		printRemaining (remainings); // prints the pieces that are available to choose and play
		int count = 1; // counts how many times each player has played
		while (count <= 8) {
		// player picks the piece, program places the piece
			String playersPiece = playersPick (console, remainings); // the piece that is picked by the player
			printBoard(programsPlacement(board, playersPiece)); // prints the board after the program places the piece
			
			if(winCheckHor(board) || winCheckVer(board) ||  winCheckDiag (board)) { // checks if the program has won
				System.out.println("The program won! ");
				break; // ends the game
			}
			
			remainings = changeRemainings (remainings, playersPiece); // deletes the last used piece from the array of available pieces
			printRemaining (remainings);
		
		// program picks, player places
			String programsPiece = programsPick(remainings); // the piece that is picked by the program
			System.out.println("Program's pick: " + programsPiece);
			printBoard(playersPlacement(console, board, programsPiece)); // prints the board after the player places the piece
			
			if(winCheckHor(board) || winCheckVer(board) || winCheckDiag (board)) { // checks if the player has won
				System.out.println("Player won! ");
				break;  // ends the game
			}
			
			remainings = changeRemainings (remainings, programsPiece); // deletes the last used piece from the array of available pieces
			printRemaining (remainings);
			
			count++;
			if (count > 8) { // when the board is full, prints if it is a tie
				System.out.println("It's a tie!");
			}
		}
	}
	
	public static boolean winCheckDiag (String[][] board) { // checks if there is a winning condition in either diagonal directions
		boolean check = true;  // symbolizes if the first diagonal has an empty place
		for(int i = 0; i < 4; i++) { // checks if the first diagonal have empty places
			if ((board[i][i]).equals("E")) {
				check = false; 
			}
		}
		for(int i = 0; i < 4; i++) { // checks if there is a winning condition in the first diagonal direction
			if (check && (board[0][0].charAt(i) == board[1][1].charAt(i)) && (board[1][1].charAt(i) == board[2][2].charAt(i)) && 
					(board[2][2].charAt(i) == board[3][3].charAt(i))) {
				return true; 
			}
		}
		
		//other diagonal direction check
		boolean check2 = true; // symbolizes if the second diagonal has an empty place
		for(int i = 0; i < 4; i++) { // checks if the second diagonal have empty places
			if ((board[i][3-i]).equals("E")) {
				check2 = false;
			}
		}
		for(int i = 0; i < 4; i++) { // checks if there is a winning condition in the second diagonal direction
			if (check2 && (board[0][3].charAt(i) == board[1][2].charAt(i)) && (board[1][2].charAt(i) == board[2][1].charAt(i)) && 
					(board[2][1].charAt(i) == board[3][0].charAt(i))) {
				return true;
			}
		}
		return false; // returns false if there is no winning condition in either of two diagonals
	}
	
	public static boolean winCheckCol (String[][] board, int col) { // checks if a column has a winning condition
		boolean check = true;
		for(int i = 0; i < 4; i++) { // makes the check boolean  false if the column has an empty piece
			if ((board[i][col]).equals("E")) { 
				check = false;
			}
		}
		
		for(int i = 0; i < 4; i++) { // returns true if the given column has a winning condition
			if (check && (board[0][col].charAt(i) == board[1][col].charAt(i)) && (board[1][col].charAt(i) == board[2][col].charAt(i)) && 
					(board[2][col].charAt(i) == board[3][col].charAt(i))) {
				return true;
			}
		}
		return false; // returns false if a column does not have a winning condition
	}
	
	
	public static boolean winCheckVer (String [][] board) { // checks and returns true if there is a winning condition in one of the columns
		for (int col = 0; col < 4; col++) { // checks every column if there is a winning condition
			if (winCheckCol(board, col)) {
				return true;
			}
		}
		return false; // returns false if there is no winning condition in any of the columns
	}
		

	
	public static boolean winCheckRow (String[][] board, int row) { // checks if a row has a winning condition
		boolean check = true;
		for(int i = 0; i < 4; i++) { // makes the check boolean false if the row has an empty piece
			if ((board[row][i]).equals("E")) {
				check = false;
			}
		}
		for(int i = 0; i < 4; i++) { // returns true if the given row has a winning condition
			if (check && (board[row][0].charAt(i) == board[row][1].charAt(i)) && (board[row][1].charAt(i) == board[row][2].charAt(i)) && 
					(board[row][2].charAt(i) == board[row][3].charAt(i))) {
				return true;
			}
		}
		return false; // returns false if a row does not have a winning condition
	}
	
	
	public static boolean winCheckHor (String[][] board) { // checks and returns true if there is a winning condition in one of the rows
		for (int row = 0; row < 4; row++) { // checks every row if there is a winning condition
			if (winCheckRow(board, row)) {
				return true;
			}
		}
		return false; // returns false if there is no winning condition in any of the rows
	}
	
	
	public static String[][] playersPlacement (Scanner console, String[][] board, String programsPick) { // makes the player choose a coordinate and prints it
		System.out.print("Player picks a coordinate: ");
		int row = console.nextInt(); // takes input that represents the x coordinate that the player has picked
		int col = console.nextInt(); // takes input that represents the y coordinate that the player has picked
		while (board[row][col] != "E") { // asks the user to pick again if the picked coordinate is not available
			System.out.print("Unavailable coordinates, pick again: ");
			row = console.nextInt();
			col = console.nextInt();
		}
		System.out.println();
		board[row][col] = programsPick; // updates the board using the piece and the coordinates
		return board; //returns the new version of the board
	}
	
	
	public static String programsPick (String[] remainings) { // makes the program pick a piece
		Random r = new Random (); // this is used to generate a number that is used for program to pick a piece
		int p = r.nextInt(16);
		while (remainings[p] == "----") { // makes program choose again when the picked piece is not available
			p = r.nextInt(16);
		}
		return remainings[p]; // returns the piece that the program has picked
	}
	
	
	public static String[][] programsPlacement (String[][] board, String playersPick) { // makes the program choose a coordinate and prints it
		Random r = new Random (); // this is used to generate a number that is used for program to pick a coordinate for the piece
		System.out.print("Program picks a coordinate: ");
		int row = r.nextInt(4); // represents the x coordinate that the program has picked
		int col = r.nextInt(4); // represents the y coordinate that the program has picked
		while (board [row][col] != "E") { // checks if the picked coordinate is available
			row = r.nextInt(4);
			col = r.nextInt(4);
		}
		System.out.println(row + ", " + col); // prints the coordinates that the program has picked
		System.out.println();
		
		board [row][col] = playersPick; // updates the board
		return board; // returns the updated board
	}
	
	
	public static boolean checkThePiece (String[] remainings, String playersPiece) { // checks if the piece that the player has chosen is an available configuration
		for (int i = 0; i < remainings.length; i++) {  
			if (playersPiece.equals(remainings[i])) { // compares the piece with the remaining pieces and returns true if the piece is available
				return true; 
			}
		}
		return false; // returns false if the piece is not available
	}
	
	
	public static void printRemaining (String[] remainings) throws FileNotFoundException { // prints remaining pieces
		System.out.print("Available pieces are: ");
		for (int i = 0; i < remainings.length; i++) {
			System.out.print(remainings[i] + " ");
		}
		System.out.println(); // these are written to make the game look more neat
		System.out.println();
	}
	
	
	public static String playersPick (Scanner console, String [] remainings) { // makes the player pick a piece and returns that piece
		System.out.print("Player's time to pick: ");
		String playersPiece = console.next();
		
		while (!checkThePiece(remainings, playersPiece)) { // checks if the chosen piece is avaailable
			System.out.print("Unavailable piece, please pick again: ");
			playersPiece = console.next();
			checkThePiece(remainings, playersPiece);
		}
		return playersPiece; // returns the piece that the player has picked
	}
	
	
	public static int startTheGame (Scanner console) { // initialization of the board and the start of the game
		System.out.print("Press 1 for new game, press 2 to continue to an ongoing game: "); // asks the player their preference about the game
		int answer = console.nextInt();
		
		while (answer != 1 && answer != 2) { // prompts the player to write a valid input
			System.out.print("Invalid input, please try again: ");
			answer = console.nextInt();
		}
		return answer; // returns the player's preference about the game
	}
	
	
	public static String[][] initializeExistingBoard (String [][] board) throws FileNotFoundException { // prints the saved game's board to the console
		Scanner input = new Scanner (new File ("input.txt")) ; // thakes the saved game's board from the file 
		
		for (int i = 0; i < 4; i++) {  // fills the board with tokens that are in the file
			for (int j = 0; j < 4; j++) {
				board [i][j] = input.next();
			}
		}
		input.close();
		return board;  // returns the saved board 
	}
	
	
	public static void initializeEmptyBoard (String [][] board, String[] remainings) { // creates and prints an empty board to the console
		for (int i = 0; i < 4; i++) { // creates an empty board
			for (int j = 0; j < 4; j++) {
				board [i][j] = "E";
			}
		}
		
		for (int i = 0; i < board.length; i++) { // prints an empty board
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	public static void printBoard (String[][] board) throws FileNotFoundException { // prints the board consequtively on both console and the file
		PrintStream str = new PrintStream (new File("input.txt")); // used for writing the ongoing game's board into a file named input.txt
		
		for (int i = 0; i < board.length; i++) { // prints the board on both console and the file
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " "); // prints the board on the console 
				str.print(board[i][j] + " "); // prints the board on the file
			}
			System.out.println();
			str.println();
		}
		str.close();
	}
	
	
	public static String[] changeRemainings (String[] remainings, String playersPiece ) { // updates the array that stores available pieces
		int count = 0; 
		for (int i = 0; i < remainings.length; i++) {  // finds the picked piece and changes it into "----"
			if (remainings[i].equals(playersPiece)) {
				count = i;
				remainings[count] = "----";
			}
		}
		return remainings; // returns the updated array that stores available pieces
	}

}
