package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import common.Synchro;
import common.Synchro.won;

class Message extends JPanel{
	Synchro syn;
	private int lengthOfSpace = 160;//the size of the square fields
	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font smallerFont = new Font("Verdana", Font.BOLD, 20);
	private Font largerFont = new Font("Verdana", Font.BOLD, 50);
	
	private String waitingString = "Waiting for another player";
	private String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";
	private String wonString = "You won!";
	private String enemyWonString = "Opponent won!";
	private String tieString = "Game ended in a tie.";
	private static final long serialVersionUID = 1L;
 
	public Message(Synchro syn) {
		this.setSize(500,100);
		setFocusable(true);
		this.setSize(500,100);
		requestFocus();
		this.setSize(500,100);
		setBackground(Color.WHITE);
		this.setSize(500,100);
		this.syn = syn;
	}
  
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}

    
private void render(Graphics g) {
		
		if (syn.isUnableToCommunicateWithOpponent()) {
			g.setColor(Color.RED);
			g.setFont(smallerFont);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(unableToCommunicateWithOpponentString);
			g.drawString(unableToCommunicateWithOpponentString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			return;
		}

		if (syn.isGame()) {
			
			if (syn.getGameWinStatus() == won.playerOneWon || syn.getGameWinStatus() == won.playerTwoWon) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				g.setColor(Color.BLACK);
				g.drawLine(syn.getFirstSpot() % 3 * syn.getFirstSpot() + 10 * syn.getFirstSpot() % 3 +
						  lengthOfSpace / 2, (int) (syn.getFirstSpot()/ 3) * lengthOfSpace + 10 * (int) (syn.getFirstSpot() / 3) + 
						  lengthOfSpace / 2, syn.getSecondSpot() % 3 * lengthOfSpace + 10 * syn.getSecondSpot() % 3 + 
						  lengthOfSpace / 2, (int) (syn.getSecondSpot() / 3) * lengthOfSpace + 10 * (int) (syn.getSecondSpot() / 3) + lengthOfSpace / 2);

				g.setColor(Color.RED);
				g.setFont(largerFont);
				if (syn.getGameWinStatus() == won.playerOneWon) {
					int stringWidth = g2.getFontMetrics().stringWidth(wonString);
					g.drawString(wonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
				} else if (syn.getGameWinStatus() == won.playerTwoWon) {
					int stringWidth = g2.getFontMetrics().stringWidth(enemyWonString);
					g.drawString(enemyWonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
				}
			}
			if (syn.getGameWinStatus() == won.tie) {
				Graphics2D g2 = (Graphics2D) g;
				g.setColor(Color.BLACK);
				g.setFont(largerFont);
				int stringWidth = g2.getFontMetrics().stringWidth(tieString);
				g.drawString(tieString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			}
		} else {
			g.setColor(Color.RED);
			g.setFont(font);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int stringWidth = g2.getFontMetrics().stringWidth(waitingString);
			g.drawString(waitingString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
		}

	}

}
