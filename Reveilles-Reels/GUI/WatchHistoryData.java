import java.awt.*;
import java.nio.file.WatchService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class WatchHistoryData extends JPanel implements ActionListener {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);
	JTextField titleLabel = new JTextField("Title");
	JTextField titleText = new JTextField(10);
	JTextField genreLabel = new JTextField("Genre");
	JTextField genreText = new JTextField(10);
	JTextField directorLabel = new JTextField("Director");
	JTextField directorText = new JTextField(10);
	JTextField actorLabel = new JTextField("Actor");
	JTextField actorText = new JTextField(10);
	JTextArea showList;
	JButton searchBtn = new JButton("Search");
	JButton backBtn = new JButton("Home");

	public WatchHistoryData() {
		super();
		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);

		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
					"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		// JOptionPane.showMessageDialog(null, "Opened database successfully");

		String shows = "";
		try {
			// create a statement object
			Statement stmt = conn.createStatement();
			// create an SQL statement
			String sqlStatementShows = "SELECT title, date_watched, client_id" + " FROM"
					+ " (SELECT content_id, client_id, date_watched FROM VIEWS" + "  )view_sq" + " INNER JOIN" + " CONTENT"
					+ " On content.content_id = view_sq.content_id" + " ORDER BY date_watched DESC" + " LIMIT 1000";
			// System.out.println(sqlStatementShows);
			ResultSet Result = stmt.executeQuery(sqlStatementShows);
			shows = "Title\t| Date Watched\t| Client ID\n";
			while (Result.next()) {
			
				shows += Result.getString("title") + "\t| " + Result.getString("date_watched") + "\t| "
						+ Result.getString("client_id") + "\n";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database.");
		}

		// setLayout(null); If i set to null unable to see buttons
		// JPanel jp2 = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		// All Content Button create
		searchBtn.setPreferredSize(new Dimension(100, 50));
		searchBtn.addActionListener(this);
		backBtn.addActionListener(this);

		// create title Label
		JLabel titLabel = new JLabel("Watch History");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel showTitle = new JLabel("TOP SHOWS");
		showTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		showTitle.setForeground(Color.WHITE);
		showTitle.setHorizontalAlignment(SwingConstants.LEFT);

		showList = new JTextArea(shows);
		JScrollPane showScrollPane = new JScrollPane(showList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// add labels
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(titLabel, c);
		add(titLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(showTitle, c);
		// add(showTitle);

		c.gridx = 0;
		c.gridy = 11;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(showScrollPane, c);
		add(showScrollPane);

		// Buttons Added
		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.gridx = 2;
		c.gridy = 0;
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		JPanel buttonPanel = (JPanel) button.getParent();
		JPanel cardPanel = (JPanel) buttonPanel.getParent();
		CardLayout layout = (CardLayout) cardPanel.getLayout();
		if (button == searchBtn) {
			JPanel panel = new JPanel(new GridLayout(5, 3));
			titleLabel.setEditable(false);
			genreLabel.setEditable(false);
			directorLabel.setEditable(false);
			String s1[] = {"All", "Movie", "Series"};
			JComboBox<String> typeMenu = new JComboBox<String>(s1);

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

			Connection conn = null;
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
						"csce315903_13user", "31590313");
			} catch (Exception em) {
				em.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + em.getMessage());
				System.exit(0);
			}

			// JOptionPane.showMessageDialog(null, "Opened database successfully");

			String shows = "";
			try {
				// create a statement object
				Statement stmt = conn.createStatement();

				ArrayList<String> searchFields = new ArrayList<String>();
				String sqlStatementShows = "";
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


				sqlStatementShows = "SELECT * FROM(SELECT title, date_watched, client_id" + " FROM"
						+ " (SELECT content_id, client_id, date_watched FROM VIEWS" + "  )view_sq" + " INNER JOIN" + " CONTENT"
						+ " On content.content_id = view_sq.content_id" + " ORDER BY date_watched DESC)b ";

				for (int i = 0; i < searchFields.size(); i++) {
					sqlStatementShows += searchFields.get(i);
				}
				sqlStatementShows += "LIMIT 1000;";
				//System.out.println(sqlStatementShows);
				// create an SQL statement
				// System.out.println(sqlStatementShows);
				ResultSet Result = stmt.executeQuery(sqlStatementShows);
				shows = "Title\t|Date Watched\t|Client ID\n";
				while (Result.next()) {
					shows += Result.getString("title") + "\t| " + Result.getString("date_watched") + "\t| "
							+ Result.getString("client_id") + "\n";
				}
				if (shows.equals("Title\t|Date Watched\t|Client ID\n")) {
					JOptionPane.showMessageDialog(null, "Invalid Search. Try again.");
				}
			} catch (Exception em) {
				JOptionPane.showMessageDialog(null, "Error accessing Database.");
			}
			showList.setText(shows);
			layout.show(cardPanel, "search");
		} else if (button == backBtn) {
			layout.show(cardPanel, "analystPanel");
		}
	}
}
