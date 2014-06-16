package domain;

import gui.Gui;

import javax.swing.JOptionPane;

import network.Client;


	
public class ChatClient {
	
	
	public static void main(String[] args){
		String name = JOptionPane.showInputDialog(null,"Geben Sie Ihren Namen ein","TutoriumChat-Client",JOptionPane.PLAIN_MESSAGE);
		MessageManager mgr = new MessageManager(name);

	}
}
