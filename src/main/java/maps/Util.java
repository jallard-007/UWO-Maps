package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {
  public static String getRootPath() throws IOException {
    return new File(".").getCanonicalPath();
  }

  public static String getJSONFileContents(String absolutePath) {
    File metaDataFile = new File(absolutePath);
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

  public static boolean createUserFile(String username, String password) throws IOException {
    // search users folder within the application folder for username
    String rootPath = getRootPath();
    System.out.println("Hello");
    File userFile = new File(rootPath + "/appData/users/" + username + ".json");
    if (userFile.exists()) {
      return false;
    }

    JSONObject newUserJSON = new JSONObject();
    newUserJSON.put("username", username);
    newUserJSON.put("password", password);
    newUserJSON.put("userType", "base");
    newUserJSON.put("customPOIs", new JSONArray());

    FileWriter file = new FileWriter(rootPath + "/appData/users/" + username + ".json");
    file.write(newUserJSON.toString());
    file.flush();
    file.close();
    System.out.println(getJSONFileContents(rootPath + "/appData/users/" + username + ".json"));
    return true;
  }
}
