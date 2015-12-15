package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.ServerResources;
import common.Synchro;
import logic.CPU;
import logic.Client;
import logic.Server;

public class Menu extends JPanel {// implements MouseListener  {
        JButton host;
        JButton singlePlay;
        JButton hotSeat;
        JLabel ip;
        JTextField ipAddress;
        JLabel port;
        JTextField hostPort;
        JButton connect;
        Synchro syn;
        ServerResources sr;
        Board board;
        JTextArea messages;
	public Menu(Synchro syn,ServerResources sr,Board board,JTextArea messages){
        this.syn = syn;
        this.sr = sr;
        this.board = board;
        this.messages = messages;
        
		this.setLayout(new BorderLayout());
		
		Box hBoxUp = Box.createHorizontalBox();
		hBoxUp.add(ip = new JLabel("IP"));
		hBoxUp.add(ipAddress = new JTextField("localhost"));
		hBoxUp.add(port = new JLabel("Port"));
		hBoxUp.add(hostPort = new JTextField("1025"));
		hBoxUp.add(connect = new JButton("Connect"));
		
		Box hBoxDown = Box.createHorizontalBox();
		hBoxDown.add(host = new JButton("Host"));
		hBoxDown.add(Box.createHorizontalGlue());
		hBoxDown.add(singlePlay = new JButton("Single play"));
		hBoxDown.add(Box.createHorizontalGlue());
		hBoxDown.add(hotSeat =new JButton("Hot seat"));
		
		this.add(hBoxUp,BorderLayout.NORTH);
		this.add(hBoxDown,BorderLayout.SOUTH);
		
		hotSeat.addActionListener(new ActionListener(){ 			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				syn.setHotSeat(true);
				syn.setPlayerOneTurn(true);
				messages.setText("Player One Turn");
			}
		});
		
		host.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				//String ip = sr.getCurrentIp();
				String ip = ipAddress.getText();
				//ipAddress.setText(ip);
				sr.setIp(ip);
				int port = 1025;
				System.out.println(sr.getIp()+" "+sr.getPort());
				for (; port < 65355; port++) {
					if(sr.initializeServer(port)){
						
						hostPort.setText(Integer.toString(port));
						
						System.out.println(hostPort.getText());
						break;
					}
				}			
				Server server = new Server(syn,sr,board,messages);
			}
		});
		
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				String ip = ipAddress.getText();
				sr.setIp(ip);
				int port = Integer.parseInt(hostPort.getText());
				sr.setPort(port);
				Client client = new Client(syn,sr,board,messages);
			}
		});
		
		singlePlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				CPU cpu = new CPU(syn,board,messages);
			}
		});
	}

		
	void setGame(){
		syn.setGame(true);
		for (int i = 0; i < 9; i++) {
			board.setButton(i, '\0');
		}
		messages.setText("");
	}
}
