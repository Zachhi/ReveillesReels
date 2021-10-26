import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalystPanel extends JPanel implements ActionListener {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);

	JButton watchHistory = new JButton("Watch History Data");
	JButton popularContent = new JButton("Popular Content Among Viewers");
	JButton analysisOfInterest = new JButton("Analysis of Interest");

	public AnalystPanel () {
		super();
		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);
		//setLayout(null);	If i set to null unable to see buttons
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		//Watch History Button create
		watchHistory.setPreferredSize(new Dimension(200, 200));
		watchHistory.addActionListener(this);

		//Popular Content Button Create
		popularContent.setPreferredSize(new Dimension(200, 200));
		popularContent.addActionListener(this);

		//Analysis of Interest Button Create
		analysisOfInterest.setPreferredSize(new Dimension(200, 200));
		analysisOfInterest.addActionListener(this);

		//create title Label
		JLabel titLabel = new JLabel("Reveille's Reels");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);
		

		//add labels
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(titLabel, c);
		add(titLabel);

		// Buttons Added
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(watchHistory, c);
		add(watchHistory);

		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(popularContent, c);
		add(popularContent);

		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(analysisOfInterest, c);
		add(analysisOfInterest);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		JPanel buttonPanel = (JPanel)button.getParent();
		JPanel cardPanel = (JPanel)buttonPanel.getParent();
		CardLayout layout = (CardLayout)cardPanel.getLayout();
		if(button == watchHistory)
		{
			layout.show(cardPanel, "watchHistoryData");
		}
		else if (button == popularContent)
		{
			layout.show(cardPanel, "popularData");
		}
		else if (button == analysisOfInterest) {
			layout.show(cardPanel, "analysisOfInterest");
		}
	}

}