import java.awt.*;
import java.nio.file.WatchService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.Year;
import java.util.*;
import javax.swing.JComboBox;



public class PopularData extends JPanel implements ActionListener {
    public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);

    JButton searchBtn = new JButton("???");
    JButton backBtn = new JButton("Home");
	JComboBox<String>  intervalOptionMonth;
	JComboBox<String>  intervalOptionYear;

	//private static JButton allContentBtn;
	//private static JButton recContentBtn;
	public PopularData() {
		super();
		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);

		Connection conn = null; 
		try{ 
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
			"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		String shows = ""; 
		String actors = "";
		String genres = "";

		String[] attributes = findPopularAttributes(conn, shows, actors, genres);
		shows = attributes[0];
		actors = attributes[1];
		genres = attributes[2];

	
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		//All Content Button create
		searchBtn.setPreferredSize(new Dimension(100, 50));
		searchBtn.addActionListener(this);
        backBtn.addActionListener(this);

		//create title Label
		JLabel titLabel = new JLabel("Popular Content");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);
		titLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JTextArea showList = new JTextArea(shows);
		showList.setEditable(false);
		JScrollPane showScrollPane = new JScrollPane(showList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JTextArea actorsList = new JTextArea(actors);
		actorsList.setEditable(false);
		JScrollPane actorScrollPane = new JScrollPane(actorsList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JTextArea genreList = new JTextArea(genres);
		genreList.setEditable(false);
		JScrollPane genreScrollPane = new JScrollPane(genreList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		String[] intervalsMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "ALL MONTHS"};
		String[] intervalsYears = {"1999", "2000", "2001", "2002", "2003", "2004", "2005", "ALL YEARS"};

		class IntervalListener implements ActionListener {
			String intervalChoice = "";
			String month = "";
			String year = "";
			String beginDate = "";
			String endDate = ""; 

			public void actionPerformed(ActionEvent a) {
				JComboBox<?> cb = (JComboBox<?>)a.getSource();
				intervalChoice = (String)cb.getSelectedItem();
				
				month = intervalsMonths[intervalOptionMonth.getSelectedIndex()];
				year = intervalsYears[intervalOptionYear.getSelectedIndex()];

				if(intervalChoice.equals("01") || intervalChoice.equals("02") || intervalChoice.equals("03") || intervalChoice.equals("04") || intervalChoice.equals("05") ||
				intervalChoice.equals("06") || intervalChoice.equals("07") || intervalChoice.equals("08") || intervalChoice.equals("09") || intervalChoice.equals("10") || intervalChoice.equals("11") || 
				intervalChoice.equals("12") || intervalChoice.equals("ALL MONTHS")){
					month = intervalChoice; 
				}
				else{
					year = intervalChoice;
				}
				
				if(month.equals("ALL MONTHS")){
					beginDate = year + "-" + "01" + "-" + "01";
					endDate = year + "-" + "12" + "-" + "01";
				}
				else if(year.equals("ALL YEARS")){
					beginDate = "1999" + "-" + month + "-" + "01";
					endDate = "2003" + "-" + month + "-" + "01";
				}
				else{
					beginDate = year + "-" + month + "-" + "01";
					endDate = year + "-" + month + "-" + "01";
				}

				Connection conn = null; 
				try{ 
					Class.forName("org.postgresql.Driver");
					conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
					"csce315903_13user", "31590313");
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
				}
				String shows = ""; 
				String actors = "";
				String genres = "";

				String[] attributesByDate = findPopularAttributesByDate(conn, shows, actors, genres, beginDate, endDate, year, month);
				shows = attributesByDate[0];
				actors = attributesByDate[1];
				genres = attributesByDate[2];

				actorsList.setText(actors);
				genreList.setText(genres);
				showList.setText(shows);
				validate();
				repaint();
			}
		}
		//add filtering for duration (watch history in last month, week, etc)

		JLabel actorsTitle = new JLabel("TOP ACTORS");
		actorsTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		actorsTitle.setForeground(Color.WHITE);
		actorsTitle.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel genreTitle = new JLabel("TOP GENRES");
		genreTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		genreTitle.setForeground(Color.WHITE);
		genreTitle.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel showTitle = new JLabel("TOP CONTENT");
		showTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		showTitle.setForeground(Color.WHITE);
		showTitle.setHorizontalAlignment(SwingConstants.LEFT);

		
		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		intervalOptionMonth = new JComboBox<>(intervalsMonths);
		intervalOptionMonth.setPreferredSize(new Dimension (100, 100));
		intervalOptionMonth.setSelectedIndex(12);
		intervalOptionMonth.addActionListener(new IntervalListener());
		
		
		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		intervalOptionYear = new JComboBox<>(intervalsYears);
		intervalOptionYear.setPreferredSize(new Dimension (100, 100));
		intervalOptionYear.setSelectedIndex(7);
		intervalOptionYear.addActionListener(new IntervalListener());

		

		//add labels
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
		add(showTitle);

		//add text area
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(showScrollPane, c);
		add(showScrollPane);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 6;
		layout.setConstraints(actorsTitle, c);
		add(actorsTitle);

		c.gridx = 0;
		c.gridy = 7;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(actorScrollPane, c);
		add(actorScrollPane);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 10;
		layout.setConstraints(genreTitle, c);
		add(genreTitle);

		c.gridx = 0;
		c.gridy = 11;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(genreScrollPane, c);
		add(genreScrollPane);

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


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		JPanel buttonPanel = (JPanel)button.getParent();
		JPanel cardPanel = (JPanel)buttonPanel.getParent();
		CardLayout layout = (CardLayout)cardPanel.getLayout();
		if(button == searchBtn)
		{
			layout.show(cardPanel, "search");
		}
		else if (button == backBtn)
		{
			layout.show(cardPanel, "analystPanel");
		}
	}

	public String[] findPopularAttributes(Connection conn, String shows, String actors, String genres) {
		try{
			//create a statement object
			Statement stmt = conn.createStatement();
			//create an SQL statement
			String sqlStatementShows = "SELECT name, title, genre, no_views " +
			"FROM( " +
			"SELECT no_views, actors.CONTENT_ID, TITLE, GENRE, people_id "+ 
			"FROM( " + 
			"SELECT COUNT(*) as no_views, CONTENT_ID, TITLE, GENRE FROM "+ 
			"(SELECT view_id, content.content_id, TITLE, GENRE " + 
			"FROM VIEWS " + 
			"INNER JOIN CONTENT " + 
			"on CONTENT.content_id = views.content_id "+ 
			")v_c_i " + 
			"GROUP BY CONTENT_ID, TITLE, GENRE " + 
			")count_v_c_i " + 
			"INNER JOIN ACTORS " + 
			"On actors.content_id= count_v_c_i.content_id " + 
			")a " + 
			"INNER JOIN PEOPLE " + 
			"On a.people_id =  people.people_id " + 
			"Order by no_views desc " + 
			";";
			
			ResultSet Result = stmt.executeQuery(sqlStatementShows);

			String curShow = ""; 
			String curActors = ""; 
			String[] curGenres = {}; 
			while (Result.next()) {
				curShow = Result.getString("title")+"\n";
				if(!shows.contains(curShow)){
					shows += curShow;
				}
				curActors = Result.getString("name")+"\n";
				if(!actors.contains(curActors)){
					actors += curActors;
				}	
				curGenres = (Result.getString("genre").replace("\n", "")).split(",");
				for(int i = 0; i < curGenres.length; i++){
					if(!genres.contains(curGenres[i])){
						genres += curGenres[i] + "\n";
					}
				}
				
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}
		String[] attributes = {shows, actors, genres};
		return attributes;
	}

	public String[] findPopularAttributesByDate(Connection conn, String shows, String actors, String genres, String beginDate, String endDate, String year, String month) {
		try{
			//create a statement object
			Statement stmt = conn.createStatement();
			String sqlStatementShows = "";
			//create an SQL statement
		
			sqlStatementShows = "SELECT name, title, genre, no_views FROM( SELECT no_views, actors.CONTENT_ID, " +  
				"TITLE, GENRE, people_id FROM( SELECT COUNT(*) as no_views, CONTENT_ID, TITLE, GENRE FROM (SELECT view_id, " +
				"content.content_id, TITLE, GENRE FROM (SELECT * FROM VIEWS WHERE date_watched >= " +  "\'" + beginDate + "\'" + " and date_watched"  +
				"<= " + "\'" +endDate + "\'" + ")v INNER JOIN CONTENT on CONTENT.content_id = v.content_id )v_c_i GROUP BY CONTENT_ID, TITLE, GENRE )count_v_c_i " +
				"INNER JOIN ACTORS On actors.content_id= count_v_c_i.content_id )a INNER JOIN PEOPLE On a.people_id =  people.people_id " +
				"Order by no_views desc;";
	
			if(year.equals("ALL YEARS") && month.equals("ALL MONTHS")){ 
				sqlStatementShows = "SELECT name, title, genre, no_views " +
				"FROM( " +
				"SELECT no_views, actors.CONTENT_ID, TITLE, GENRE, people_id "+ 
				"FROM( " + 
				"SELECT COUNT(*) as no_views, CONTENT_ID, TITLE, GENRE FROM "+ 
				"(SELECT view_id, content.content_id, TITLE, GENRE " + 
				"FROM VIEWS " + 
				"INNER JOIN CONTENT " + 
				"on CONTENT.content_id = views.content_id "+ 
				")v_c_i " + 
				"GROUP BY CONTENT_ID, TITLE, GENRE " + 
				")count_v_c_i " + 
				"INNER JOIN ACTORS " + 
				"On actors.content_id= count_v_c_i.content_id " + 
				")a " + 
				"INNER JOIN PEOPLE " + 
				"On a.people_id =  people.people_id " + 
				"Order by no_views desc " + 
				";";
			}
			
			ResultSet Result = stmt.executeQuery(sqlStatementShows);
			String curShow = ""; 
			String curActors = ""; 
			String[] curGenres = {}; 
			while (Result.next()) {
				curShow = Result.getString("title");
				if(!shows.contains(curShow)){
					shows += Result.getString("title")+"\n";
				}
				curActors = Result.getString("name");
				if(!actors.contains(curActors)){
					actors += curActors+"\n";
				}	
				curGenres = (Result.getString("genre").replace("\n", "")).split(",");
				for(int i = 0; i < curGenres.length; i++){
					if(!genres.contains(curGenres[i])){
						genres += curGenres[i] + "\n";
					}
				}
				
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}
		String[] attributes = {shows, actors, genres};
		return attributes;
	}
}
