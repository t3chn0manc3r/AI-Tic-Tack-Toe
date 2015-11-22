import java.awt.Point;
import java.util.ArrayList;

public class TheronAI extends AI {

	private int turn;
	
	public TheronAI(){
		name = "Theron";
		turn = 1;
	}
	
	@Override
	Point move(TTTBoard b) {
		//Take top left corner or middle for first move
		if(turn == 1){
			turn++;
			if(side == 'o'){
				if(b.checkSpace(new Point(1,1))==' '){
					return new Point(1,1);
				}
				else{
					return new Point(0,0);
				}
			}
			else{
				return new Point(0,0);
			}
		}
		
		//Set up two part win
		if(turn == 2){
			turn++;
			if(side == 'x'){
				if(b.checkSpace(new Point(2,2)) == ' '){
					return new Point(2,2);
				}
				else{
					return new Point(2,0);
				}
			}
			else{
				if(b.checkSpace(new Point(0,0)) == 'x' && b.checkSpace(new Point(2,2))== 'x'){
					return new Point(0,1);
				}
				if(b.checkSpace(new Point(2,0)) == 'x' && b.checkSpace(new Point(0,2))== 'x'){
					return new Point(0,1);
				}
			}
		}
		
		
		//Check if I can win
		System.out.println("Theron - \"Can I win?\"");
		ArrayList<Point> wins = checkForWin(side, b);
		if(wins.size() > 0){
			System.out.println("Theron - \"Yes, I can win!\"");
			return wins.get(0);
		}
		
		//Check if opponent can win to block
		System.out.println("Theron - \"So I can't win... Do I need to block?\"");
		char toDef;
		if(side == 'x') toDef = 'o';
		else toDef = 'x';
		ArrayList<Point> def = checkForWin(toDef,b);
		if(def.size() > 0){
			System.out.println("Theron - \"My opponent can win! I need to block...\"");
			return def.get(0);
		}
		
		//Try to get me to have the most wins
		System.out.println("Theron - \"There are no urgent moves to make. So what can give me the most potential wins?\"");
		ArrayList<Point> most = new ArrayList<Point>(),test;
		Point winning = new Point();
		TTTBoard copy;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(b.checkSpace(new Point(i,j)) == ' '){
					copy = b.getBoardCopy();
					copy.place(side, new Point(i,j));
					test = checkForWin(side,copy);
					if(test.size() > most.size()){
						most = test;
						winning = new Point(i,j);
					}
				}
			}
		}
		if(most.size() != 0) return winning;
		
		//idk what im doing with this statement......
		System.out.println("Theron - \"Well... This is already a tie game... GG!\"");
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(b.checkSpace(new Point(i,j)) == ' '){
					return new Point(i,j);
				}
			}
		}
		return new Point(-1,-1);
	}
	
	private ArrayList<Point> checkForWin(char symb, TTTBoard board){
		ArrayList<Point> possibleWins = new ArrayList<Point>();
		int sideCount, emptyCount;
		//vertical
		for(int i = 0; i < 3; i++){
			sideCount = 0;
			emptyCount = 0;
			char one = board.checkSpace(new Point(i,0));
			char two = board.checkSpace(new Point(i,1));
			char three = board.checkSpace(new Point(i,2));
			if(one == symb) sideCount++;
			else if(one == ' ') emptyCount++;
			
			if(two == symb) sideCount++;
			else if(two == ' ') emptyCount++;
			
			if(three == symb) sideCount++;
			else if(three == ' ') emptyCount++;
		
			if(sideCount == 2 && emptyCount == 1){
				if(one == ' ') possibleWins.add(new Point(i,0));
				else if(two == ' ') possibleWins.add(new Point(i,1));
				else if(three == ' ') possibleWins.add(new Point(i,2));
			}
		}
		
		//horizontal
		for(int i = 0; i < 3; i++){
			sideCount = 0;
			emptyCount = 0;
			char one = board.checkSpace(new Point(0,i));
			char two = board.checkSpace(new Point(1,i));
			char three = board.checkSpace(new Point(2,i));
			if(one == symb) sideCount++;
			else if(one == ' ') emptyCount++;
			
			if(two == symb) sideCount++;
			else if(two == ' ') emptyCount++;
			
			if(three == symb) sideCount++;
			else if(three == ' ') emptyCount++;
		
			if(sideCount == 2 && emptyCount == 1){
				if(one == ' ') possibleWins.add(new Point(0,i));
				else if(two == ' ') possibleWins.add(new Point(1,i));
				else if(three == ' ') possibleWins.add(new Point(2,i));
			}
		}
		//diagonal
		sideCount = 0;
		emptyCount = 0;
		char tl = board.checkSpace(new Point(0,0));
		char tr = board.checkSpace(new Point(2,0));
		char m = board.checkSpace(new Point(1,1));
		char br = board.checkSpace(new Point(2,2));
		char bl = board.checkSpace(new Point(0,2));
		
		if(tl == symb) sideCount++;
		else if(tl == ' ') emptyCount++;
		
		if(m == symb) sideCount++;
		else if(m == ' ') emptyCount++;
		
		if(br == symb) sideCount++;
		else if(br == ' ') emptyCount++;
		
		if(sideCount == 2 && emptyCount == 1){
			if(tl == ' ') possibleWins.add(new Point(0,0));
			else if(m == ' ') possibleWins.add(new Point(1,1));
			else if(br == ' ') possibleWins.add(new Point(2,2));
		}
		
		sideCount = 0;
		emptyCount = 0;
		
		if(tr == symb) sideCount++;
		else if(tr == ' ') emptyCount++;
		
		if(m == symb) sideCount++;
		else if(m == ' ') emptyCount++;
		
		if(bl == symb) sideCount++;
		else if(bl == ' ') emptyCount++;
		
		if(sideCount == 2 && emptyCount == 1){
			if(tr == ' ') possibleWins.add(new Point(2,0));
			else if(m == ' ') possibleWins.add(new Point(1,1));
			else if(bl == ' ') possibleWins.add(new Point(0,2));
		}
		return possibleWins;
	}

}
