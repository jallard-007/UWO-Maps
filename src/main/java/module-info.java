module uwo.group2 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens uwo.group2 to javafx.fxml;
    exports uwo.group2;
}