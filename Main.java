import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        String searchPhrase = searchField.getText();
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
        searchInDirectory(directory, results, searchPhrase);
        resultArea.setText(results.toString());
    }

    // metoda do listowania plików w katalogu
    private void searchInDirectory(File directory, StringBuilder results, String searchPhrase) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (containsPhrase(file, searchPhrase)) {
                        results.append(file.getAbsolutePath()).append("\n");
                    }
                } else if (file.isDirectory()) {
                    searchInDirectory(file, results, searchPhrase);
                }
            }
        }
    }

    // metoda do sprawdzania, czy plik zawiera szukaną frazę
    private boolean containsPhrase(File file, String searchPhrase) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchPhrase)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
