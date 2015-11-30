import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TTTPanel extends JPanel {
	private ImageIcon x, o, board;
	private Image[][] storage;
	private String winner;
	public TTTPanel(){
		setBackground(Color.GREEN.darker());
		storage = new Image[3][3];
		x = null;
		o = null;
		board = null;
		try{
			String folder = new File( "." ).getCanonicalPath();
			String sys = System.getProperty("os.name").toLowerCase();
			if(sys.contains("windows")){
				folder += "\\TTTSprites\\";
			}
			else{
				folder += "/TTTSprites/";
			}
			board = new ImageIcon(folder+"board.png");
			x = new ImageIcon(folder+"x.png");
			o = new ImageIcon(folder+"o.png");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void updateBoard(Point p, char s){
		if(p.x < 3 && p.x >=0 && p.y < 3 && p.y >=0){
			if(s == 'x'){
				storage[p.x][p.y] = x.getImage();
			}
			else{
				storage[p.x][p.y] = o.getImage();
			}
		}
		repaint();
	}
	public void displayWinner(String s){
		winner = s;
		repaint();
	}
	public void resetBoard(){
		storage = new Image[3][3];
		winner = null;
		repaint();
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(board.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(storage[0][0], 0, 0, (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[1][0], (int)(this.getWidth()*(340.0/980)), 0, (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[2][0], (int)(this.getWidth()*(680.0/980)), 0, (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
	
		g.drawImage(storage[0][1], 0, (int)(this.getHeight()*(340.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[1][1], (int)(this.getWidth()*(340.0/980)), (int)(this.getHeight()*(340.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[2][1], (int)(this.getWidth()*(680.0/980)), (int)(this.getHeight()*(340.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
	
		g.drawImage(storage[0][2], 0, (int)(this.getHeight()*(680.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[1][2], (int)(this.getWidth()*(340.0/980)), (int)(this.getHeight()*(680.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
		g.drawImage(storage[2][2], (int)(this.getWidth()*(680.0/980)), (int)(this.getHeight()*(680.0/980)), (int)(this.getWidth()*(300.0/980)), (int)(this.getHeight()*(300.0/980)),this);
	
		if(winner != null){
			String sentence = winner.toUpperCase()+" IS THE WINNER!";
			if(winner.equals("Tie")){
				sentence = "TIE GAME!";
			}
			g.setFont(new Font("Serif",Font.BOLD,5));
			int init = 5;
			while(g.getFontMetrics().stringWidth(sentence) < getWidth() - (getWidth()/8)){
				init += 5;
				g.setFont(new Font("SansSerif",Font.BOLD,init));
			}
			g.setColor(Color.CYAN);
			g.drawString(sentence.toUpperCase(), (getWidth() - g.getFontMetrics().stringWidth(sentence))/2, getHeight()/2 + (g.getFontMetrics().getHeight())/4);
		}
	}
}