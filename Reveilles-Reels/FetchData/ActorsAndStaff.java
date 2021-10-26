package phase_2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;

public class ActorsAndStaff {
    static void fetchPrincipals(HashSet<String> staffSet, HashSet<String> actorSet) throws Exception {
        Scanner sc = new Scanner(new File("data/principals.csv"));  
        sc.useDelimiter("\t");   //sets the delimiter pattern 
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] values = line.split("\t");
           
            String contentID = "";
            String staffID = "";
            boolean actor = false;
            boolean staff = false;
            String gender = "";
            String role = "";
            ArrayList<String> characters = new ArrayList<String>();

            for (int i = 0; i < values.length; i++) {
                if (i == 1) {   // titleID to content ID
                    contentID = values[i];
                } else if (i == 2) {    // nconst to staff ID
                    staffID = values[i];
                } else if (i == 3) {    // category: actor / director / writer / etc
                    if (values[i].equals("actor") || values[i].equals("actress")) {
                        actor = true;
                        if (values[i].equals("actor")) {
                            gender = "male";
                        } else if (values[i].equals("actress")) {
                            gender = "female";
                        }
                    } else {
                        staff = true;
                        role = values[i];
                    }
                } else if (i == 5) {    // characters array
                    values[i] = values[i].replace("\"[", "");   //remove the brackets from the array
                    values[i] = values[i].replace("]\"", "");

                    String[] charactersString = values[i].split(",");
                    for (int j = 0; j < charactersString.length; j++) {
                        charactersString[j] = charactersString[j].replace("\"", "");
                        charactersString[j] = charactersString[j].replace("\'", "\"");
                        characters.add(charactersString[j]); 
                    }
                }
            }

            if (actor == true) {
                // insert staff ID, content ID, character, gender
                for (int i = 0; i < characters.size(); i++ ){
                    actorSet.add(staffID + "," + contentID + "," + characters.get(i) + "," + gender);
                }

            } else if (staff == true) {
                // insert staff ID, content ID, role
                staffSet.add(staffID + "," + contentID + "," + role);
            }

            actor = false;
            staff = false;
            
        }
    }
}
