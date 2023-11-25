module com.example.quoridorjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens com.example.quoridorjavafx to javafx.fxml;
    exports com.example.quoridorjavafx;
    exports com.example.quoridorjavafx.control;
    exports com.example.quoridorjavafx.model.javaComponents;
    opens com.example.quoridorjavafx.control to javafx.fxml;
    exports com.example.quoridorjavafx.model.javaFXComponents;
    exports com.example.quoridorjavafx.model.modifiedClasses;
}