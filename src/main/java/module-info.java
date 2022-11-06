module uwo.group2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens uwo.group2 to javafx.fxml;
    exports uwo.group2;
}