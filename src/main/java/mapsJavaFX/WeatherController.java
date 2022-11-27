package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherController {
  @FXML
  private Text weatherText;

  public JSONObject fetchWeatherData(String urlWeather) {
    try {

      URL url = new URL(urlWeather);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      int responseCode = conn.getResponseCode();

      if (responseCode != 200) {
        throw new RuntimeException("HttpResponseCode: " + responseCode);
      } else {

        StringBuilder weatherInfo = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
          weatherInfo.append(scanner.nextLine());
        }

        scanner.close();

        JSONParser parser = new JSONParser();

        return (JSONObject) parser.parse(String.valueOf(weatherInfo));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  void displayWeatherData() {
    JSONObject currWeather = fetchWeatherData("https://api.openweathermap.org/data/2.5/weather?lat=42.9849&lon=-81.2453&appid=09928fefc6a87f8130ddec17c33e22ee&units=metric");

    weatherText.setText("Current Weather in London: " + currWeather.get("main.temp"));

  }

}

