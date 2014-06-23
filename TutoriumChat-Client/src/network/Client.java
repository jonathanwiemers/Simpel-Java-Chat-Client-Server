package network;

import interfaces.ChatClientInterface;
import interfaces.ChatServerInterface;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import value.ChatMessage;
import value.Constants;
import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import domain.MessageManager;


@SimonRemote(value = { ChatClientInterface.class })
public class Client implements ChatClientInterface, Runnable {
	
	/** wird f��r Netzwerkverbindung ��ber Simon ben��tigt */
	private Lookup lookup;
	
	private String ip;
	
	private MessageManager mgr;
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private boolean isRunning = true;
	
	private Color color;
	
	private ChatServerInterface server;
	

	public Client(MessageManager mgr) throws UnknownHostException, LookupFailedException, EstablishConnectionFailed {
		
		// Standard IP
		ip = "127.0.0.1";
		
		this.mgr = mgr;
		
		// Nach IP des Servers fragen
		ip = (String) JOptionPane.showInputDialog(null,
						"Bitte gib die Server-IP ein.", "Verbindung zum Server",
						JOptionPane.QUESTION_MESSAGE, null, null, ip);
		// mit Server verbinden
		this.connectToServer(ip);
		
	}
	
	/**
	 * Methode sucht unter der angegebenen IP-Adresse und zugeh��rigem Port 
	 * nach einem aktiven Server. Wird ein Server gefunden findet ein login statt
	 * @param ip - String - IP Adresse des Servers
	 * @throws UnknownHostException
	 * @throws LookupFailedException
	 * @throws EstablishConnectionFailed
	 */
	public void connectToServer(String ip) throws UnknownHostException,
		LookupFailedException, EstablishConnectionFailed {
		
		// Lookup f��r den Server einrichten (Host-IP, Port des Servers)
		lookup = Simon.createNameLookup(ip, Constants.STANDARD_PORT);
		
		// Server-Objekt aufsuchen
		ChatServerInterface server = (ChatServerInterface) lookup
				.lookup(Constants.SERVER_NAME);
		
		// Client auf dem Server anmelden
	    server.login(this);
			
	    // Server-Interface lokal hinterlegen
		this.server = server;
		
	}

	

	public void pushMessage(ChatMessage m) {
		this.server.broadcastMessage(m);
	}

	@Override
	public void onMessage(ChatMessage m) {
		System.out.println("62: neue Nachricht am client");
		this.mgr.addMessage(m);
	}

	@Override
	public void run() {
	
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
		
	}
}
