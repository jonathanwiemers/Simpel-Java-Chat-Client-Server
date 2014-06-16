package network;

import interfaces.ChatClientInterface;

import java.awt.Color;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

import value.ChatMessage;

public class ChatServer implements Runnable {

	private Vector<ChatClientInterface> clients;
	private int port = 1337;
	/*
	 * für random colors 
	 */
	private Random rand;
	
	public ChatServer(){
		this.clients = new Vector<ChatClientInterface>();
		this.rand = new Random();
	}
	
	@Override
	public void run() {
		 try {
			 ServerSocket serverSocket = new ServerSocket(port);
			 while(true){
				 System.out.println("Server waiting for Clients on port " + this.port + ".");
				 Socket socket = serverSocket.accept();   
				 System.out.println("New Client");
				 this.generateRandomColor();
				 this.clients.add(new ClientThread(socket,this.generateRandomColor(),this));  	 
			 }
		 } catch (Exception e){
			 e.printStackTrace();
		 }
	}
	
	
	public synchronized void broadcast(ChatMessage m){
		System.out.println("neue Nachricht");
		for(ChatClientInterface client: this.clients){
			client.pushMessage(m);
		}
	}
	
	public synchronized void removeClient(ChatClientInterface c){
		System.out.println(this.clients.remove(c));
	}
	
	/**
	 * Generiert eine Random Color
	 * @return
	 */
	private synchronized Color generateRandomColor(){
		return new Color(this.rand.nextInt(0xFFFFFF));
	}
	
	
	
}
