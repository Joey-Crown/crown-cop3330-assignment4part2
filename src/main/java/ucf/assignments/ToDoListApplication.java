/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoListApplication extends Application {

    /*
    The following is a breakdown of the program structure, and what the user should expect from the program:

    (Moved to Readme.md)

     */

    public static void main(String[] args) {
        launch(args);
    }

    // opens the fxml file to display GUI to user
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ToDoListMain.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Todo List App");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
