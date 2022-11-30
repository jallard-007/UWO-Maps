package mapsJavaFX;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPOIController implements Initializable {
  @FXML
  private ChoiceBox<String> floorName;
  @FXML
  private Text buildingName;
  private Building selectedBuilding;

  public void initialize(URL location, ResourceBundle resources) {
    // Get current building selected in the map view
    String strSelectedBuilding = ControllerMediator.getInstance().getBuildingTab();
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(strSelectedBuilding)) {
        selectedBuilding = building;
      }
    }
    buildingName.setText(selectedBuilding.getName());

    // Set choicebox options
    List<String> floorList = new ArrayList<>();
    for (Floor floor : selectedBuilding.getFloors()) {
      floorList.add(floor.getName());
    }
    floorName.setItems(FXCollections.observableList(floorList));
    floorName.getSelectionModel().selectFirst();
  }

  public void onContinue(ActionEvent event) {
    Floor selectedFloor = null;
    for (Floor floor : selectedBuilding.getFloors()) {
      if (floor.getName().equals(floorName.getSelectionModel().getSelectedItem())) {
        selectedFloor = floor;
      }
    }

    POILocation poiLocation = new POILocation(selectedBuilding, selectedFloor,
        new POI("placeholder", POIType.custom, new Pair(0, 0)));

    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/edit.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      EditController editController = fxmlLoader.getController();
      POIButton poiButton = new POIButton(poiLocation);
      editController.setPoiLocation(poiLocation, poiButton);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
