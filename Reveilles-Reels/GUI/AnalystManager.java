
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// class extends JFrame
public class AnalystManager extends JFrame {

	private CardLayout cl; //declare class variables

	public AnalystManager()
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

		// Initialization of all panels
		AnalystPanel ap = new AnalystPanel();
        WatchHistoryData whd = new WatchHistoryData();
        PopularData pd = new PopularData();
		AnalysisOfInterest aoi = new AnalysisOfInterest();

		// Adding the panels to the mainPanel, with their respective names
		mainPanel.add(ap, "analystPanel");
        mainPanel.add(whd, "watchHistoryData");
        mainPanel.add(pd, "popularData");
		mainPanel.add(aoi, "analysisOfInterest");

		// get content pane and add mainPanel to it
		getContentPane().add(mainPanel);
	}

}

