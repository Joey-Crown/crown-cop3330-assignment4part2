package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditItemController {

    @FXML public TextField editDescription;
    @FXML public DatePicker editDate;
    @FXML public Button editItemConfirmButton;


    // when the "Edit Item" button is clicked, takes the new values in the fields given by the User
    // takes the old item, the list, and the new item and passes them to "Item.editItem" to make the changes
    public void onEditItemConfirmButton() {
        String itemDescription = editDescription.getText();
        String itemDate = editDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (itemDescription.length() <= 256 && !itemDescription.isEmpty()) {
            Item oldItem = ToDoListController.currentlySelected;
            Item newItem = new Item(itemDescription, itemDate, ToDoListController.currentlySelected.getCompleted().get());

            Item.editItem(ToDoListController.visibleToDoList, oldItem, newItem);
            ToDoListController.secondaryStage.close();
        } else {
            Dialog notValidInput = new Dialog();
            notValidInput.setTitle("Invalid Input.");
            notValidInput.setContentText("Description must be between 1-256 characters.");
            notValidInput.show();
        }
    }

}
