package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

import common.ServerResources;
import common.Synchro;
import common.Synchro.won;
import view.Board;


public class Client extends Thread {
	Synchro syn;
	ServerResources sr;
	Board board;
	Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	JTextArea messages;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Client(Synchro syn, ServerResources sr, Board board,JTextArea messages) {
		this.syn = syn;
		this.sr = sr;
		this.board = board;
		this.messages = messages;
		this.start();		
	}
    
	public void run(){
		try {
			socket = new Socket(sr.getIp(), sr.getPort());
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			messages.setText("Connection  established");
		} catch (IOException e) {
			messages.setText(
					"Unable to connect to the address: " + sr.getIp() + ":" + sr.getPort() + " | Starting a server");
			return;
		}
		
		syn.setGame(true);
		syn.setPlayerTwoTurn(true);
		messages.setText("Opponent's turn");
		while (syn.isGame() && !syn.isUnableToCommunicateWithOpponent()) {
			for (int i = 0; i < 10; i++) {
				try {
					int cell = dis.readInt();
					if (syn.getBoardSymbol(cell) != '\0') {
						if (i == 9) {
							throw new IOException();
						}
						continue;
					}
					syn.setBoardSymbol(cell, 'O');
					board.setButton(cell, 'O');
					syn.updateState('O');
					if (syn.getGameWinStatus() == won.playerTwoWon) {
						messages.setText("Opponent win");
						syn.setGame(false);
						return;
					} else if (syn.getGameWinStatus() == won.tie) {
						messages.setText("Game Tie");
						syn.setGame(false);
						return;
					}
					syn.switchPlayers();
					break;
				} catch (IOException e) {
					if (i == 9) {
						messages.setText("Unable To Communicate With Opponent");
						syn.setUnableToCommunicateWithOpponent(true);
						syn.setGame(false);
					}
				}
			}
			messages.setText("Your turn");
	
			while(syn.isPlayerOneTurn());
			messages.setText("Opponent's turn");
			
			for (int i = 0; i < 10; i++) {
				try {
					dos.writeInt(syn.getSetCell());
					break;
				} catch (IOException e) {
					if (i == 9) {
						messages.setText("Unable To Communicate With Opponent");
						syn.setUnableToCommunicateWithOpponent(true);
						syn.setGame(false);
					}
				}
			}
			
		}
	}
}
