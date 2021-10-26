import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// class extends JFrame
public class ClientManager extends JFrame {

	private CardLayout cl; //declare class variables
	public String ClientID;

	public ClientManager(String inClientID)
	{
		setTitle("Reveilles Reels"); // name the JFrame
		setSize(1200, 800); // Function to set size of JFrame

		// main JPanel that holds the layout
		JPanel mainPanel = new JPanel();

		// Initialization of object "c1"
		// of CardLayout class.
		cl = new CardLayout();

		// set the layout to card layout
		mainPanel.setLayout(cl);

		//pass ClientID
		ClientID = inClientID;
		// Initialization of all panels
		ClientPanel cp = new ClientPanel(ClientID);
		AllContent ac = new AllContent();
		ReccomendedContent rc = new ReccomendedContent(ClientID);
		WatchHistory wh = new WatchHistory(ClientID);

		// Adding the panels to the mainPanel, with their respective names
		mainPanel.add(cp, "clientPanel");
		mainPanel.add(ac, "allContent");
		mainPanel.add(rc, "recContent");
		mainPanel.add(wh, "watchHistory");

		// get content pane and add mainPanel to it
		getContentPane().add(mainPanel);
	}

}

