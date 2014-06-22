package interfaces;

import java.awt.Color;

import value.ChatMessage;

public interface ChatClientInterface {

	public void onMessage(ChatMessage m);
	
	public void pushMessage(ChatMessage m);

	public void setColor(Color color);
}
