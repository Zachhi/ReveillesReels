import java.awt.*;
import java.nio.file.WatchService;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;
import java.sql.*;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReccomendedContent extends JPanel implements ActionListener {
    public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final Color MAROON = new Color(80, 0, 0);
    private JButton searchBtn = new JButton("Search");
    private JButton backBtn = new JButton("Home");
	private Connection conn;
	private String clientID;


	public ReccomendedContent(String clientID) {
		super();
		setSize(WIDTH, HEIGHT); // width,height
		setBackground(MAROON);
		//setLayout(null);	If i set to null unable to see buttons
	
		this.clientID = "\'" + clientID + "\'";

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
			"csce315903_13user", "31590313");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		//All Content Button create
		searchBtn.setPreferredSize(new Dimension(100, 50));
		searchBtn.addActionListener(this);
		backBtn.addActionListener(this);

		//create title Label
		JLabel titLabel = new JLabel("Recommended Content");
		titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		titLabel.setForeground(Color.WHITE);

		JLabel forYouLabel = new JLabel("Viewer Beware");
		forYouLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		forYouLabel.setForeground((Color.WHITE));

		JLabel trendingLabel = new JLabel("Viewer's Choice");
		trendingLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		trendingLabel.setForeground((Color.WHITE));
		
		JTextArea trendingContent = new JTextArea(viewerChoice(conn));
		trendingContent.setEditable(false);

		JScrollPane trendingScroll = new JScrollPane(trendingContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JTextArea forYou = new JTextArea(viewerBeware(conn));
		forYou.setEditable(false);

		JScrollPane forYouScroll = new JScrollPane(forYou, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JLabel directorsLabel = new JLabel("Director's Choice");
		directorsLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		directorsLabel.setForeground(Color.WHITE);

		JTextArea directorsChoice = new JTextArea(directorsChoice(conn));
		directorsChoice.setEditable(false);

		JScrollPane directorsScroll = new JScrollPane(directorsChoice, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//add labels
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.weighty = 1;
		c.weightx = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(titLabel, c);
		add(titLabel);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(trendingScroll, c);
		add(trendingScroll);

		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(forYouScroll, c);
		add(forYouScroll);

		c.gridx = 6;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = 3;
		layout.setConstraints(directorsScroll, c);
		add(directorsScroll);

		// c.ipady = 100;
		// c.ipadx = 200;
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		layout.setConstraints(forYouLabel, c);
		add(forYouLabel);

		// c.ipady = 100;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		layout.setConstraints(trendingLabel, c);
		add(trendingLabel);

		c.gridx = 6;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		layout.setConstraints(directorsLabel, c);
		add(directorsLabel);

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

		try {
			conn.close();
			// JOptionPane.showMessageDialog(null,"Connection Closed.");
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
		}

	}

	public String viewerChoice(Connection conn) {
		String choice = "";
		try{
			// creates the viewers choice query
			Statement stmt = conn.createStatement();
			String sqlStatement = "SELECT title FROM (SELECT content_id FROM (SELECT genre, content.content_id"
			+ " FROM content LEFT JOIN (SELECT content_id"
			+ " FROM views WHERE client_id = " + clientID + ")client_views" 
			+ " ON client_views.content_id = content.content_id"
			+ " WHERE client_views.content_id IS NULL)genre_content_id_not_watched"
			+ " INNER JOIN (SELECT COUNT(*) as COUNT, GENRE, CLIENT_ID FROM (SELECT CLIENT_ID, GENRE FROM VIEWS"
			+ " INNER JOIN CONTENT ON VIEWS.CONTENT_ID = CONTENT.CONTENT_ID WHERE CLIENT_ID = " + clientID + ")VIEWS_COUNT_AND_GENRES" 
			+ " GROUP BY GENRE, CLIENT_ID ORDER BY COUNT DESC LIMIT 1)most_watched_genre ON genre_content_id_not_watched.genre = most_watched_genre.genre"
			+ " UNION DISTINCT"
			+ " SELECT content_id FROM (SELECT genre, content.content_id"
			+ " FROM content LEFT JOIN (SELECT content_id"
			+ " FROM views WHERE client_id = " + clientID + ")client_views"
			+ " ON client_views.content_id = content.content_id"
			+ " WHERE client_views.content_id IS NULL)genre_content_id_not_watched"
			+ " INNER JOIN (SELECT CLIENT_ID, GENRE, RATING" 
			+ " FROM VIEWS INNER JOIN CONTENT" 
			+ " ON VIEWS.CONTENT_ID = CONTENT.CONTENT_ID"
			+ " WHERE RATING = (SELECT MAX(RATING) FROM VIEWS WHERE CLIENT_ID = " + clientID + ")"
			+ " AND CLIENT_ID = " + clientID + " LIMIT 1)highest_rated_genre ON genre_content_id_not_watched.genre = highest_rated_genre.genre"
			+ " UNION DISTINCT"
			+ " SELECT content_id FROM (SELECT people_id, actors.content_id" 
			+ " FROM actors LEFT JOIN (SELECT content_id"
			+ " FROM views WHERE client_id = " + clientID + ")client_views"
			+ " ON client_views.content_id = actors.content_id"
			+ " WHERE client_views.content_id IS NULL)actors_content_id_not_watched"
			+ " INNER JOIN (SELECT COUNT(*) as COUNT, people_id, client_id FROM (SELECT client_id, people_id FROM VIEWS INNER JOIN actors"
			+ " ON views.content_id = actors.content_id WHERE client_id = " + clientID + ") VIEWS_COUNT_AND_ACTOS GROUP BY people_id, client_id ORDER" 
			+ " BY COUNT DESC LIMIT 1)most_watched_actor ON actors_content_id_not_watched.people_id = most_watched_actor.people_id"
			+ " UNION DISTINCT"
			+ " SELECT content_id FROM (SELECT people_id, actors.content_id" 
			+ " FROM actors LEFT JOIN (SELECT content_id FROM views WHERE client_id = " + clientID + ")client_views"
			+ " ON client_views.content_id = actors.content_id WHERE client_views.content_id IS NULL)actors_content_id_not_watched"
			+ " INNER JOIN (SELECT client_id, people_id, rating FROM views INNER JOIN actors ON views.content_id = actors.content_id"
			+ " WHERE rating = (SELECT MAX(rating) FROM views WHERE client_id = " + clientID + ") AND client_id = " + clientID + " LIMIT 1"
			+ ")highest_rated_actor ON actors_content_id_not_watched.people_id = highest_rated_actor.people_id"
			+ " UNION DISTINCT"
			+ " SELECT content_id FROM (SELECT people_id, staff.content_id FROM STAFF LEFT JOIN (SELECT content_id FROM views WHERE client_id = " + clientID + ")client_views"
			+ " ON client_views.content_id = STAFF.content_id WHERE client_views.content_id IS NULL)staff_content_id_not_watched"
			+ " INNER JOIN (SELECT COUNT(*) as COUNT, PEOPLE_ID, CLIENT_ID FROM (SELECT CLIENT_ID, PEOPLE_ID FROM VIEWS INNER JOIN STAFF"
			+ " ON VIEWS.CONTENT_ID = STAFF.CONTENT_ID WHERE CLIENT_ID = " + clientID + " )VIEWS_COUNT_AND_STAFF GROUP BY PEOPLE_ID, CLIENT_ID" 
			+ " ORDER BY COUNT DESC LIMIT 1)most_watched_staff ON staff_content_id_not_watched.people_id = most_watched_staff.people_id"
			+ " UNION DISTINCT"
			+ " SELECT content_id FROM (SELECT PEOPLE_ID, staff.content_id"
			+ " FROM STAFF LEFT JOIN (SELECT content_id FROM views WHERE client_id = " + clientID + ")client_views ON client_views.content_id = STAFF.content_id"
			+ " WHERE client_views.content_id IS NULL)staff_content_id_not_watched INNER JOIN (SELECT CLIENT_ID, PEOPLE_ID, RATING FROM VIEWS" 
			+ " INNER JOIN STAFF ON VIEWS.CONTENT_ID = STAFF.CONTENT_ID WHERE RATING = (SELECT MAX(RATING) FROM VIEWS WHERE CLIENT_ID = " + clientID + ")"
			+ " AND CLIENT_ID = " + clientID + " LIMIT 1)highest_rated_staff ON staff_content_id_not_watched.people_id = highest_rated_staff.people_id"
			+ " UNION DISTINCT"
			+ " SELECT other_client_viewed.content_id FROM (SELECT CONTENT_ID FROM VIEWS" 
			+ " WHERE CLIENT_ID = (SELECT OVERLAPPING_client FROM (SELECT COUNT(*) as count, id_likes, overlapping_client" 
			+ " FROM (SELECT CLIENT_LIKES.CLIENT_ID as ID_LIKES, ALL_LIKES.CLIENT_ID as overlapping_client, CLIENT_LIKES.CONTENT_ID" 
			+ " FROM (SELECT CONTENT_ID, CLIENT_ID FROM VIEWS WHERE RATING >= 3) ALL_LIKES INNER JOIN" 
			+ " (SELECT CONTENT_ID, CLIENT_ID FROM VIEWS WHERE RATING >= 3 and CLIENT_ID = " + clientID + ") CLIENT_LIKES"
			+ " ON CLIENT_LIKES.CONTENT_ID = ALL_LIKES.CONTENT_ID) VIEWER_PAIRS GROUP BY ID_LIKES, OVERLAPPING_client ORDER BY COUNT DESC"
			+ " LIMIT 1) over_client))other_client_viewed LEFT JOIN (SELECT content_id" 
			+ " FROM views WHERE client_id = " + clientID + ")client_views ON client_views.content_id = other_client_viewed.content_id" 
			+ " WHERE client_views.content_id IS NULL)content_id_list INNER JOIN content ON content_id_list.content_id = content.content_id LIMIT 50;";

			ResultSet result = stmt.executeQuery(sqlStatement);
			while (result.next()) {
				choice += result.getString("title")+"\n";
			}
			
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}

		return choice;
	}

	public String viewerBeware(Connection conn) {
		String beware = "";
		try{
			//create a statement object
			Statement stmt = conn.createStatement();
			//create an SQL statement
			// creates the beware list of titles 
			String sqlStatement = "SELECT title FROM (SELECT other_client_viewed.content_id"
			+ " FROM (SELECT CONTENT_ID FROM"
			+ " VIEWS" 
			+ " WHERE CLIENT_ID ="
			+ " (SELECT OVERLAPPING_client FROM (SELECT COUNT(*) as count, id_likes, overlapping_client" 
			+ " FROM (SELECT CLIENT_LIKES.CLIENT_ID as ID_LIKES, ALL_LIKES.CLIENT_ID as overlapping_client, CLIENT_LIKES.CONTENT_ID"
			+ " FROM (SELECT CONTENT_ID, CLIENT_ID"
			+ " FROM VIEWS"
			+ " WHERE RATING >= 3) ALL_LIKES"
			+ " INNER JOIN"
			+ " (SELECT CONTENT_ID, CLIENT_ID" 
			+ " FROM VIEWS"
			+ " WHERE RATING >= 3 and CLIENT_ID = " + clientID + ") CLIENT_LIKES"
			+ " ON CLIENT_LIKES.CONTENT_ID = ALL_LIKES.CONTENT_ID) VIEWER_PAIRS"
			+ " GROUP BY ID_LIKES, OVERLAPPING_client"
			+ " ORDER BY COUNT DESC" 
			+ " LIMIT 1) over_client))other_client_viewed"
			+ " LEFT JOIN" 
			+ " (SELECT content_id"
			+ " FROM views WHERE client_id = " + clientID + ")client_views"
			+ " ON client_views.content_id = other_client_viewed.content_id"
			+ " WHERE client_views.content_id IS NULL)content_id_list"
			+ " INNER JOIN content ON content_id_list.content_id = content.content_id LIMIT 50;";
			
			//send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlStatement);
			while (result.next()) {
				beware += result.getString("title")+"\n";
			}
			
			
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}
		return beware;
	}

	public String directorsChoice(Connection conn)
	{
		String directorsChoice = "";
		String topDirectors = "";
		String topDirectorsArray [];
		String topGenres = "";
		String topGenresArray [];
		String WatchHistory = "";
		String WatchHistoryArray [];
		String topMoviesByDirector = "";
		String topMoviesByDirectorArray [];
		String topMoviesByGenre = "";
		String topMoviesByGenreArray [];
		boolean found = false;

		try{
			//create a statement object
			Statement stmtTopDirector = conn.createStatement();
			Statement stmtTopGenre = conn.createStatement();
			Statement stmtWatchHistory = conn.createStatement();
			Statement stmtTopMovieByDirector = conn.createStatement();
			Statement stmtTopMovieByGenre = conn.createStatement();

			//Statement to get all of a clients directors that they have watched, in order from favorite to least favorite
			String getTopDirectorsSQL = "SELECT AVG(rating) as AVG_rating, COUNT(*) as number_watched, staff.people_id as director_id"
			+ " FROM (SELECT client_id, content.content_id as content_id, rating, title"
			+ " FROM (SELECT content_id, client_id, rating FROM views WHERE CLIENT_ID = " + clientID + ")view_sq"
			+ " INNER JOIN content ON view_sq.content_id = content.content_id )final_sq"
			+ " INNER JOIN staff ON final_sq.content_id = staff.content_id"
			+ " WHERE ROLE = 'director'"
			+ " GROUP BY director_id ORDER BY AVG_rating DESC, number_watched DESC;";
			
			//now, get all directors and put it into a string separated by spaces
			ResultSet resultTopDirectors = stmtTopDirector.executeQuery(getTopDirectorsSQL);
			while (resultTopDirectors.next()) 
			{
				topDirectors += resultTopDirectors.getString("director_id")+" ";
			}
			//put it into array
			topDirectorsArray = topDirectors.split(" ");

			//Statement to get all of clients genres that they have watched, in order from favorite to least favorite
			String getTopGenreSQL = "SELECT AVG(rating) as AVG_rating, COUNT(*) as number_watched, content.genre"
			+ " FROM (SELECT client_id, content.content_id as content_id, rating, title"
			+ " FROM (SELECT content_id, client_id, rating FROM views WHERE CLIENT_ID = " + clientID + ")view_sq"
			+ " INNER JOIN content ON view_sq.content_id = content.content_id )final_sq"
			+ " INNER JOIN staff ON final_sq.content_id = staff.content_id"
			+ " INNER JOIN content ON final_sq.content_id = content.content_id"
			+ " WHERE ROLE = 'director'"
			+ " GROUP BY content.genre ORDER BY AVG_rating DESC, number_watched DESC;";

			//now, get all genres and put it into a string separated by spaces
			ResultSet resultTopGenres = stmtTopGenre.executeQuery(getTopGenreSQL);
			while(resultTopGenres.next())
			{
				topGenres += resultTopGenres.getString("genre")+" ";
			}
			topGenresArray = topGenres.split(" ");

			//Statement to get all of clients watch history
			String getWatchHistorySQL = "SELECT title, date_watched, client_id, content.content_id FROM"
			+ " (SELECT content_id, client_id, date_watched FROM VIEWS WHERE CLIENT_ID = " + clientID + ")"
			+ " view_sq INNER JOIN CONTENT On content.content_id = view_sq.content_id;";

			//now, get all watch history and put it into a string separated by spaces
			ResultSet resultWatchHistory = stmtWatchHistory.executeQuery(getWatchHistorySQL);
			while (resultWatchHistory.next())
			{
				WatchHistory += resultWatchHistory.getString("title")+",";
			}
			WatchHistoryArray = WatchHistory.split(",");

			//if user hasnt watched anything return the empty string before going into the while loop
			//otherwise we get an error accessing database
			if(WatchHistory.equals(""))
				return directorsChoice;

			int i = 0;
			int k = 0;
			while(!found)
			{
				//if we havent reached the end of all favorite directors
				if(i < topDirectorsArray.length)
				{
					String getTopMovieByDirectorSQL = "SELECT title, average_rating as rating"
					+ " FROM(SELECT staff.people_ID, content_id FROM staff WHERE staff.people_ID = '" + topDirectorsArray[i] + "')view_sq "
					+ " INNER JOIN content ON view_sq.content_id = content.content_id" 
					+ " ORDER BY rating DESC;";

					ResultSet resultTopMovieByDirector = stmtTopMovieByDirector.executeQuery(getTopMovieByDirectorSQL);
					while(resultTopMovieByDirector.next())
					{
						topMoviesByDirector += resultTopMovieByDirector.getString("title")+",";
					}
					topMoviesByDirectorArray = topMoviesByDirector.split(",");

					for(int j = 0; j < topMoviesByDirectorArray.length; j++)
					{
						if(!Arrays.asList(WatchHistoryArray).contains(topMoviesByDirectorArray[j]))
						{
							directorsChoice = "'" + topMoviesByDirectorArray[j] + "' by your number " + (i+1) + " director: " + topDirectorsArray[i];
							found = true;
							return directorsChoice;
						}
					}
					i+=1;
				}
				//if we reached the end of all favorite directors, and client watched every movie by them
				//we now reccomend the top movie in their favorite genre
				else
				{
					String getTopMovieByGenreSQL = "SELECT title, average_rating as rating, content.genre FROM content WHERE genre = '" + topGenresArray[k]
					+ "' ORDER BY rating DESC;";
					
					ResultSet resultTopMovieByGenre = stmtTopMovieByGenre.executeQuery(getTopMovieByGenreSQL);
					while(resultTopMovieByGenre.next())
					{
						topMoviesByGenre += resultTopMovieByGenre.getString("title")+",";
					}
					topMoviesByGenreArray = topMoviesByGenre.split(",");

					for(int j = 0; j < topMoviesByGenreArray.length; j++)
					{
						if(!Arrays.asList(WatchHistoryArray).contains(topMoviesByGenreArray[j]))
						{
							directorsChoice = "You have already seen every title by all your watched directors...\n" +"''" + topMoviesByGenreArray[j] + 
							"' in your number " + (k+1) + " genre: " + topGenresArray[k];
							found = true;
							return directorsChoice;
						}
					}
					k+=1;
				}
			}
		
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error accessing Database.");
		}

		return directorsChoice;
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
			layout.show(cardPanel, "clientPanel");
		}
	}
}
