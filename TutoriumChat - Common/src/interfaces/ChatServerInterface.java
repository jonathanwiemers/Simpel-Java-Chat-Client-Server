package interfaces;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import value.ChatMessage;
import de.root1.simon.exceptions.NameBindingException;

public interface ChatServerInterface {

	
	public void login(ChatClientInterface client);

	public void removeSession(ChatClientInterface session);
	
	public void startServer() throws UnknownHostException, IOException, NameBindingException;
	
	public void stopServer();

	public List<ChatClientInterface> getSessions();
	
	public void pushMessage(ChatMessage m);
	
}
