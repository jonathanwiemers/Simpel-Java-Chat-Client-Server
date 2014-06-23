package domain;

import gui.Gui;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import network.Client;
import value.ChatMessage;

public class MessageManager {
	
	
	private List<ChatMessage> messages;
	private Gui chatGui;
	private String name;
	private Client client;

	public MessageManager(String name) throws UnknownHostException, LookupFailedException, EstablishConnectionFailed {
		// Socket Verbindung herstellen

		this.client = new Client(this);
		new Thread(this.client).start();

		messages = new Vector<ChatMessage>();
		this.chatGui = new Gui(this);
		this.name = name;
	}

	public void addMessage(ChatMessage m) {
		this.chatGui.appendMessage(m);
	}

	public void sendMessage(String message) {
		ChatMessage temp = new ChatMessage();
		temp.setAuthor(this.name);
		temp.setMessage(message);
		temp.setColor(client.getColor());
		this.client.pushMessage(temp);
	}
	
	public void logout(){
		this.client.logout();
	}

}
