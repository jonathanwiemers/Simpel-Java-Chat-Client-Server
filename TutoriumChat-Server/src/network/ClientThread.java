package network;

import interfaces.ChatClientInterface;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import value.ChatMessage;

public class ClientThread implements Runnable, ChatClientInterface{
	
	private ObjectInputStream sInput; // to read from the socket
	private ObjectOutputStream sOutput; // to write on the socket
	private Socket socket;
	private ChatServer server;
	private boolean isRunning = true;
	private Color color;
	
	public ClientThread(Socket s,Color c, ChatServer server){
		this.color = c;
		this.socket = s;
		this.server = server;
		try {
			this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.sInput = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Starte den Client Thread 
		 */
		new Thread(this).start();
		
	}
	
	public void closeConnection(){
		try{
			this.socket.close();
			this.server.removeClient(this);
			this.isRunning = false;
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void run() {
		ChatMessage cm;
		while(this.isRunning){
			try {
				/*
				 * Da nichts anderes außer ChatMessages übertragen werden, kann hier gleich gecastet werden
				 */
				this.onMessage((ChatMessage)this.sInput.readObject());
			} catch (ClassNotFoundException | IOException e) {	
				System.err.println("Lost Connection");
				this.closeConnection();
			}
		}

	}

	@Override
	public void onMessage(ChatMessage m) {
		this.server.broadcast(m.setColor(this.color));
	}

	@Override
	public void pushMessage(ChatMessage m) {
		try {
			this.sOutput.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
