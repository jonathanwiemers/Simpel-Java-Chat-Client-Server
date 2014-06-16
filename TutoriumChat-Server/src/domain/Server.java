package domain;

import network.ChatServer;

public class Server {

	public static void main(String[] args) {
		ChatServer chatS = new ChatServer();
		new Thread(chatS).start();
	}

}
