import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    private TextField directoryPathField;
    private TextField searchField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Browser and Search");

        // tworzenie pola tekstowego do wprowadzenia ścieżki katalogu
        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");

        // tworzenie pola tekstowego do wprowadzenia frazy do wyszukiwania
        searchField = new TextField();
        searchField.setPromptText("Enter search phrase");

        // tworzenie przycisku "Browse"
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> browseDirectory());

        // tworzenie przycisku "Search"
        Button searchButton = new Button("Search");

        // układ HBox z polem tekstowym i przyciskiem "Browse"
        HBox hBox = new HBox(10, directoryPathField, browseButton);

        // układ VBox z HBox, polem tekstowym i przyciskiem "Search"
        VBox vBox = new VBox(10, hBox, searchField, searchButton);

        // tworzenie sceny i ustawienie jej w primaryStage
        Scene scene = new Scene(vBox, 600, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // metoda do wyboru katalogu
    private void browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        // sprawdzenie czy użytkownik wybrał katalog
        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }
}
