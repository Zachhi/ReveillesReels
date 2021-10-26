package phase_2;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;

public class People {
    public static HashMap<String, String> fetchNames() throws Exception {
        Scanner sc = new Scanner(new File("data/names.csv"));
        HashMap<String, String> data = new HashMap<String, String>();
        sc.nextLine();    // skip header line
        sc.useDelimiter("\t");    // split line on tabs
        while (sc.hasNextLine()) {
            String id = "";
            String name = "";
            String bYear = "";
            String dYear = "";
            String jobs = "";
            int index = 0;
            Scanner val_sc = new Scanner(sc.nextLine());
            val_sc.useDelimiter("\t");
            while (val_sc.hasNext()) {
                String item = val_sc.next();
                if (index == 0) {
                    // nothing
                } else if (index == 1) {
                    id = item;
                } else if (index == 2) {
                    item = item.replace(",", "");
                    name = item;
                } else if (index == 3) {
                    if (item.equals("")) {
                        bYear = "-1";
                    } else {
                        bYear = item;
                    }     
                } else if (index == 4) {
                    if (item.equals("")) {
                        dYear = "-1";
                    } else {
                        dYear = item;
                    }  
                } else if (index == 5) {
                    jobs = item;
                }
                index++;
            }
            val_sc.close();
            String rest = id + "," + name + "," + bYear + "," + dYear;
            data.put(id, rest);
        }
        sc.close();    // closes the scanner
        return data;
    }

    public static void main(String[] args) throws Exception {
        fetchNames();    // debug
    }
}
