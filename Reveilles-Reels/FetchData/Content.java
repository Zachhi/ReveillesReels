package phase_2;

import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;

public class Content {
    private String ContentID;
    private String TitleType;
    private String Title;  //original title
    private int ReleaseDate;
    private int EndDate;
    private int Duration; //in minutes
    private String Genre;
    private double AvgRating;   //from external source
    private int NumVotes;   //num votes that went into computing AvgRating


    public String getContentID() {
        return this.ContentID;
    }

    public void setContentID(String ContentID) {
        this.ContentID = ContentID;
    }

    public String getTitleType() {
        return this.TitleType;
    }

    public void setTitleType(String TitleType) {
        this.TitleType = TitleType;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getReleaseDate() {
        return this.ReleaseDate;
    }

    public void setReleaseDate(int ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }

    public int getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(int EndDate) {
        this.EndDate = EndDate;
    }

    public int getDuration() {
        return this.Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public String getGenre() {
        return this.Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public double getAvgRating() {
        return this.AvgRating;
    }

    public void setAvgRating(double AvgRating) {
        this.AvgRating = AvgRating;
    }

    public int getNumVotes() {
        return this.NumVotes;
    }

    public void setNumVotes(int NumVotes) {
        this.NumVotes = NumVotes;
    }

    @Override
    public String toString() {
        return
            "$$"+getContentID() + "$$," +
            "$$"+ getTitle() + "$$," +
            getDuration() + "," +
            "$$"+getGenre() + "$$," +
            getReleaseDate() + "," +
            getEndDate() + "," +
            "$$"+getTitleType() + "$$," +
            getAvgRating() + "," +
            getNumVotes();
    }



    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Content)) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(ContentID, content.ContentID);    //only check contentID. 
    }
    

    /*
        fetchTitles() 
        Parses through csv to ensure there are no duplicates of ContentID and trash entries.
        Entries were removed if they did contain data for all attributes (ContentID, Title, releaseDate, Duration, Genre, Rating, and NumVotes)
        EXCEPT EndYear. If no EndYear exists null value of 0 is added. 

        Duplicate is found when ContentID equals another entrie's ContentID. The entry with the more recent release data is kept. If they
        are equal in ContentID and ReleaseData the entry that has already been parsed completely (addded to contentList) is kept. The other entry is skipped.

        Uses class Content for quality of life when implementing this function.
    */
    static List<Content> fetchTitles() throws Exception {
        Scanner scanner = new Scanner(new File("data/titles.csv"));
        Scanner valueScanner = null;
        int index = 0;
        List<Content> contentList = new ArrayList<>();

        scanner.nextLine(); 	// skip line with headers
        while (scanner.hasNextLine()) {
            valueScanner = new Scanner(scanner.nextLine());    // looks at a single line of csv
            valueScanner.useDelimiter("\t");
            Content entryContent = new Content();

            valueScanner.next();	// skip index value (0, 1, 2, 3...)

            while (valueScanner.hasNext()) {
                String data = valueScanner.next();
                data = data.trim();
                if (index == 0) {	// ContentID - alphanumeric - String
                    if (data.length() == 0) {
                        break;	// if no ContentID do not keep entry
                    }
                    entryContent.setContentID((String) data);

                } else if (index == 1) {	// TitleType - String
                    if (data.length() == 0) {
                        break;	// if no TitleType do not keep entry
                    }
                    entryContent.setTitleType((String) data);
                } else if (index == 2) {	// originalTitle - String
                    if (data.length() == 0) {
                        break;	// if no origTitle do not keep entry
                    }
                    entryContent.setTitle((String) data);
                } else if (index == 3) {	// Release Date - Int
                    if (data.length() == 0) {
                        break;    // if no origTitle do not keep entry
                    }
                    entryContent.setReleaseDate(Integer.parseInt(data));
                } else if (index == 4) {	// End Date - Int
                    if (data.length() == 0) {
                        data = "0";    // if no endYear use zero as null value
                    }
                    entryContent.setEndDate(Integer.parseInt(data));
                } else if (index == 5) {	// Duration(minutes) - Int
                    if (data.length() == 0) {
                        break;    // if no duration, do not keep entry
                    }
                    entryContent.setDuration(Integer.parseInt(data));
                } else if (index == 6) {	// Genres - String
                    if (data.length() == 0) {
                        break;    // if no Genre, do not keep entry
                    }
                    entryContent.setGenre((String) data);
                } else if (index == 7) {	// Year value (NOT USING)
                    // do aboslutely nothing. not keeping this value
                } else if (index == 8) {	// AvgRating Double
                    if (data.length() == 0) {
                        break;    // if no avgRating, do not keep entry
                    }
                    entryContent.setAvgRating(Double.parseDouble(data));
                } else if (index == 9) {	// NumVotes
                    if (data.length() == 0) {
                        break;     // if no numVotes, do not keep entry
                    }
                    entryContent.setNumVotes(Integer.parseInt(data));

                    // no missing values. check if titleID duplicate exists
                    if (contentList.contains(entryContent)) {	//if duplicate exists pick entry with most recent release
                        int indexOfDup = contentList.indexOf(entryContent);
                        Content dupEntry = contentList.get(indexOfDup);
                        if (dupEntry.getReleaseDate() >= entryContent.getReleaseDate()) {    //if dupEntry has more recent or equal release keep dupEntry in list. 
                            break;    //do not keep entryContent
                        }
                        else {
                            contentList.remove(indexOfDup);
                            contentList.add(entryContent);    // add entryContent if more recent release or no dupplicate
                        }
                        
                    }
                    else {    //no duplicate exists, add entry
                        contentList.add(entryContent);
                    }
                }
                index++;
            }
            index = 0;
        }

        scanner.close();
        return contentList;
    }
    
    public static void main(String[] args) throws Exception {

    }
}