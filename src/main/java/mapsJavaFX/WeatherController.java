package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import maps.Application;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Retrieve information pertaining to the current weather in London, ON and display the information
 * to the user
 */
public class WeatherController {
  public Application app;

  @FXML
  private Text weatherText;

  public WeatherController() {
  }

  /**
   * Get JSON object from openweathermap API
   *
   * @param urlWeather the URL to make a GET request with
   * @return the JSON object containing all the retrieved data pertaining to the current weather
   */
  public JSONObject fetchWeatherData(String urlWeather) {
    try {

      // create a URL with the inputted url string
      URL url = new URL(urlWeather);

      // create an HTTP connection with the URL and specify that we are making a GET
      // request
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      // retrieve the response code and check if it is valid
      int responseCode = conn.getResponseCode();

      // a response code of anything other than 200 means there is an error
      if (responseCode != 200) {
        throw new RuntimeException("HttpResponseCode: " + responseCode);
      } else {
        // create a string representing the JSON data we retrieve
        StringBuilder weatherInfo = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
          weatherInfo.append(scanner.nextLine());
        }
        scanner.close();

        // parse the string into a JSON object

        return new JSONObject(weatherInfo.toString());
      }
    } catch (Exception e) {
      System.out.println(
          "Unable to fetch data from weather API"); // print out what is returned from our exception
    }
    return null; // return null if the try block fails
  }

  /**
   * Get the current weather in London from the openweathermap API, and then extract the data to
   * display to the user, setting the text to show the current weather
   */

  public void initialize() {
    JSONObject currWeather = fetchWeatherData(
        "https://api.openweathermap.org/data/2.5/weather?lat=42.9849&lon=-81.2453&appid=09928fefc6a87f8130ddec17c33e22ee&units=metric");
    if (currWeather != null) {
      JSONObject main = currWeather.getJSONObject("main");
      weatherText.setText("Current Weather in London: " + main.get("temp") + "°C");
    }
  }

}
