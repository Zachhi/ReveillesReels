package phase_2;

import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;


public class Crew {
    

    static HashSet<String> fetchCrew() throws Exception
    {
      int index = 0;
      String movieId = null;
      
      Scanner sc = new Scanner(new File("data/crew.csv"));	  // parsing a CSV file into Scanner class constructor
      sc.useDelimiter("\t");	// sets the delimiter pattern
      sc.nextLine();
      HashSet<String> tableSet = new HashSet<String>();		// made a HashSet in order to avoid duplicates
  
      while (sc.hasNextLine()) {
        Scanner valSc = new Scanner(sc.nextLine());
        valSc.useDelimiter("\t");
  
        while (valSc.hasNext()) {
          String item = valSc.next();
          if (index == 0) {
            // nothing
          } else if (index == 1) {
            movieId = item;
          } else if (index == 2) {
            String[] directorValues = item.split(",");
            for (int i = 0; i < directorValues.length; i++) {
              if (directorValues[i].length() == 0) {
                continue;
              } else {
                tableSet.add(directorValues[i]  + "," + movieId + "," + "director");
              }
            }
          } else if (index == 3) {
            String[] writerValues = item.split(",");
            for (int i = 0; i < writerValues.length; i++) {
              if (writerValues[i].length() == 0) {
                continue;
              }
              tableSet.add(writerValues[i] + "," + movieId  +  "," + "writer");
            }
          }
          index++;
        }
        index = 0;
      }
      sc.close(); 
      return tableSet; 
    }
}
