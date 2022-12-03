# Western Maps 

Ever been stuck in a building on campus with no idea how to get around? Do the physical maps leave
you running in circles? 
If so, look no further for your new favourite app, **Western Maps**.

Western Maps is a desktop application designed to immerse Western Students within common buildings found on Campus,
such as Middlesex College, Talbot College, and the Natural Sciences Centre. This app is intended to provide Western Students an
efficient way to find their way around buildings without the headache that may come with physical maps. 

## Features

### Browsing Maps

- In the heart of the application, users can find a directory of different buildings on campus. Within these buildings, the corresponding maps are provided for each floor.
- Users may switch between buildings and floors seamlessly, zooming in and out, as well as scrolling, as needed.

### Built-In Points of Interest (POI)

- Each floor map within a building contains built-in points of interests that users can layer onto the map while browsing or searching throughout the floor.
- Facilities including but not limited to classrooms, washrooms, computer labs, eateries, and even common stairways or elevators may be layered or hidden from the map.


### Searching Maps

- In the event a user would like to search for a specific POI on the map, a search menu is available for use with both user-made and built-in POIs at hand.

### Favourites

- Have a specific point-of-interest you find yourself needing access to frequently? If so, Western Maps is equipped with the ability for users to favourite and un-favourite POIs as needed.
- Access to these favourite POIs may be found in the favourites menu. 

### User-Created Points of Interest

- In addition to built-in POIs, users may create their own custom POIs as well. Simply drag and drop a button representing a new POI, enter the corresponding information, and have your custom POI readily visible on the map.

### Editing Tool for Admins

- A feature specific to administrators, users are able to add, edit, and remove buildings from the directory, floors from each corresponding building, and points of interests within a map.
- Additionally, users may also graphically edit the metadata for built-int POIs. 

### Multi-user System

- Western Maps supports multiple users, both of base and admin permissions.
- Base users will not have access to admin-specific features, but are still able to enjoy full functionality of the application.

### Weather

- Users will be able to view the current weather in London, ON, which is dynamically updated in the menu bar according to changes in temperature.

## Technologies and Tools

- Java was used to program the back-end of the application as well as create Controller classes for GUI-specific components.
- JavaFX, to create the User Interface using both FXML files for UI markup, as well as Controller classes written in Java to add user functionality. 
- Maven, which was the build automation tool for this project.

## Install and Set Up for Developers

- To run Western Maps, first clone the repository to your local system.
- Make sure you have all proper dependencies and libraries installed and/or resolved, such as:
  - JDK 19
  - Maven, including:
    - org.json
    - org.simple.json
    - JavaFX
    - JUnit
- Once your repository is looking ready for use, navigate to the WesternMaps class and run WesternMaps.main() to view and use the application!

Happy travels :)



