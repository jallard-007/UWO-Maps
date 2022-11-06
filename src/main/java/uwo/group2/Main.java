package uwo.group2;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Application app = new Application();
      try {
        app.loadData();
        /*
        System.out.println(app.buildings.toString());
        for (POILocation p : app.poiLocations) {
          System.out.println(p.toString());
        }
        */
      } catch (IOException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        System.exit(12);
      }

    Scanner input = new Scanner(System.in);
    while(true) {
      System.out.print("Enter room name: ");
      try {
        String s = input.nextLine();
        if (s.equals("-1")){
          break;
        }
        List<POILocation> matches = app.searchForPOI(s);
        if (matches.size() == 0) {
          System.out.println("No matches");
        } else {
          for (POILocation match : matches) {
            System.out.println(match.toString());
          }
        }
      } catch (Exception e) {
        System.out.println("Exception thrown");
      }
    }
    input.close();
  }

}