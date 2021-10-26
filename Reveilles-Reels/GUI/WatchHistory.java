import java.awt.*;
import java.nio.file.WatchService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import java.sql.*;

public class WatchHistory extends JPanel implements ActionListener {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);
	JButton backBtn = new JButton("Home");
	JComboBox<String> intervalOptionMonth;
	JComboBox<String> intervalOptionYear;
	public  String intervalChoiceMonth;
	public  String intervalChoiceYear;
	public final String[] intervalsMonths = { "All Months", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12" };
	public final String[] intervalsYears = {"All Years","1999","2000","2001","2002","2003","2004","2005"};
	public final String clientID;
	Connection conn;
	String currChoiceMonth;
	String currChoiceYear;
	String sqlBegMonthDay;
	String sqlEndMonthDay;
	String sqlBegYear, sqlEndYear;
	String sqlDateInterval;

	public WatchHistory(String inClientID) {
		super();
		clientID = inClientID;
		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);

		conn = null;
		// Query for All Watch history from Database
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
					"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		String watchHistory = "";
		try {
			// create a statement object
			Statement stmt = conn.createStatement();
			// create an SQL statement
			String sqlStatement = "SELECT title, date_watched, client_id" + " FROM"
					+ " (SELECT content_id, client_id, date_watched FROM VIEWS" + " WHERE CLIENT_ID = '" + clientID
					+ "')view_sq" + " INNER JOIN" + " CONTENT" + " On content.content_id = view_sq.content_id;";
			// send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlStatement);
			while (result.next()) {
				watchHistory += result.getString("title") + "\t" + result.getString("date_watched") + "\t"
						+ result.getString("client_id") + "\n";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database.");
		}

		try {
			conn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
		}


		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		// All Content Button create
		backBtn.addActionListener(this);
		// create title Label
		JLabel titLabel = new JLabel("Watch History");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// create text area
		JTextArea contentList = new JTextArea(watchHistory);
		contentList.setEditable(false);
		JScrollPane contentListScroll = new JScrollPane(contentList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
		// add JCombo Boxes to filter for duration (watch history in last month, week, etc)
		intervalOptionMonth = new JComboBox<>(intervalsMonths);
		intervalOptionMonth.setPreferredSize(new Dimension(100, 100));
		intervalOptionMonth.setSelectedIndex(0);

		intervalOptionYear = new JComboBox<>(intervalsYears);
		intervalOptionYear.setPreferredSize(new Dimension(100, 100));
		intervalOptionYear.setSelectedIndex(0);


		// Local Listener for JComboBox
		class IntervalListener implements ActionListener {
			
			public void actionPerformed(ActionEvent a) {
			
				String watchHistory = getNewWatchHistory(conn, a);

				//return string from that function
				//update text field
				contentList.setText(watchHistory);

				validate();
				repaint();

				try {
					conn.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
				}

			}
		}
		
		intervalOptionMonth.addActionListener(new IntervalListener());
		intervalOptionYear.addActionListener(new IntervalListener());

		// add labels
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

		// add text area
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(contentListScroll, c);
		add(contentListScroll);

		// Buttons Added
		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//layout.setConstraints(searchBtn, c);
		//add(searchBtn);

		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		layout.setConstraints(backBtn, c);
		add(backBtn);


		//add JComboBox
		c.ipady = 1;
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.setConstraints(intervalOptionMonth, c);
		layout.setConstraints(intervalOptionMonth, c);
		add(intervalOptionMonth);


		c.ipady = 1;
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.setConstraints(intervalOptionYear, c);
		layout.setConstraints(intervalOptionYear, c);
		add(intervalOptionYear);

	}

	//----------------------------------FUNCTIONS---------------------------------------//
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		JPanel buttonPanel = (JPanel) button.getParent();
		JPanel cardPanel = (JPanel) buttonPanel.getParent();
		CardLayout layout = (CardLayout) cardPanel.getLayout();

		// if (button == searchBtn) {
		// 	layout.show(cardPanel, "search");
		// } else 
		if (button == backBtn) {
			layout.show(cardPanel, "clientPanel");
		}
	}
 
	/*
	This function taskes input the connection to the database var, and the actionevent var triggered by changing the interval for watchHistory.
	Creates sql statement based on actioneven that occurred, creates sql statement, executes sql statement, and returns new watchHistory to fill text area with
	*/
	public String getNewWatchHistory(Connection conn, ActionEvent a){

		// based on action event create query and execute and return text to display
		currChoiceYear = intervalsYears[intervalOptionYear.getSelectedIndex()];
		currChoiceMonth = intervalsMonths[intervalOptionMonth.getSelectedIndex()];
		if (currChoiceYear == "All Years") { // if what currently set in the JComboBox = All Years.
			sqlBegYear = "1999";
			sqlEndYear = "2005";
		} else {
			sqlBegYear = currChoiceYear; // else set whats currently in the JCombox Year to the year selected
			sqlEndYear = currChoiceYear;
		}
		if (currChoiceMonth == "All Months") { // if what currently set in the JcomboBox == All Months
			sqlBegMonthDay = "01-01";
			sqlEndMonthDay = "12-31";
		} else {
			// Append proper day for whatever currMonth is set to ex: if Jan(01) day should
			// be 01-31
			if ((currChoiceMonth == "01") || (currChoiceMonth == "03") || (currChoiceMonth == "05")
					|| (currChoiceMonth == "07") || (currChoiceMonth == "08") || (currChoiceMonth == "10")
					|| (currChoiceMonth == "12")) {
				sqlBegMonthDay = currChoiceMonth + "-01";
				sqlBegMonthDay = currChoiceMonth + "-31";
			} else if (currChoiceMonth == "02") {
				sqlBegMonthDay = currChoiceMonth + "-01";
				sqlEndMonthDay = currChoiceMonth + "-27";
			} else { // everything else would be 30
				sqlBegMonthDay = currChoiceMonth + "-01";
				sqlEndMonthDay = currChoiceMonth + "-30";
			}

		}
		JComboBox<?> cb = (JComboBox<?>) a.getSource();
		if (cb == intervalOptionMonth) {
			intervalChoiceMonth = (String) cb.getSelectedItem();
			if (sqlBegYear == "1999" && sqlEndYear == "2005" && intervalChoiceMonth != "All Months") {
				sqlBegMonthDay = "01-01";
				sqlEndMonthDay = "12-31";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\' AND EXTRACT(MONTH FROM date_watched) = " + intervalChoiceMonth;
			} else {
				if (intervalChoiceMonth == "All Months") {
					sqlBegMonthDay = "01-01";
					sqlEndMonthDay = "12-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";
				} else if (intervalChoiceMonth == "01") {
					sqlBegMonthDay = "01-01";
					sqlEndMonthDay = "01-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";
				} else if (intervalChoiceMonth == "02") {
					sqlBegMonthDay = "02-01";
					sqlEndMonthDay = "02-28";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";
				} else if (intervalChoiceMonth == "03") {
					sqlBegMonthDay = "03-01";
					sqlEndMonthDay = "03-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";
				} else if (intervalChoiceMonth == "04") {
					sqlBegMonthDay = "04-01";
					sqlEndMonthDay = "04-30";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "05") {
					sqlBegMonthDay = "05-01";
					sqlEndMonthDay = "05-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "06") {
					sqlBegMonthDay = "06-01";
					sqlEndMonthDay = "06-30";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "07") {
					sqlBegMonthDay = "07-01";
					sqlEndMonthDay = "07-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "08") {
					sqlBegMonthDay = "08-01";
					sqlEndMonthDay = "08-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "09") {
					sqlBegMonthDay = "09-01";
					sqlEndMonthDay = "09-30";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "10") {
					sqlBegMonthDay = "10-01";
					sqlEndMonthDay = "10-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";
				} else if (intervalChoiceMonth == "11") {
					sqlBegMonthDay = "11-01";
					sqlEndMonthDay = "11-30";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				} else if (intervalChoiceMonth == "12") {
					sqlBegMonthDay = "12-01";
					sqlEndMonthDay = "12-31";
					sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
							+ sqlEndMonthDay + "\'";

				}
			}

		} else if (cb == intervalOptionYear) {
			intervalChoiceYear = (String) cb.getSelectedItem();
			if (intervalChoiceYear == "All Years" && currChoiceMonth != "All Months") {
				sqlBegYear = "1999";
				sqlEndYear = "2005";
				sqlDateInterval = "\'" + sqlBegYear + "-" + "01-01" + "\'" + "AND" + "\'" + sqlEndYear + "-" + "12-31"
						+ "\' AND EXTRACT(MONTH FROM date_watched) = " + currChoiceMonth;
			} else if (intervalChoiceYear == "All Years" && currChoiceMonth == "All Months") {
				sqlBegYear = "1999";
				sqlEndYear = "2005";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "1999") {
				sqlBegYear = "1999";
				sqlEndYear = "1999";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2000") {
				sqlBegYear = "2000";
				sqlEndYear = "2000";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2001") {
				sqlBegYear = "2001";
				sqlEndYear = "2001";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2002") {
				sqlBegYear = "2002";
				sqlEndYear = "2002";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2003") {
				sqlBegYear = "2003";
				sqlEndYear = "2003";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2004") {
				sqlBegYear = "2004";
				sqlEndYear = "2004";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			} else if (intervalChoiceYear == "2005") {
				sqlBegYear = "2005";
				sqlEndYear = "2005";
				sqlDateInterval = "\'" + sqlBegYear + "-" + sqlBegMonthDay + "\'" + "AND" + "\'" + sqlEndYear + "-"
						+ sqlEndMonthDay + "\'";
			}
		}

		// System.out.println(sqlDateInterval);

		conn = null;
		//open DB
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
					"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		// call function hear for getting executing new watch history.
		String watchHistory = "";
		try {
			// create a statement object
			Statement stmt = conn.createStatement();
			// create an SQL statement
			String sqlStatement = "SELECT title, date_watched, client_id" + " FROM "
					+ "(SELECT content_id, client_id, date_watched FROM VIEWS " + "WHERE CLIENT_ID = \'" + clientID
					+ "\' AND date_watched BETWEEN " + sqlDateInterval + ") view_sq " + "INNER JOIN " + "CONTENT "
					+ "On content.content_id = view_sq.content_id;";
			// send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlStatement);
			while (result.next()) {
				watchHistory += result.getString("title") + "\t" + result.getString("date_watched") + "\t"
						+ result.getString("client_id") + "\n";

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database.");
		}

		return watchHistory;
	}
}
