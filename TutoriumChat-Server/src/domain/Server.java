package domain;

import java.io.IOException;

import network.ChatServer;
import de.root1.simon.exceptions.NameBindingException;

public class Server {

	public static void main(String[] args) {
		ChatServer chatS = null;
		try {
			chatS = new ChatServer();
		} catch (IOException | NameBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(chatS).start();
	}

}
