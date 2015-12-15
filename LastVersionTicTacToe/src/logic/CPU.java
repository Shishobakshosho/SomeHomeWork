package logic;

import javax.swing.JTextArea;

import common.Synchro;
import view.Board;

public class CPU extends Thread {
	Synchro syn;
	Board board;
	JTextArea messages;
	
	public CPU(Synchro syn,Board board,JTextArea messages){
		this.board = board;
		this.syn = syn;
		this.messages = messages;
		syn.setGame(true);
		this.start();
	}
	
	public void run(){
		int cpuMove = -1;
		messages.setText("Single Play");
		syn.setPlayerOneTurn(true);
		while(syn.isGame()){

			while(syn.isPlayerOneTurn());
			cpuMove = computerMove();
			if(cpuMove < 0){
				syn.setGame(false);
				messages.setText("Game Tie");
				return;
			}
			else{
				syn.setBoardSymbol(cpuMove, 'O');
				board.setButton(cpuMove, 'O');
			}
			
			switch (syn.getGameWinStatus()) {
			case playerOneWon:
				messages.setText("You Win");
				syn.setHotSeat(false);
				syn.setGame(false);
				break;
			case playerTwoWon:
				messages.setText("CPU wins");
				syn.setGame(false);
				break;
			case tie:
				messages.setText("Game Tie");
				syn.setGame(false);
				break;
			default:
				syn.switchPlayers();
				break;
			}
			
			
			
		}
	}
	
	public int computerMove() {
		int score = -2;
		int move = -1;
	    for(int i = 0; i < 9; ++i) {
	        if(syn.getBoardSymbol(i) == '\0') {
	        	syn.setBoardSymbol(i,'O');
	            int tempScore = -minimax(-1);
	            syn.setBoardSymbol(i, '\0');
	            if(tempScore > score) {
	                score = tempScore;
	                move = i;
	            }
	        }
	    }
	    //returns a score based on minimax tree at a given node.
	   return move;
	}
	
	
	
	private int minimax(int player) {
		
	    switch (syn.getCurrStateNoUpdate(player < 0 ? 'O': 'X')) {
		case playerOneWon:
             return -100;
		case playerTwoWon:
             return 100;
		}
	    
        int move = -1;
	    int score = -2;//Losing moves are preferred to no move
	    int i;
	    for(i = 0; i < 9; ++i) {//For all moves,
	        if(syn.getBoardSymbol(i) == 0) {//If legal,
	        	syn.setBoardSymbol(i, player < 0 ? 'X': 'O');
	            int thisScore = -minimax(-player);
	            if(thisScore > score) {
	                score = thisScore;
	                move = i;
	            }//Pick the one that's worst for the opponent
	            syn.setBoardSymbol(i, '\0');
	        }
	    }
        if(move < 0) {return 0;}
	    return score;
	}
}
