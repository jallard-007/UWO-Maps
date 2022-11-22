package uwo.group2;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws IOException {
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
      System.out.print("Enter your user name: ");
      // try {
        String username = input.nextLine();
        if (username.equals("-1")){
          break;
        }
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        if(password != null){
          int loginSuccess = app.login(username, password);
          switch(loginSuccess){
            case 1:
              //login was successful 
              System.out.println("login success! welcome " + app.user.getUserName());
              app.loadUserPOIs();
              System.out.println("your custom POIs: ");
              for(POILocation location: app.poiLocations){
                if(location.poi.type == POIType.custom){
                  System.out.println(location.poi.name);
                }
              }
              for(Building building: app.buildings){
                for(Floor floor: building.floors){
                  // System.out.println(floor.getPOIS());
                  List<POI> customs = floor.getPOIS()[POIType.custom.ordinal()];
                  for(POI poi: customs){
                    System.out.println(poi.name);
                    System.out.println(poi.roomNumber);
                  }
                }
              }
              while(true){
                System.out.print("Logout?: ");
                // try {
                  String logger = input.nextLine();
                  if (logger.equals("1")){
                    app.logout();
                    // for(POILocation location: app.poiLocations){
                    //   if(location.poi.type == POIType.custom){
                    //     System.out.println(location.poi.name);
                    //   }
                    // }
                    // for(Building building: app.buildings){
                    //   for(Floor floor: building.floors){
                    //     // System.out.println(floor.getPOIS());
                    //     List<POI> customs = floor.getPOIS()[POIType.custom.ordinal()];
                    //     for(POI poi: customs){
                    //       System.out.println(poi.name);
                    //       System.out.println(poi.roomNumber);
                    //     }
                    //   }
                    // }
                    break;
                  }

              }
              break;
            case 2:
              //password was incorrect
              System.out.println("sorry, password incorrect. ");
              break;
            case 3:
              System.out.println("No such username exists. ");
              System.out.println("Would you like to sign up as a new user? ");
              String selection = input.nextLine();
              if(selection.equals("y")){
                System.out.println("Enter your new username");
                String usernameNew = input.nextLine();
                System.out.print("Enter your password: ");
                String passwordNew = input.nextLine();
                Application.newUser(usernameNew, passwordNew);
              }



              break;
            default:
              System.out.println("Undefined behaviour. ");
          }

        }
        // List<POILocation> matches = app.searchForPOI(s);
        // if (matches.size() == 0) {
        //   System.out.println("No matches");
        // } else {
        //   for (POILocation match : matches) {
        //     System.out.println(match.toString());
        //   }
        // }
      // } catch (Exception e) {
      //   System.out.println("Exception thrown");
      // }
    }
    input.close();
  }

}