package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utility class. This class contains useful static methods
 */
public class Util {
  private static final String rootPath;

  static {
    String tmp = null;
    try {
      tmp = new File(".").getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(e.hashCode());
    }
    rootPath = tmp;
  }

  /**
   * @return the absolute path of the root directory
   */
  public static String getRootPath() {
    return rootPath;
  }

  public static String getJSONFileContents(File metaDataFile) {
    if (!metaDataFile.exists()) {
      System.out.println("File does not exist");
      System.exit(13);
    }
    if (!metaDataFile.canRead()) {
      System.out.println("Cannot read the file");
      System.exit(14);
    }
    StringBuilder fileContent = new StringBuilder();
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(metaDataFile);
    } catch (FileNotFoundException e) {
      System.exit(e.hashCode());
    }
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();
    return fileContent.toString();
  }

  /**
   * Gets the contents of a file
   *
   * @param absolutePath the absolute path of the file to read from
   * @return the contents of the file
   */
  public static String getJSONFileContents(String absolutePath) {
    File metaDataFile = new File(absolutePath);
    return getJSONFileContents(metaDataFile);
  }

  /**
   * Creates a user file
   *
   * @param username username of the new user
   * @param password password
   * @return true if the user creation was successful, false otherwise
   */
  public static boolean createUserFile(String username, String password) {
    // search users folder within the application folder for username
    String rootPath = getRootPath();
    File userFile = new File(rootPath + "/appData/users/" + username + ".json");
    if (userFile.exists()) {
      return false;
    }

    JSONObject newUserJSON = new JSONObject();
    newUserJSON.put("username", username);
    newUserJSON.put("password", password);
    newUserJSON.put("userType", "base");
    newUserJSON.put("customPOIs", new JSONArray());
    newUserJSON.put("favourites", new JSONArray());

    writeToFile(newUserJSON, "/appData/users/" + username + ".json");
    return true;
  }

  /**
   * Writes a json object to a file
   *
   * @param json         the json object to write
   * @param relativePath the relative path
   */
  public static void writeToFile(JSONObject json, String relativePath) {
    try {
      FileWriter file = new FileWriter(rootPath + relativePath);
      file.write(json.toString());
      file.flush();
      file.close();
    } catch (IOException ignored) {
      System.out.println("Could not write to file: " + rootPath + relativePath);
    }
  }
}
