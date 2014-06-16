package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import value.ChatMessage;
import domain.MessageManager;

public class Gui extends JFrame {
	final private MessageManager mgr;
	private JScrollPane scrollpane;
	private JPanel messagePane;

	public Gui(final MessageManager mgr) {
		this.mgr = mgr;
		int w = 450;
		int h = 300;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(w, h);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setTitle("TutChat");
		// Center the Window
		this.setLocation(
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()) - w) / 2,
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - h) / 2));

		this.setLayout(new BorderLayout());

		// JPanel MainContent erstellen und JFrame Chat hinzuf�gen.
		JPanel mainContent = new JPanel();
		mainContent.setLayout(new BorderLayout());
		mainContent.setPreferredSize(new Dimension(w - 20, h - 100));
		this.add(mainContent);

		// JButton "commit" wird erstellt.
		final JButton sendButton = new JButton("send");
		sendButton.setPreferredSize(new Dimension(100, 30));

		// JTextField wird erstellt.
		final JTextField input = new JTextField();
		input.setPreferredSize(new Dimension(300, 30));

		// JPanel commitPane wird hinzugef�gt.
		JPanel commitPane = new JPanel(new FlowLayout());

		// ChaListener und KeyListener werden erzeugt.
		// ChatListener ch = new ChatListener(this.lock, input);
		// input.addKeyListener(ch);

		commitPane.add(input);

		commitPane.add(sendButton);
		mainContent.add(commitPane, BorderLayout.PAGE_END);
		this.messagePane = new JPanel();
		this.messagePane.setLayout(new BoxLayout(this.messagePane,
				BoxLayout.Y_AXIS));

		this.scrollpane = new JScrollPane(this.messagePane);
		this.scrollpane.setPreferredSize(new Dimension(400, 220));

		JScrollBar vertical = this.scrollpane.getVerticalScrollBar();

		// die Scrollbar immer nach unten scrollen, wenn der view schon unten ist, sonst nicht
		vertical.addAdjustmentListener(new AdjustmentListener() {

			BoundedRangeModel brm = scrollpane
					.getVerticalScrollBar().getModel();
			boolean wasAtBottom = true;
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (!brm.getValueIsAdjusting()) {
					if (wasAtBottom)
						brm.setValue(brm.getMaximum());
				} else
					wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm
							.getMaximum());

			}
		});

		mainContent.add(this.scrollpane, BorderLayout.PAGE_START);

		/*
		 * Action Listener für den Button damit mit einem Klick auf send auch
		 * was gemacht wird ;)
		 */
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = input.getText();
				input.setText("");
				input.requestFocus();
				mgr.sendMessage(message);

			}
		});

		input.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			/*
			 * Enter löst einen klick auf den button aus
			 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode() == 10) {
					sendButton.doClick();
				}

			}
		});

		this.setVisible(true);
		input.requestFocus();
	}

	public void appendMessage(ChatMessage m) {
		JTextPane temp = new JTextPane();
		temp.setForeground(m.getColor());
		temp.setText(m.getAuthor() + ": " + m.getMessage());
		temp.setPreferredSize(new Dimension(420, 30));
		temp.setMaximumSize(temp.getPreferredSize());
		temp.setEditable(false);
		temp.setBackground(m.getColor().darker().darker().darker());
		temp.setAlignmentX(Component.LEFT_ALIGNMENT);
		temp.setAlignmentY(Component.TOP_ALIGNMENT);
		this.messagePane.add(temp);
		this.messagePane.revalidate();
		this.scrollpane.revalidate();
	}

}
