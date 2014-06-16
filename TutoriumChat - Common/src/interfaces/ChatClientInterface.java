package interfaces;

import value.ChatMessage;

public interface ChatClientInterface {

	public void onMessage(ChatMessage m);
	
	public void pushMessage(ChatMessage m);
}
