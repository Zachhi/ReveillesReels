import java.awt.*;
import java.nio.file.WatchService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.Year;
import java.util.*;
import javax.swing.JComboBox;

public class AnalysisOfInterest extends JPanel implements ActionListener {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    public static final Color MAROON = new Color(80, 0, 0);

    JButton backBtn = new JButton("Home");
    JButton moviesFreshTom = new JButton("Fresh Tomatoes Content ID Input");
    JTextField movieALabel = new JTextField("Content ID for Content A:");
    JTextField movieAText = new JTextField(15);
    JTextField movieBLabel = new JTextField("Content ID for Content B");
    JTextField movieBText = new JTextField(15);
    String hollyWoodPairs = "";
    String movieA = "";
    String movieB = "";
    String freshTomatoe = "";
    String cultClassics = "";
    JTextArea freshTomList = new JTextArea(freshTomatoe);
    Connection conn;


    public AnalysisOfInterest() {
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
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        hollywoodPairs();
        cultClassics();

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        // BackButton Button create
        //backBtn.setPreferredSize(new Dimension(100, 50));
        backBtn.addActionListener(this);
        moviesFreshTom.setPreferredSize(new Dimension(50, 50));
        moviesFreshTom.addActionListener(this);

        // create title Label
        JLabel titLabel = new JLabel("Analysis of Interest");
        titLabel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        titLabel.setForeground(Color.WHITE);
        titLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea hwPairsList = new JTextArea(hollyWoodPairs);
        hwPairsList.setEditable(false);
        JScrollPane hwpScrollPane = new JScrollPane(hwPairsList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //JTextArea freshTomList = new JTextArea(freshTomatoe);
        freshTomList.setEditable(false);
        JScrollPane ftScrollPane = new JScrollPane(freshTomList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JTextArea cultClassicsList = new JTextArea(cultClassics);
        cultClassicsList.setEditable(false);
        JScrollPane ccScrollPane = new JScrollPane(cultClassicsList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        

       
        // add filtering for duration (watch history in last month, week, etc)

        JLabel hwpTitle = new JLabel("HollyWood Pairs");
        hwpTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        hwpTitle.setForeground(Color.WHITE);
        hwpTitle.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel ftTitle = new JLabel("Fresh Tomatoe");
        ftTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        ftTitle.setForeground(Color.WHITE);
        ftTitle.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel ccTitle = new JLabel("Cult Classics");
        ccTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        ccTitle.setForeground(Color.WHITE);
        ccTitle.setHorizontalAlignment(SwingConstants.LEFT);


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
        layout.setConstraints(hwpTitle, c);
        add(hwpTitle);

        // add text area
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        layout.setConstraints(hwpScrollPane, c);
        add(hwpScrollPane);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weighty = 0;
        c.weightx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 6;
        layout.setConstraints(ftTitle, c);
        add(ftTitle);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0;
        c.weightx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 7;
        layout.setConstraints(moviesFreshTom, c);
        add(moviesFreshTom);

        c.gridx = 0;
        c.gridy = 8;
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        layout.setConstraints(ftScrollPane, c);
        add(ftScrollPane);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weighty = 0;
        c.weightx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 11;
        layout.setConstraints(ccTitle, c);
        add(ccTitle);

        c.gridx = 0;
        c.gridy = 12;
        c.weighty = 1;
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        layout.setConstraints(ccScrollPane, c);
        add(ccScrollPane);



        // Buttons Added
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        layout.setConstraints(backBtn, c);
        add(backBtn);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        JPanel buttonPanel = (JPanel) button.getParent();
        JPanel cardPanel = (JPanel) buttonPanel.getParent();
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        if (button == backBtn) {
            layout.show(cardPanel, "analystPanel");
        }
        else if (button == moviesFreshTom) {
            JPanel panel = new JPanel(new GridLayout(2, 3));
            movieALabel.setEditable(false);
            movieBLabel.setEditable(false);

            panel.add(movieALabel);
            panel.add(movieAText);
            panel.add(movieBLabel);
            panel.add(movieBText);
            JOptionPane.showMessageDialog(null, panel);

            //create query and execute based on input of ContentIDs for movie A and Movie B
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315903_13db",
                        "csce315903_13user", "31590313");
            } catch (Exception es) {
                es.printStackTrace();
                System.err.println(es.getClass().getName() + ": " + es.getMessage());
                System.exit(0);
            }

            //String freshTomatoe = "";
            String movieAContent_ID = "\'" + movieAText.getText() + "\'";
            String movieBContent_ID = "\'" + movieBText.getText() + "\'";

            freshTomato(movieBContent_ID, movieAContent_ID);
        }
    }

    public void hollywoodPairs() {
        try {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement for HollyWood Pairs
            String sqlStatementHwp = "SELECT people_ID1, people_ID2, name1, people.name as name2 FROM"
            + " (SELECT people_ID1, people_ID2, people.name as name1 FROM(SELECT AVG(average_rating)"
            + " as avgrating, PEOPLE_ID1, PEOPLE_ID2 FROM"
            + " (SELECT actors.content_ID as content_ID, actormovierating.people_ID as people_ID1,"
            + " actors.people_ID as people_ID2, average_rating FROM (SELECT content.content_ID as content_ID,"
            + " people_ID, average_rating FROM content INNER JOIN actors ON content.content_ID ="
            + " actors.content_ID)actormovierating INNER JOIN actors on actormovierating.content_ID ="
            + " actors.content_ID WHERE actormovierating.people_ID <> actors.people_ID)bothactors_rating"
            + " GROUP BY people_ID1, people_ID2 ORDER BY avgrating DESC LIMIT 10)"
            + " hwp_peopleID INNER JOIN people ON hwp_peopleID.people_ID1 = people.people_ID)name_people1" 
            + " INNER JOIN people ON name_people1.people_ID2 = people.people_ID;";
            // System.out.println(sqlStatementHwp);
            ResultSet result1 = stmt.executeQuery(sqlStatementHwp);

            while (result1.next()) {
                hollyWoodPairs+= result1.getString("name1") + " and " + result1.getString("name2") + "\n";
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
    }

    public void cultClassics() {
        try {
            // create an SQL statement for Cult Classics 
            Statement stmt3 = conn.createStatement();
            String sqlStatementCc = "SELECT COUNT(*) as COUNT, TITLE FROM"
            + " (SELECT client_id, content.content_id as content_id, rating, title"
            + " FROM views INNER JOIN content ON views.content_id = content.content_id WHERE rating > 3)"
            + " high_viewer_rating GROUP BY title ORDER BY count DESC LIMIT 10;";
            ResultSet result3 = stmt3.executeQuery(sqlStatementCc);

            while (result3.next()) {
                cultClassics += result3.getString("title") + "\n";
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
    }

    public void freshTomato(String movieBContent_ID, String movieAContent_ID) {
        try {

            //check if any if the fields for movieA/B ContentID are blank. If so dont attempt query
            if (movieBContent_ID.isEmpty() || movieBContent_ID.isBlank() || movieAContent_ID.isEmpty() || movieAContent_ID.isBlank()) {
                JOptionPane.showMessageDialog(null, "Content IDs must be provided for Title A and Title B");
                freshTomList.setText(freshTomatoe);
            }
            else {
            // TOMATOES:
            Statement stmtB = conn.createStatement();
            Statement stmtA = conn.createStatement();

            //Make sure that both inputted movies actually have ratings that are >= 4
            String sqlStatementMovieA = "SELECT CLIENT_ID as Clientid FROM VIEWS WHERE RATING >= 4 and CONTENT_ID = " + movieAContent_ID + " LIMIT 10;"; 
            String sqlStatementMovieB = "SELECT CLIENT_ID as Clientid FROM VIEWS WHERE RATING >= 4 and CONTENT_ID = " + movieBContent_ID + " LIMIT 10;";

            ResultSet resultMovieA = stmtA.executeQuery(sqlStatementMovieA);
            ResultSet resultMovieB = stmtB.executeQuery(sqlStatementMovieB);

            while (resultMovieA.next()) {
                movieA += resultMovieA.getString("Clientid") + "\n";
            }
            while (resultMovieB.next()) {
                movieB += resultMovieB.getString("Clientid") + "\n";
            }
        
            String base = "";
            // If either of the movies does not have any ratings >= 4, then put an error into the GUI box
            if(movieA.length() == 0 || movieB.length() == 0){
                freshTomList.setText("ERROR; no views >= 4.");
            }
            else{
                int count = 1;
                Statement stmtBase = conn.createStatement();
                String baseSql = "";
                ResultSet resultBase;
                String out;
                String baseSqlContentB;
                String select= "";
                // count keeps track of the "layers" (or edges if you want to think of it as a graph problem)
                while(count > 0){
                    out = "";
                    base = "";
                    // base case; where one client has watched both movies; there is only one edge
                    if(count == 1){
                        baseSql = "SELECT movie_a_views.content_id as contentida, movie_a_views.client_id as clientida1, views_4.CONTENT_ID as " + 
                        "Othercontenta1 FROM(SELECT CLIENT_ID, CONTENT_ID FROM VIEWS WHERE RATING >= 4 and CONTENT_ID = " + movieAContent_ID +  ")movie_a_views inner join " +
                        "(SELECT * FROM views WHERE RATING >= 4)views_4 on movie_a_views.client_id = views_4.client_id where movie_a_views.content_id <> views_4.content_id"; 

                        select = "SELECT contentida, clientida1, Othercontenta1";
                    }
                   
                    if(count != 1){
                        // get the clients that have watched the other content
                        baseSql = select + " , views_4.client_id as clientida" + (count) + " FROM (" + baseSql + ")t_" + 
                                  count + " inner join (SELECT * FROM VIEWS WHERE RATING >= 4)views_4 on views_4.content_id = " + 
                                  "t_" + count + ".othercontenta" + (count -1) + " WHERE " + "t_" + count + ".clientida" + (count -1) + 
                                  " <> views_4.client_id";
                        select += ", clientida" + count;

                        // get content for the above clients
                        baseSql = select + " , views_4.content_id as othercontenta" + (count)+ " FROM (" + baseSql + ")t_" + 
                                   count +  count + " inner join (SELECT * FROM VIEWS WHERE RATING >= 4)views_4 on views_4.client_id = " 
                                   + "t_" + count + count + ".clientida" + (count) + " WHERE "  + "t_" + count + count + ".othercontenta" 
                                   + (count -1) + " <> views_4.content_id";

                        select += ", othercontenta" + count;
                        
                    }
                    // determine if movie B is in the above content
                    baseSqlContentB = select + " FROM (" + baseSql + ")basesql where othercontenta" + count + " = " +  movieBContent_ID;
        
                    resultBase = stmtBase.executeQuery(baseSqlContentB + " LIMIT 10;");
                    // if you want to see the queries that each iteration is making:
                    //System.out.println("count:  " + count);
                    //System.out.println(baseSqlContentB);

                    // get the columns generated by each iteration to print out the path from movie a to movie b
                    while (resultBase.next()) {
                        //System.out.println(resultBase);
                        base = "";
                        base += "CONTENT_ID A: " + resultBase.getString("contentida");
                    
                        for(int i = 1; i <= count; i ++){
                            base += " , CLIENT_ID: " + resultBase.getString("clientida" + (i));
                            if(i != count){
                                base += " , CONTENT_ID: " + resultBase.getString("othercontenta" + (i)) ; 
                            } 
                            else{ 
                                out += base +  ", CONTENT_ID B: " + resultBase.getString("Othercontenta" + count) + "\n";
                            }
                        } 
                    }
                    // if the length of the output is > 0, then we found a path, exit while loop
                    if(out.length() != 0){
                        freshTomList.setText(out);
                        count = 0;
                    }
                    // if the length of the output is < 0, then do another iteration
                    else{ 
                        count += 1;
                    }
                }    
            }
        }
        
            try {
                conn.close();
                // JOptionPane.showMessageDialog(null,"Connection Closed.");
            } catch (Exception et) {
                JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
            }
        }
        catch (Exception em) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
    }
}
