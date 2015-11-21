import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;

/** BruteForceAI
 * 
 * A tic-tac-toe AI that simply tries to get its way. 
 * It looks through its strategies for the first possible way to win, and goes for it.
 * It's not very smart, though.
 * 
 * @author Jacob Rardin/theInternalError
 *
 */

/*	Board Numbering:
 * 		0,0 | 1,0 | 2,0
 * 		---------------
 * 		0,1 | 1,1 | 2,1
 * 		---------------
 *		0,2 | 1,2 | 2,2
 *
 *		0 | 1 | 2
 * 		---------
 * 		3 | 4 | 5
 * 		---------
 *		6 | 7 | 8
 *
 *		coords to indices: N = x + 3y
 *		indices to coords: X = N % 3, Y = N / 3
 */

public class BruteForceAI extends AI {
	
	private final int[][] strategies = {
			{0,1,2},	// 0 Top row
			{3,4,5},	// 1 Mid row
			{6,7,8},	// 2 Bot row
			{0,3,6},	// 3 Left col
			{1,4,7},	// 4 Mid col
			{2,5,8},	// 5 Right row
			{0,4,8},	// 6 Down diag
			{2,4,6}		// 7 Up diag
						// 8 Random space
	};

	//No parameter constructor, initialize name
	public BruteForceAI(){
		this.name = "BruteForceAI";
	}
	@Override
	public Point move(TTTBoard b) {
		//TTTBoard c = b;
		ArrayList<Integer> myMoves = new ArrayList<Integer>();
		ArrayList<Integer> openMoves = new ArrayList<Integer>();
		
		// Read off the board
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				char space = b.checkSpace(new Point(i,j));
				if ( space == side )				// finds my moves
					myMoves.add( i + 3*j );
				if ( space == ' ' )					// finds available moves
					openMoves.add( i + 3*j );
			}
		}
		
		int strat = -1;		// chosen strategy
		//int k = 0;
		for (int k = 0; strat < 0 && k < 9; k++)
		//while (strat < 0 && k < 9)	// checks through each strategy to see if it works
		{
			if (k == 8)
				strat = 8;
			else if (myMoves.contains(strategies[k][0]) || openMoves.contains(strategies[k][0]))
			{
				if (myMoves.contains(strategies[k][1]) || openMoves.contains(strategies[k][1]))
				{
					if (myMoves.contains(strategies[k][2]) || openMoves.contains(strategies[k][2])) { strat = k; }
				}		
			}
		}
		
		Point myMove;
		int N = 0;
		if (strat == 8)
		{
			Random r = new Random();
			N = openMoves.get(r.nextInt(openMoves.size()));
		}
		else
		{
			int i = 0;
			N = strategies[strat][i];
			while (!openMoves.contains(N) && i < 2)
			{
				N = strategies[strat][++i];
			}
		}
		myMove = new Point(N % 3, N / 3);
		return myMove;
	}

}
