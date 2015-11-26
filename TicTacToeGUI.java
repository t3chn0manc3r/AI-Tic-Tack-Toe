import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class TicTacToeGUI{
	private JFrame frame;
	private JPanel optionPanel;
	public JComboBox boxOne, boxTwo;
	public JButton play;
	public volatile boolean go; 
	public TTTPanel panel;
	public TicTacToeGUI(){
		//Setting up frame
		frame = new JFrame("Tic-Tac-Toe");
		frame.setLayout(new BorderLayout());
		BufferedImage icon = null;
		try{
			String current = new File( "." ).getCanonicalPath()+"\\TTTSprites\\icon.png";
			File iconFile = new File(current);
			icon = ImageIO.read(iconFile);
		}
		catch(Exception e){
			System.out.println(e);
		}
		frame.setIconImage(icon);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setting up the options panel
		optionPanel = new JPanel();
		optionPanel.add(new JLabel("X : "));
		String[] list = {"BruteForceAI","Human","MinimaxAI","TestAI","TheronAI","TylerAI"};
		boxOne = new JComboBox(list);
		optionPanel.add(boxOne);
		optionPanel.add(new JLabel("O : "));
		boxTwo = new JComboBox(list);
		optionPanel.add(boxTwo);
		play = new JButton("Play");
		go = false;
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				go = true;
				play.setEnabled(false);
				boxOne.setEnabled(false);
				boxTwo.setEnabled(false);
			}
		});
		//add a listener
		optionPanel.add(play);
		frame.add(optionPanel, BorderLayout.PAGE_START);
		
		//Setting up the board graphics
		panel = new TTTPanel();
		frame.add(panel, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		TicTacToeGUI gui = new TicTacToeGUI();
		
		AI one, two;
		while(true){
			if(gui.go){
				//Start off with a clean board
				gui.panel.resetBoard();
				
				//Initializing AI
				String aiOne = (String) gui.boxOne.getSelectedItem();
				String aiTwo = (String) gui.boxTwo.getSelectedItem();
				switch(aiOne){
					case "TheronAI":
						one = new TheronAI();
						break;
					case "MinimaxAI":
						one = new MinimaxAI();
						break;
					case "BruteForceAI":
						one = new BruteForceAI();
						break;
					case "Human":
						one = new Human();
						break;
					case "TylerAI":
						one = new TylerAI();
						break;
					case "TestAI":
						one = new TestAI();
						break;
					default:
						one = new TestAI();
				};
				one.setSide('x');
				
				switch(aiTwo){
					case "TheronAI":
						two = new TheronAI();
						break;
					case "MinimaxAI":
						two = new MinimaxAI();
						break;
					case "BruteForceAI":
						two = new BruteForceAI();
						break;
					case "Human":
						two = new Human();
						break;
					case "TylerAI":
						two = new TylerAI();
						break;
					case "TestAI":
						two = new TestAI();
						break;
					default:
						two = new TestAI();
				};
				two.setSide('o');
				
				//Initialize data in board
				TTTBoard board = new TTTBoard();
				
				boolean turn = true;
				
				//game cycle
				boolean won = false;
				while(!won){
					if(turn){
						Point move = one.move(board.getBoardCopy());
						board.place(one.getSide(),move);
						gui.panel.updateBoard(move, one.getSide());
						turn = !turn;
					}
					else{
						Point move = two.move(board.getBoardCopy());
						board.place(two.getSide(),move);
						gui.panel.updateBoard(move, two.getSide());
						turn = !turn;
					}
					try{Thread.sleep(1000);}catch(Exception e){}
					char check = board.winCheck();
					won = check != '-' || check == 't';
				}
				char winner = board.winCheck();
				if(one.getSide() == winner){
					gui.panel.displayWinner(one.getName());
				}
				else if(two.getSide() == winner){
					gui.panel.displayWinner(two.getName());
				}
				else if(winner == 't'){
					gui.panel.displayWinner("Tie");
				}
				else{
					gui.panel.displayWinner("Wat?");
				}
				
				gui.play.setEnabled(true);
				gui.boxOne.setEnabled(true);
				gui.boxTwo.setEnabled(true);
				gui.go = false;
			}
	
		}

	}
}