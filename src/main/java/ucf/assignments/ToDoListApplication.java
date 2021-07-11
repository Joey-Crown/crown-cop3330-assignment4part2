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

    The left side of th GUI contains a list view that displays all the lists currently accessible by the program
    clicking on a ToDoList in the listview will display all the items in that list
    The items of the ToDoList are displayed on the right with a TableView dividing the item into columns
    The values of the cells in TableView are "description" and "date"
    right clicking on an item in tableview will give a menu with two options, 'delete' and 'complete'
    clicking 'delete' will remove the item from the tableview
    clicking 'complete' will change that item in the row to the color red to indicate that it is completed

    ToDolists are displayed in tabs that can be opened by creating a new list or clicking on one in ListView
    Tabs can be closed to keep things organized

    the menubar has options for 'File', 'Edit', and 'About'
    'File' will contain MenuItems that will allow th user to make a new list, save a list, save all lists,
    and load list(s) from file.
    'Edit' will contain MenuItems that allow th user to edit the name of a list, and edit an item's details
    'About' contains a MenuItem 'About Application" that will open an alert menu giving details about the Application

    There is also a second FXML file in the resources folder that will be a window that asks the user
    to fill out the description and date of the ToDoList item

    Some program functionality is currently worked out in the classes. I did some testing with various methods to see
    what was possible before settling on the methods and classes that would need to be used

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
