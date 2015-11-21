/*
 *	Board Numbering:
 * 		0,0 | 1,0 | 2,0
 * 		---------------
 * 		0,1 | 1,1 | 2,1
 * 		---------------
 *		0,2 | 1,2 | 2,2
 */

import java.awt.Point;

public class TTTBoard {
	
	private char[][] board;
	
	public TTTBoard(){
		board = new char[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				board[i][j] = ' ';
			}
		}
	}
	/*
	 * Checks to see what is held at the point in the board.
	 */
	public char checkSpace(Point p){
		if(p.x < 3 && p.x >= 0 && p.y >= 0 && p.y < 3)
			return board[p.x][p.y];
		else return '-';
	}
	/*
	 * Returns a copy of the board to mutilate at will.
	 */
	public TTTBoard getBoardCopy(){
		TTTBoard copy = new TTTBoard();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				copy.place(board[i][j], new Point(i,j));
			}
		}
		return copy;
	}
	/*
	 * Places a value at the point p.
	 */
	public boolean place(char side, Point p){
		int x = p.x, y =p.y;
		if(x < 3 && x >=0 && y < 3 && y >= 0){
			if(board[x][y] == ' '){
				board[x][y] = side;
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	/*
	 * Returns a char representing the winner. Ex: 'x' or 'o'. If no winner returns '-'
	 */
	public char winCheck(){
		//vertical
		for(int i = 0; i < 3; i++){
			if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
				if(board[i][0] != ' ')
					return board[i][0]; //true
			}
		}
		//horizontal
		for(int i = 0; i < 3; i++){
			if(board[0][i] == board[1][i] && board[1][i] == board[2][i]){
				if(board[0][i] != ' ')
					return board[0][i]; //true
			}
		}
		//diagonal
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' '){
			return board[0][0]; //true
		}
		if(board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != ' '){
			return board[2][0]; //true
		}
		
		boolean emptySpace = false;
		for(int i = 0; i < 3 && !emptySpace; i++){
			for(int j = 0; j < 3 && !emptySpace; j++){
				if(board[i][j] == ' ') emptySpace = true;
			}
		}
		//nothing worked
		if(emptySpace)
			return '-'; //false
		else
			return 't';
	}
	/*
	 * Print out the board to the screen
	 */
	public void printBoard(){
		for(int j = 0; j < 3; j++){
			System.out.print(" ");
			for(int i = 0; i < 3; i++){
				System.out.print(board[i][j]);
				if(i != 2) System.out.print(" | ");
				else System.out.println();
			}
			if(j != 2) System.out.println("-----------");
		}
	}
}