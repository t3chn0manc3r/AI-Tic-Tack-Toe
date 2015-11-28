import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;

/** SmartBlockAI
 * 
 * SmartBruteAI, but with brute-force blocking.
 * This AI checks 1) if it can win, 2) if its opponent can win, 3) if it can win next turn
 * The offensive strategy is the same as SmartBrute's.
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

public class SmartBlockAI extends AI {
	
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
	ArrayList<Integer> myMoves;
	ArrayList<Integer> openMoves;
	ArrayList<Integer> enemyMoves;
	private char enemySide;

	//No parameter constructor, initialize name
	public SmartBlockAI(){
		this.name = "SmartBlockAI";
	}
	@Override
	public Point move(TTTBoard b) {
		enemySide = (side == 'x') ? 'o' : 'x';
		myMoves = new ArrayList<Integer>();
		openMoves = new ArrayList<Integer>();
		enemyMoves = new ArrayList<Integer>();
		
		// Read off the board
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				char space = b.checkSpace(new Point(i,j));
				if ( space == side )				// finds my moves
					myMoves.add( i + 3*j );
				else if ( space == enemySide )		// finds enemy moves
					enemyMoves.add( i + 3*j );
				else								// finds available moves
					openMoves.add( i + 3*j );
			}
		}
		
		boolean attack = true;	// switch for attack or defense
		int strat = -1;			// chosen strategy
		int N = 0;				// chosen point
		Point myMove;			// Point to return
		
		// This algorithm isn't very efficient, but it's adaptable
		if (myMoves.size() > 0)		// check for possible winning plays, if it isn't the first turn
		{
			int[] stratCount = new int[strategies.length];
			for (int i = 0; i < myMoves.size(); i++)				// look for strats containing my pieces
			{
				int prevMove = myMoves.get(i);
				for (int j = 0; j < stratCount.length; j++)
				{
					if (prevMove == strategies[j][0] || prevMove == strategies[j][1] || prevMove == strategies[j][2])
					{
						stratCount[j]++;
					}
				}
			}
			
			int[] stratECount = new int[strategies.length];
			for (int i = 0; i < enemyMoves.size(); i++)				// look for strats containing enemy pieces
			{
				int prevMove = enemyMoves.get(i);
				for (int j = 0; j < stratECount.length; j++)
				{
					if (prevMove == strategies[j][0] || prevMove == strategies[j][1] || prevMove == strategies[j][2])
					{
						stratECount[j]++;
					}
				}
			}
			
			ArrayList<Integer> strats2 = new ArrayList<Integer>();
			ArrayList<Integer> stratsE = new ArrayList<Integer>();
			ArrayList<Integer> strats1 = new ArrayList<Integer>();
			for (int k = 0; k < stratCount.length; k++)				// grab all of the win-this-turn and win-next-turn strats
			{
				if (stratCount[k] == 2)
					strats2.add(k);
				else if (stratCount[k] == 1)
					strats1.add(k);
			}
			
			for (int k = 0; k < stratECount.length; k++)			// grab all of the enemy win-this-turn strats
			{
				if (stratECount[k] == 2)
					stratsE.add(k);
			}
			
			// strategy selector
			for (int m = 0; m < strats2.size() && strat < 0; m++)
			{
				int tmpStrat = strats2.get(m);
				if (checkOpenStrat(tmpStrat))
					strat = tmpStrat;
			}
			for (int m = 0; m < stratsE.size() && strat < 0; m++)
			{
				int tmpStrat = stratsE.get(m);
				if (checkEnemyStrat(tmpStrat))
					strat = tmpStrat;
			}
			for (int m = 0; m < strats1.size() && strat < 0; m++)
			{
				int tmpStrat = strats1.get(m);
				if (checkOpenStrat(tmpStrat))
					strat = tmpStrat;
			}
			
			if (strat == -1)	// no strat chosen; pick a random open point
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
		}
		
		else		// my first turn
		{
			// picks space 0, or 8 if 0 is taken
			if(openMoves.contains(0))
				N = 0;
			else
				N = 8;
		}
		
		myMove = new Point(N % 3, N / 3);
		return myMove;
	}
	
	private boolean checkOpenStrat(int strat)
	{
		if (!enemyMoves.contains(strategies[strat][0])) {
			if (!enemyMoves.contains(strategies[strat][1])) {
				if (!enemyMoves.contains(strategies[strat][2])) {
					return true;		// no enemy pieces can be in the given strat to reach this condition
				}
			}
		}
		return false;					// enemy piece found
	}
	
	private boolean checkEnemyStrat(int strat)
	{
		if (!myMoves.contains(strategies[strat][0])) {
			if (!myMoves.contains(strategies[strat][1])) {
				if (!myMoves.contains(strategies[strat][2])) {
					return true;		// no friendly pieces can be in the given strat to reach this condition
				}
			}
		}
		return false;					// my piece found
	}

}
