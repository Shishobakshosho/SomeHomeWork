package common;

import java.io.*;
import java.net.*;
import java.util.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;

public class ServerResources {
	private String ip = "localhost";
	private int port = 22222;
	
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;

	private ServerSocket serverSocket;
	
	private boolean canChange = true;
    
	public String getCurrentIp(){
		Enumeration<NetworkInterface> nets;
		try {
		    nets = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return "Unknown";
		}
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
		
	public boolean initializeServer(int port) {
		
		if(serverSocket != null && !serverSocket.isClosed()){
			try {
				serverSocket.close();
			} catch (IOException e) {
				return false;
				//e.printStackTrace();
			}
		}
		
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
			this.port = port;
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	
	
	public synchronized String getIp() {
		return ip;
	}

	public synchronized void setIp(String ip) {
		this.ip = ip;
	}

	public synchronized int getPort() {
		return port;
	}

	public synchronized void setPort(int port) {
		this.port = port;
	}

	public synchronized Socket getSocket() {
		return socket;
	}

	public synchronized void setSocket(Socket socket) {
		this.socket = socket;
	}

	public synchronized DataOutputStream getDos() {
		return dos;
	}

	public synchronized void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public synchronized DataInputStream getDis() {
		return dis;
	}

	public synchronized void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public synchronized ServerSocket getServerSocket() {
		return serverSocket;
	}

	public synchronized void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public synchronized boolean isCanChange() {
		return canChange;
	}

	public synchronized void setCanChange(boolean canChange) {
		this.canChange = canChange;
	}
}
