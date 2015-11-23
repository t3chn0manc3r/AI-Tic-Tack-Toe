/*
 * Initialize AI's same as TickTackToe.java ...Just now realized that I misspelled tic-tac-toe....
 */

import java.awt.Point;
import java.util.Scanner;

public class QuickComparison {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		//Get names of AI's
		System.out.println("Name of AI 1: ");
		String nameOne = scan.nextLine();
		nameOne = nameOne.trim();
		System.out.println("Name of AI 2: ");
		String nameTwo = scan.nextLine();
		
		//Get number of matches
		System.out.println("How many matches would you like to run?");
		int matches = scan.nextInt();
		scan.nextLine();
		
		System.out.println("Would you like the board printed for each (m)ove, (g)ame, or (n)ot at all?");
		String resp = scan.nextLine().toLowerCase().trim();
		char opt = resp.charAt(0);
		
		scan.close();
		
		System.out.println("\nInitializing...");
		
		//Setup printing
		boolean printMove = opt == 'm', printGame = opt == 'g';
		
		//Initialize the AI's
		AI one,two;
		try{
			one = (AI) (Class.forName(nameOne).newInstance());
		}
		catch(Exception e){
			System.out.println("Could not load AI one.");
			scan.close();
			return;
		}
		nameTwo = nameTwo.trim();
		try{
			two = (AI) (Class.forName(nameTwo).newInstance());
		}
		catch(Exception e){
			System.out.println("Could not load AI two.");
			return;
		}
		
		System.out.println("Success!\n");
		
		int oneWins = 0, twoWins = 0, ties = 0, oneFirst = 0, twoFirst = 0, oneBreak = 0, twoBreak = 0;
		boolean lastTurn = false;
		boolean turn = false;
		for(int match = 0; match < matches; match++){	
			
			TTTBoard board = new TTTBoard();
			
			//Find out who goes first this match based on last match
			if(!lastTurn){
				one.setSide('x');
				two.setSide('o');
				turn = true;
				oneFirst++;
			}
			else{
				one.setSide('o');
				two.setSide('x');
				turn = false;
				twoFirst++;
			}
			
			lastTurn = !lastTurn;
			
			//game cycle
			boolean won = false;
			while(!won){
				boolean goodPlace = false;
				int badAttempts = 0;
				if(turn){
					if(printMove) System.out.println(one.getName()+"'s Turn...");
					while(!goodPlace){
						Point move = one.move(board.getBoardCopy());
						goodPlace = board.place(one.getSide(),move);
						if(!goodPlace){
							badAttempts++;
						}
						if(badAttempts >= 10){
							goodPlace = true;
						}
					}
					turn = !turn;
				}
				else{
					if(printMove) System.out.println(two.getName()+"'s Turn...");
					while(!goodPlace){
						Point move = two.move(board.getBoardCopy());
						goodPlace = board.place(two.getSide(),move);
						if(!goodPlace){
							badAttempts++;
						}
						if(badAttempts >= 10){
							goodPlace = true;
						}
					}
					turn = !turn;
				}
				char check = board.winCheck();
				if(printMove){
					board.printBoard();
					System.out.println();
				}
				won = check != '-' || check == 't' || badAttempts >= 10;
			}
			char winner = board.winCheck();
			if(winner == 'x'){
				if(one.getSide() == 'x'){
					oneWins++;
					if(printGame || printMove){
						System.out.println(one.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
				else{
					twoWins++;
					if(printGame || printMove){
						System.out.println(two.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
			}
			else if(winner == 'o'){
				if(one.getSide() == 'o'){
					oneWins++;
					if(printGame || printMove){
						System.out.println(one.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
				else{
					twoWins++;
					if(printGame || printMove){
						System.out.println(two.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
			}
			else if(winner == 't'){
				ties++;
				if(printGame || printMove){
					System.out.println("Tie for Game #"+(match+1)+"!");
					board.printBoard();
					System.out.println();
				}
			}
			else{
				//AI broke down, award to non broken AI
				if(turn){
					oneWins++;
					twoBreak++;
					if(printGame || printMove){
						System.out.println(two.getName()+" Broke! "+one.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
				else{
					twoWins++;
					oneBreak++;
					if(printGame || printMove){
						System.out.println(one.getName()+" Broke! "+two.getName()+" Wins Game #"+(match+1)+"!");
						board.printBoard();
						System.out.println();
					}
				}
			}
		}
		System.out.println("\nStats\n----------------------------------------------------------------");
		System.out.println(one.getName()+":");
		System.out.println("\tWon "+oneWins+" times.");
		System.out.println("\tWent first "+oneFirst+" times.");
		System.out.println("\tBroke down "+oneBreak+" times.\n");
		System.out.println(two.getName()+":");
		System.out.println("\tWon "+twoWins+" times.");
		System.out.println("\tWent first "+twoFirst+" times.");
		System.out.println("\tBroke down "+twoBreak+" times.\n");
		System.out.println("There were "+ties+" ties.");
	}
}