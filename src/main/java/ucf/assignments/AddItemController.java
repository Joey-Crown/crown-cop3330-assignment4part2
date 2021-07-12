/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AddItemController {

    /*
    This is a new window with it's own fxml file associated with it
    It prompts the user to input the description and date to be added to the ToDoList

    initialize()
    creates the new window from fxml file

    onAddItemButtonClick()
    This will take the text that were inputted into the text fields 'date' and 'description'
    construct an Item object and return it to the method onNewItemClick or onEditItemClick

     */

    @FXML
    public TextField descriptionField;

    @FXML
    public DatePicker dateField;

    public void onAddItemButtonClick() {
        String itemDescription = descriptionField.getText();
        String itemDate = dateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (itemDescription.length() <= 256 && !itemDescription.isEmpty()) {
            Item newItem = new Item(itemDescription, itemDate);
            Item.addItem(ToDoListController.visibleToDoList, newItem);
            ToDoListController.secondaryStage.close();
        } else {
            Dialog notValidInput = new Dialog();
            notValidInput.setTitle("Invalid Input.");
            notValidInput.setContentText("Description must be between 1-256 characters.");
            notValidInput.show();
        }
    }


}
