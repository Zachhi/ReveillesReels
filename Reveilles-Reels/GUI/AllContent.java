import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class AllContent extends JPanel implements ActionListener {
    public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);
    private JButton searchBtn = new JButton("Search");
	private JButton backBtn = new JButton("Home");
	private Connection conn;
	private JTextArea contentList;
	private JScrollPane contentListScroll;

	public AllContent() {
		super();

		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);

		conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
			"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}

		//JOptionPane.showMessageDialog(null,"Opened database successfully");
		String name = "";
		try{
			//create a statement object
			Statement stmt = conn.createStatement();
			String sqlStatement = "SELECT * FROM content LIMIT 1000";
			//send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlStatement);
			while (result.next()) {
			name += result.getString("title")+", ";
			name += result.getString("release_date")+", ";
			name += result.getString("title_type")+"\n";
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}
		
		//setLayout(null);	If i set to null unable to see buttons
		//JPanel jp2 = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		//All Content Button create
		searchBtn.setPreferredSize(new Dimension(100, 50));
		searchBtn.addActionListener(this);
		backBtn.addActionListener(this);
		
		//create title Label
		JLabel titLabel = new JLabel("All Content");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// create text area
		contentList = new JTextArea(name);
		contentList.setEditable(false);
		contentListScroll = new JScrollPane(contentList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//add labels
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 1;
		c.weightx = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(titLabel, c);
		add(titLabel);

		//add text area
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(contentListScroll, c);
		add(contentListScroll);

		// Buttons Added
		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.gridx = 2;
		c.gridy=0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.setConstraints(searchBtn, c);
		add(searchBtn);

		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		layout.setConstraints(backBtn, c);
		add(backBtn);

		try {
			conn.close();
			// JOptionPane.showMessageDialog(null,"Connection Closed.");
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
		}
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		JPanel buttonPanel = (JPanel)button.getParent();
		JPanel cardPanel = (JPanel)buttonPanel.getParent();
		CardLayout layout = (CardLayout)cardPanel.getLayout();
		if(button == searchBtn) {
			JPanel panel = new JPanel(new GridLayout(5, 3));
			JTextField titleLabel = new JTextField("Title");
			JTextField titleText = new JTextField(10);
			JTextField genreLabel = new JTextField("Genre");
			JTextField genreText = new JTextField(10);
			JTextField directorLabel = new JTextField("Director");
			JTextField directorText = new JTextField(10);
			JTextField actorLabel = new JTextField("Actor");
			JTextField actorText = new JTextField(10);
			String s1[] = {"All", "Movie", "Series"};
			JComboBox<String> typeMenu = new JComboBox<String>(s1);
			titleLabel.setEditable(false);
			genreLabel.setEditable(false);
			directorLabel.setEditable(false);

			panel.add(titleLabel);
			panel.add(titleText);
			panel.add(genreLabel);
			panel.add(genreText);
			panel.add(directorLabel);
			panel.add(directorText);
			panel.add(actorLabel);
			panel.add(actorText);
			panel.add(typeMenu);
			JOptionPane.showMessageDialog(null, panel);


			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
				"csce315903_13user", "31590313");
			} catch (Exception es) {
				es.printStackTrace();
				System.err.println(es.getClass().getName()+": "+es.getMessage());
				System.exit(0);
			}

			String name = "";
			try{
				//create a statement object
				Statement stmt1 = conn.createStatement();
				//create an SQL statement
				String sqlStatement = "SELECT * FROM content";
				ArrayList<String> searchFields = new ArrayList<String>();
				int whereCount = 0; 
				if (!actorText.getText().equals("")) { // only has genre query rght now
					searchFields.add(" INNER JOIN (SELECT * FROM ACTORS INNER JOIN PEOPLE ON PEOPLE.PEOPLE_ID = ACTORS.PEOPLE_ID)a_p ON CONTENT.CONTENT_ID = a_p.content_id");
					if (whereCount == 0) {
						searchFields.add(" WHERE name LIKE \'%\' || \'" + actorText.getText() + "\' || \'%\'");
						whereCount++;
					} else {
						searchFields.add(" AND name LIKE \'%\' || \'" + actorText.getText() + "\' || \'%\'");
						whereCount++;
					}
				}
				if (!directorText.getText().equals("")) { // only has director query now
					searchFields.add(" INNER JOIN (SELECT * FROM STAFF INNER JOIN PEOPLE ON STAFF.PEOPLE_ID = PEOPLE.PEOPLE_ID WHERE ROLE = 'director')s_p ON CONTENT.CONTENT_ID = s_p.content_id");
					if (whereCount == 0) {
						searchFields.add(" WHERE name LIKE \'%\' || \'" + directorText.getText() + "\' || \'%\'");
						whereCount++;
					} else {
						searchFields.add(" AND name LIKE \'%\' || \'" + directorText.getText() + "\' || \'%\'");
						whereCount++;
					}
				}
				if (!(titleText.getText()).equals("")) {
					if (whereCount == 0) {
						searchFields.add(" WHERE title LIKE \'%\' || \'" + titleText.getText() + "\' || \'%\'");
						whereCount++;
					} else {
						searchFields.add(" AND title LIKE \'%\' || \'" + titleText.getText() + "\' || \'%\'");
						whereCount++;
					}
				} 
				if (!genreText.getText().equals("")) {
					if (whereCount == 0) {
						searchFields.add(" WHERE genre LIKE \'%\' || \'" + genreText.getText() + "\' || \'%\'");
						whereCount++;
					} else {
						searchFields.add(" AND genre LIKE \'%\' || \'" + genreText.getText() + "\' || \'%\'");
						whereCount++;
					}
				}
				if (!typeMenu.getSelectedItem().equals("All")) {
					if (whereCount == 0) {
						String command = " WHERE title_type LIKE \'%\' || \'" + typeMenu.getSelectedItem() + "\' || \'%\'";
						command += " OR title_type LIKE \'%\' || \'" + ((String) typeMenu.getSelectedItem()).toLowerCase() + "\' || \'%\'";
						searchFields.add(command);
						whereCount++;
					} else {
						String command = " AND title_type LIKE \'%\' || \'" + typeMenu.getSelectedItem() + "\' || \'%\'";
						command += " OR title_type LIKE \'%\' || \'" + ((String) typeMenu.getSelectedItem()).toLowerCase() + "\' || \'%\'";
						searchFields.add(command);
						whereCount++;
					}
				}
				
				for (int i = 0; i < searchFields.size(); i++) {
					sqlStatement += searchFields.get(i);
				}
				
				sqlStatement += " ORDER BY title ASC LIMIT 1000;";

				//send statement to DBMS
				ResultSet result = stmt1.executeQuery(sqlStatement);
				while (result.next()) {
					name += result.getString("title")+", ";
					name += result.getString("release_date") + ", ";
					name += result.getString("title_type")+"\n";
				}
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Search. Try again.");
				}
				contentList.setText(name);

				try {
					conn.close();
					// JOptionPane.showMessageDialog(null,"Connection Closed.");
				} catch(Exception et) {
					JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
				}
			} catch (Exception em){
				JOptionPane.showMessageDialog(null,"Error accessing Database.");
			}
			// layout.show(cardPanel, "allContent");
			System.out.println("End of if");		
		}
		else if (button == backBtn) {
			layout.show(cardPanel, "clientPanel");
		} 

	}
}
