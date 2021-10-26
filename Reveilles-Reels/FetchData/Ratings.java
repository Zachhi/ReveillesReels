package phase_2;
import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;

public class Ratings {
    static HashSet<String> fetchRatings() throws Exception {
        String customerID = "";    // declare the 4 values we will be fetching on each line
        String rating = "";
        String date = "";
        String titleID = "";
        HashSet<String> tableSet = new HashSet<String>();

        Scanner mainScanner = new Scanner(new File("data/customer_ratings.csv"));   // scanner to go down the csv until bottom                                                                       
        mainScanner.nextLine();     // skip the column titles
        int viewID = 0;
        while (mainScanner.hasNextLine()) {
            int index = 1;     // set index to 1 before next while loop. Not 0 since we call next() down below to skip the first value
            Scanner subScanner = new Scanner(mainScanner.nextLine()); // scanner to go left to right of a line until no characters left
            subScanner.next();    // skip the first value in each line since it is useless to us

            while (subScanner.hasNext()) {
                String currVal = subScanner.next();    // store the next value of the line we are currently on
                if (index == 1)    // customer id eg. 1488844
                {
                    customerID = currVal;
                } else if (index == 2)    // rating eg. 2.5
                {
                    rating = currVal;
                } else if (index == 3)    // date eg. 2005-09-06
                {
                    date = currVal;
                } else    // title id eg. tt0389605
                {
                    titleID = currVal;
                }

                index++;
            }
            tableSet.add(customerID + "," + titleID + "," + date + "," + rating + "," + viewID);
            viewID++;
        }
        
        return tableSet;
    }
}