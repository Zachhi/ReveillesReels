import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JPanel implements ActionListener {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);

	//need these buttons up here so we can use the override actionPerformed method
	JButton allContentBtn = new JButton("All Content");
	JButton recContentBtn = new JButton("Recomended Content");
	JButton watchHistBtn = new JButton("Watch History");

	public ClientPanel (String inClientID) {
		super();
		String ClientID = inClientID;

		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);
		//setLayout(null);	If i set to null unable to see buttons
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		//All Content Button create
		allContentBtn.setPreferredSize(new Dimension(200, 200));
		allContentBtn.addActionListener(this);

		//Reccomended Content Button Create
		recContentBtn.setPreferredSize(new Dimension(200, 200));
		recContentBtn.addActionListener(this);

		//Watch History Button Create
		watchHistBtn.setPreferredSize(new Dimension(200, 200));
		watchHistBtn.addActionListener(this);

		//create title Label
		JLabel titLabel = new JLabel("Reveille's Reels");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// //create ClientID Label
		JLabel clientLabel = new JLabel("ClientID: "+ ClientID);
		clientLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		clientLabel.setForeground(Color.WHITE);
		clientLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
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

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 1;
		layout.setConstraints(clientLabel, c);
		add(clientLabel);

		// Buttons Added
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(allContentBtn, c);
		add(allContentBtn);
		
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(recContentBtn, c);
		add(recContentBtn);

		c.gridy = 2;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(watchHistBtn, c);
		add(watchHistBtn);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		JPanel buttonPanel = (JPanel)button.getParent();
		JPanel cardPanel = (JPanel)buttonPanel.getParent();
		CardLayout layout = (CardLayout)cardPanel.getLayout();
		if(button == allContentBtn)
		{
			layout.show(cardPanel, "allContent");
		}
		else if (button == recContentBtn)
		{
			layout.show(cardPanel, "recContent");
		}
		else if (button == watchHistBtn)
		{
			layout.show(cardPanel, "watchHistory");
		}
	}
	
}