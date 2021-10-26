package phase_2;

//create method to go through the rows of crew.csv and principals.csv
// these files will eventually fill the actors and staff tables
// this first method will analyze the types of the data in each row
import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;
import java.sql.*;

public class Sanitizer 
{  
    

    public static void main(String[] args) throws Exception  
    {  
        Connection conn = null;
        String teamNumber = "13";
        String sectionNumber = "903";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        String userName = "csce315" + sectionNumber + "_" + teamNumber + "user";
        String userPassword = "31590313";

        try {
            conn = DriverManager.getConnection(dbConnectionString, userName, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        //fetchCrew
        HashSet<String> staffSet;
        HashSet<String> actorSet = new HashSet<String>();
        HashSet<String> viewsSet;

        staffSet = Crew.fetchCrew();
        ActorsAndStaff.fetchPrincipals(staffSet, actorSet);
        viewsSet = Ratings.fetchRatings();
        HashMap<String, String> peopleMap = People.fetchNames();

        Iterator<String> i = staffSet.iterator();
        try {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO staff (staff_id, content_id, role) VALUES (?,?,?);");
            while (i.hasNext()) {
                String[] row = i.next().split(",");
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                pstmt.setString(3, row[2]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Staff done");

        //fetchActors
        i = actorSet.iterator();
        try {
            // create a statement object
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO actors (actor_id, content_id, character, gender) VALUES (?,?,?,?);");
            while (i.hasNext()) {
                String[] row = i.next().split(",");
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                pstmt.setString(3, row[2]);
                pstmt.setString(4, row[3]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Actor done");

        //fetch Views
        i = viewsSet.iterator();
        try {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO views (client_id, content_id, date_watched, rating, view_id) VALUES (?,?,?,?,?);");
            while (i.hasNext()) {
                String[] row = i.next().split(",");
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                pstmt.setDate(3, java.sql.Date.valueOf(row[2]));
                pstmt.setFloat(4, Float.parseFloat(row[3]));
                pstmt.setInt(5, Integer.parseInt(row[4]));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Views done");

        //fetchPeople
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO people (people_id, name, birth_date, death_date) VALUES (?,?,?,?);");
            for (String value : peopleMap.values()) {
                String[] row = value.split(",");
                pstmt.setString(1, row[0]);
                pstmt.setString(2, row[1]);
                if (row[2].equals("-1")) {
                    pstmt.setNull(3, Types.NULL);
                } else {
                    pstmt.setInt(3, Integer.parseInt(row[2]));
                }
                if (row[3].equals("-1")) {
                    pstmt.setNull(4, Types.NULL);
                } else {
                    pstmt.setInt(4, Integer.parseInt(row[3]));
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("people done");

        //fetchTitles
        List<Content> contentList = Content.fetchTitles();
        try {   
                // Keeping below code to insert without using Batch for safety. (Below code has worked and executed.)
                // create a statement object
                // Statement stmt = conn.createStatement();
                //System.out.println(contentList.size());
                // String entry = contentList.get(k).toString();  
                
                // Running a query
                // String sqlStatement = "INSERT INTO content VALUES (" + entry + ");";
                //System.out.println(sqlStatement);
                // send statement to DBMS
                // int resultSet = stmt.executeUpdate(sqlStatement);

                // resultSet.close();
                // stmt.close();
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);  
            for (int k = 0; k < contentList.size(); k++) {
                String entry = contentList.get(k).toString(); 
                String sqlStatement = "INSERT INTO content2 VALUES (" + entry + ");";
                stmt.addBatch(sqlStatement);
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
        }
        
    }  
}  


