import java.awt.Point;
import java.util.Scanner;

/** Human
 * 
 * Not an AI, but an interface to allow a human to play against an AI (or another human)
 * 
 * @author Jacob Rardin/theInternalError
 *
 */

/*	Board Numbering
 *		0 | 1 | 2
 * 		---------
 * 		3 | 4 | 5
 * 		---------
 *		6 | 7 | 8
 */

public class Human extends AI {
	
	//No parameter constructor, initialize name
	public Human(){
		this.name = "Human";
	}
	@Override
	Point move(TTTBoard b) {
		int N = 0;
		String input;
		Scanner in = new Scanner(System.in);
	    System.out.println("What is your move?");
	    input = in.nextLine();
		N = Integer.parseInt(input);
		return new Point(N % 3, N / 3);
		
	}

}
