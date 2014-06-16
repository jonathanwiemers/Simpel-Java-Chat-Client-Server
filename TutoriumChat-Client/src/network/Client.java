package network;

import interfaces.ChatClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import value.ChatMessage;
import domain.MessageManager;

/**
 * socket Verbindung
 * 
 * @author Jonathan
 * 
 */
public class Client implements ChatClientInterface, Runnable {
	private MessageManager mgr;
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private boolean isRunning = true;

	public Client(MessageManager mgr) {
		this.mgr = mgr;
		this.connect();
	}

	public void connect() {
		try {
			this.socket = new Socket("127.0.0.1", 1337);
			
			this.sInput = new ObjectInputStream(this.socket.getInputStream());			
			this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			this.closeConnection();
			e.printStackTrace();
		}

		System.err.println("Socket Verbindung");
	}

	public void closeConnection() {
		try {
			this.socket.close();
			this.isRunning = false;

		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] options = { "Shutdown", "Reconnect" };
		int selected = JOptionPane.showOptionDialog(null,
				"Treffen Sie eine Auswahl", "Alternativen",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[0]);
		
		switch(selected){
		case 0: System.exit(-1);
				break;
		case 1: this.mgr.connect();	
			
		}
		

	}

	@Override
	public void pushMessage(ChatMessage m) {
		try {
			this.sOutput.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(ChatMessage m) {
		System.out.println("62: neue Nachricht am client");
		this.mgr.addMessage(m);
	}

	@Override
	public void run() {
		ChatMessage cm;
		while (this.isRunning) {
			System.out.println("waiting for messages");
			try {
				/*
				 * Da nichts anderes außer ChatMessages übertragen werden, kann
				 * hier gleich gecastet werden
				 */
				this.onMessage((ChatMessage) this.sInput.readObject());
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Lost Connection");
				this.closeConnection();
			}
		}
	}
}
