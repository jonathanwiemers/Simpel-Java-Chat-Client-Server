package value;

import java.awt.Color;
import java.io.Serializable;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = -4144374517550925994L;

	private String message;
	private String author;
	private Color color;
	
	public String getMessage() {
		return message;
	}
	public ChatMessage setMessage(String message) {
		this.message = message;
		return this;
	}
	public String getAuthor() {
		return author;
	}
	public ChatMessage setAuthor(String author) {
		this.author = author;
		return this;
	}
	public Color getColor() {
		return color;
	}
	public ChatMessage setColor(Color color) {
		this.color = color;
		return this;
	}
}
