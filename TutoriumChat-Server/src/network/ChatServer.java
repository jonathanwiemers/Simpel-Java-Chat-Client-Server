package network;

import interfaces.ChatClientInterface;
import interfaces.ChatServerInterface;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import value.ChatMessage;
import value.Constants;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { ChatServerInterface.class })
public class ChatServer implements ChatServerInterface, Serializable, Runnable {

	// Serializable
	private static final long serialVersionUID = 311253596910399880L;
	
	
	private Vector<ChatClientInterface> clients;
	private int port = Constants.STANDARD_PORT;
	
	// SIMON benötigt:
	Registry registry;
	
	
	/*
	 * f�r random colors 
	 */
	private Random rand;
	
	
	
	public ChatServer() throws UnknownHostException, IOException, NameBindingException{
		this.clients = new Vector<ChatClientInterface>();
		this.rand = new Random();
		
	}
	
	
	
	
	
	public synchronized void broadcast(ChatMessage m){
		System.out.println("neue Nachricht");
		for(ChatClientInterface client: this.clients){
			client.onMessage(m);
		}
	}
	
	/**
	 * Generiert eine Random Color
	 * @return
	 */
	private synchronized Color generateRandomColor(){
		return new Color(this.rand.nextInt(0xFFFFFF));
	}
	
	
	
	public synchronized void removeClient(ChatClientInterface c){
		System.out.println(this.clients.remove(c));
	}
	
	

	@Override
	public void login(ChatClientInterface client) {
	
		// Session in die Session-Liste eintragen
		clients.add(client);
		client.setColor(generateRandomColor());
	
	}

	/**
	 * 
	 * @param client - ChatClientInterface - Client der entfernt werden soll
	 */
	@Override
	public void removeSession(ChatClientInterface client) {
		clients.remove(client);
		System.out.println("Client abgemeldet");
	}

	/**
	 * Erstellt eine Registry und bindet diese an der Serverinterface Objekt
	 */
	@Override
	public void startServer() throws UnknownHostException, IOException,
			NameBindingException {
		// Registry erstellen
		Registry registry = Simon.createRegistry(Constants.STANDARD_PORT);
		// Registry an das Serverinterface-Objekt binden und Namen vergeben
		registry.bind(Constants.SERVER_NAME, this);
		System.out.println("Server running listening on Port " + Constants.STANDARD_PORT);
	}

	/**
	 * Die Bindung zwischen Registry und dem ServerInterface wird gel��st
	 * und die Registry gestoppt
	 */
	@Override
	public void stopServer() {
		if(registry != null) {
			// Bindung von der Registry l��sen
			registry.unbind(Constants.SERVER_NAME);
			// Registry stoppen
			registry.stop();
		}
	}

	@Override
	public List<ChatClientInterface> getSessions() {
	
		return clients;
	}





	@Override
	public void run() {
		try {
			this.startServer();
		} catch (IOException | NameBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}






	@Override
	public void pushMessage(ChatMessage m) {
		this.broadcast(m);
		
	}
	
	
	
}
