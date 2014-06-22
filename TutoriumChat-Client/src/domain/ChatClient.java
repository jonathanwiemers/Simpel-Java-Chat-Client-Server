package domain;

import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;


	
public class ChatClient {
	
	
	public static void main(String[] args){
		String name = JOptionPane.showInputDialog(null,"Geben Sie Ihren Namen ein","TutoriumChat-Client",JOptionPane.PLAIN_MESSAGE);
		try {
			MessageManager mgr = new MessageManager(name);
		} catch (UnknownHostException | LookupFailedException
				| EstablishConnectionFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
