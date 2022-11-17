package uwo.group2;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class test {

    public static void main(String[] args) throws IOException {
        String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
        System.out.println(rootPath);
        File metaDataFile = new File(rootPath + "/appData/metaData/poiMetaData.json");


        Scanner fileScanner = new Scanner(metaDataFile);
        StringBuilder fileContent = new StringBuilder();
        while (fileScanner.hasNext()) {
            fileContent.append(fileScanner.nextLine());
        }
        fileScanner.close();

        JSONObject jsonObject = new JSONObject(fileContent.toString());
//        JSONArray obj = jsonObject.getJSONArray("buildings");
//        System.out.println(obj.length());
//        JSONObject buildings = obj.getJSONObject(0);
//        JSONArray floors = buildings.getJSONArray("floors");
//        JSONObject arr = floors.getJSONObject(0);
//        String mapPath = arr.getString("map");
//        System.out.println(mapPath);
        String mapPath = jsonObject.getJSONArray("buildings").getJSONObject(0).getJSONArray("floors").getJSONObject(0).getString("map");
        File mapFile = new File(rootPath + mapPath);
        Desktop d = Desktop.getDesktop();
        d.open(mapFile);
        System.out.println("Done");
    }
}
