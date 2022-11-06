module uwo.group2 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;


    opens uwo.group2 to javafx.fxml;
    exports uwo.group2;
}