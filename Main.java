import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    private TextField directoryPathField;
    private TextField searchField;
    private TextArea resultArea;

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

        // tworzenie pola tekstowego do wyświetlania wyników
        resultArea = new TextArea();
        resultArea.setPrefHeight(400);

        // tworzenie przycisku "Browse"
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> browseDirectory());

        // tworzenie przycisku "Search"
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchFiles());

        // układ HBox z polem tekstowym i przyciskiem "Browse"
        HBox hBox = new HBox(10, directoryPathField, browseButton);

        // układ VBox z HBox, polem tekstowym, przyciskiem "Search" i polem wyników
        VBox vBox = new VBox(10, hBox, searchField, searchButton, resultArea);

        // tworzenie sceny i ustawienie jej w primaryStage
        Scene scene = new Scene(vBox, 600, 600); // zwiększenie wysokości sceny
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

    // metoda do przeszukiwania plików
    private void searchFiles() {
        String directoryPath = directoryPathField.getText();
        if (directoryPath.isEmpty()) {
            resultArea.setText("Please provide a directory path.");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
            return;
        }

        StringBuilder results = new StringBuilder();
        listFilesInDirectory(directory, results);
        resultArea.setText(results.toString());
    }

    // metoda do listowania plików w katalogu
    private void listFilesInDirectory(File directory, StringBuilder results) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    results.append(file.getAbsolutePath()).append("\n");
                } else if (file.isDirectory()) {
                    listFilesInDirectory(file, results);
                }
            }
        }
    }
}
