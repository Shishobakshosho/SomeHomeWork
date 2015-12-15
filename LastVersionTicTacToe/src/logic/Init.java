package logic;

import common.ServerResources;
import common.Synchro;
import view.TicTacToeGUI;

public class Init {

	public static void main(String[] args) {
		Synchro syn = new Synchro();
		ServerResources sr = new ServerResources();
		new TicTacToeGUI(syn,sr);
	}

}
