package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import common.ServerResources;
import common.Synchro;
import view.Board;
import common.Synchro.won;

public class Server extends Thread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	Synchro syn;
	ServerResources sr;

	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	Board board;
	JTextArea messages;
	private ServerSocket serverSocket;

	public Server(Synchro syn, ServerResources sr, Board board, JTextArea messages) {
		this.syn = syn;
		this.messages = messages;
		this.serverSocket = sr.getServerSocket();
		this.board = board;
		this.start();
	}

	public void run() {
		try {
			messages.setText("Waiting for oppenent");
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			messages.setText("Oppenent has connected");
		} catch (IOException e) {
			messages.setText("Can't connect with client");
			return;
		}

		syn.setGame(true);

		syn.setPlayerOneTurn(true);
		messages.setText("Your turn");
		while (syn.isGame() && !syn.isUnableToCommunicateWithOpponent()) {
			
			while (syn.isPlayerOneTurn() == true)
				;
			if(!syn.isGame()){
				messages.setText("You win");
			}else{
				messages.setText("Opponent's turn");
			}
			
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
			for (int i = 0; i < 10; i++) {
				try {
					int cell = dis.readInt();
					if (syn.getBoardSymbol(cell) != '\0') {
						if (i == 9) {
							throw new IOException();
						}
						continue;
					}
					messages.setText("Your turn");
					syn.setBoardSymbol(cell, 'O');
					board.setButton(cell, 'O');
					syn.updateState('O');
					if (syn.getGameWinStatus() == won.playerTwoWon) {
						messages.setText("Opponent wins");
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
		}
	}
}
