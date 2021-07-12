package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditItemController {

    @FXML public TextField editDescription;
    @FXML public DatePicker editDate;
    @FXML public Button editItemConfirmButton;


    public void onEditItemConfirmButton() {
        String itemDescription = editDescription.getText();
        String itemDate = editDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Item oldItem = ToDoListController.currentlySelected;
        Item newItem = new Item(itemDescription, itemDate, ToDoListController.currentlySelected.getCompleted().get());

        Item.editItem(ToDoListController.visibleToDoList, oldItem, newItem);
        ToDoListController.secondaryStage.close();
    }

}
