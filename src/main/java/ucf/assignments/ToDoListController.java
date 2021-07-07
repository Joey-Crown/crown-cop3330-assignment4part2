/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ToDoListController {

    /*
    The following functions will be used to present the user with a way to interact with the GUI
    The goal of these functions is to get mouse or keyboard input from the user which then call other methods
    with explicit functions to either add ToDoLists to the display or allow the user to edit elements of the display

    The class ToDoListApplication contains a breakdown of program structure and how th GUI should be understood

    onListItemRightClick()
    Right click item in tableView, action opens menu with option 'complete item'
    changes property in item 'completed' to 'true'
    call method displayCompletedItem() to change JavaFX text display to red
    change color of item to red to mark completed

    onSaveAsClick()
    event listener that takes current collection of ToDoLists being displayed
    passes lists to gson parser and serializes data into a .json file
    uses method in class ToDoList 'saveListToFile'
    display alert upon success indicating file was successfully saved

    displayList()
    takes a name parameter and uses that as a key for the Map itemsList
    creates an ObservableList variable based on the arraylist of items from the key provided by 'name'
    sets the values of the cells in table view to "description" and "date" of a ToDoList object
    set table view using ObservableList
    will need to implement a custom cell value for when 'completed' property of item is set to 'true'
    custom cell value will set that row in TableView to the color red (indicates completed item)

    displayCompletedItems()
    Similar to displayList but constructs the TableView using only using items whose 'completed' value is set to 'true'

    displayIncompleteItems()
    Similar to displayList but constructs the TableView using only using items whose 'completed' value is set to 'false'

    onShowCompletedItemsClick()
    right clicking on item in listview will provide a menu with option 'Show Completed Items'
    passes the name of selected list to displayCompletedItems()

    onShowIncompletedItemsClick()
    right clicking on item in listview will provide a menu with option 'Show Incompleted Items'
    passes the name of selected list to displayIncompletedItems()

    onNewItemClick()
    when user clicks on 'New Item' a new Window will pop up using a new fxml file controlled by class 'AddItemController'
    gets the selected list from from the ListView and stores it as String 'key'
    It has two text fields, for description and one for date (Date has to be in format MM-DD-YYYY)
    if date isn't right format it will reopen the window and ask for input again
    Uses the key and given field to call Item.addItem to the ToDoList to make a new item in that list
    calls displayList to refresh the TableView with the new Item

    onEditItemClick()
    when user clicks on 'Edit Item' it will take the index of the currently selected item in the ArrayList
    store the index of the item
    store the name of the list
    it opens the same dialog that opens when making a new item, but the text input fields will have the current item properties
    Gets input from AddItemController which returns Item Object
    store the input fields as Strings and pass them and the index integer to editItem in class Item
    refresh the list display by calling DisplayList with the name of the list

    onNewListClick()
    when user clicks on menu item 'New List' opens a dialog asking for name of new list
    dialog shows and waits for user to click okay
    whatever was in editor gets passed as a name for a new list which then initializes it as an object
    uses method addList() to display name of list in ListView
    then calls displayList with new object to display the list with the default item list

    newTab()
    To be called when a new ToDoList is created or a ToDoList is clicked in theListView
    creates a new tab
    call DisplayList to display info in new TableView

    deleteItemOnRightClick()
    right click on item in table view
    option pops up in menu 'delete item'
    clicking on it calls method removeItem() in class Item
    calls Display list to display list again without the removed item

    saveListOnClick()
    when user clicks on menu item 'Save List'
    takes currently selected list from ListView and passes the name to saveListToFile
    saveListToFile returns boolean true or false depending on success of saving
    if successful (true) -> display alert "Successfully saved file"
    if failure (false) -> display alert "Issue saving file to folder"

    onLoadAllListsClick()
    when user clicks on MenuItem 'Open Lists' it will open a file explorer where the user can
        select a .json formatted for lists
    file gets passed to ToDoList.loadAllLists and stores the returned ArrayList
    loop through the ArrayList<ToDoList> and pass each ToDoList to addList() to initialize them in the ListView

    onListViewClick()
    When a user clicks on an item in the listview, it will open a new tab with all the items in that list in tableview
    The strategy will be to duplicate the tab format with the tableview
    call displayList with the selected item in the listview passing the name of the list
    displayList uses 'name' to access the hashmap to get the arraylist of items

    onAboutAppClick()
    in the about section of the MenuBar, when the user clicks on 'About App', it will display an alert window
    alert window contains information about the app including the app version, date released, and development credits
    */




    // adding new list to sidebar
    @FXML public ListView listView;


    public void addList(ToDoList toDoList) {
        listView.getItems().add(toDoList.name);
    }

    // menu controller
    @FXML public MenuBar menuBar;

    @FXML public Menu file;
    @FXML public MenuItem newList;
    @FXML public MenuItem editList;
    @FXML public MenuItem deleteList;

    @FXML public Button confirm;
    @FXML public TextField inputNewList;

    // table elements
    @FXML public TableView<Item> tableView;
    @FXML public TableColumn<Item, String> itemsView;
    @FXML public TableColumn<String, Item> dateView;

    // TODO
    // get this working so that clicking on an item in the list view populates that list
    public void displayList(ToDoList toDoList) {
        ObservableList<Item> data = FXCollections.observableArrayList(toDoList.listOfItems);
        tableView.setItems(data);
        itemsView.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        dateView.setCellValueFactory(new PropertyValueFactory<String, Item>("date"));

        tableView.setItems(data);
    }

    // Stores the currently selected list
    public static String currentlySelected;
    public static ToDoList visibleToDoList;

    public void initialize() {

        // opens a dialog that prompts the user to enter the name of a list and then initializes a list with that name
        TextInputDialog listDialog = new TextInputDialog("Enter name of new list");
        listDialog.setHeaderText("New List");

        EventHandler<ActionEvent> onNewListClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listDialog.showAndWait();
                TextField input = listDialog.getEditor();
                String name = input.getText();

                visibleToDoList = new ToDoList(name);
                addList(visibleToDoList);
                displayList(visibleToDoList);
            }
        };

        // opens new list dialogue on click
        newList.setOnAction(onNewListClick);



        // click item in listView to display items
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                currentlySelected = newValue.toString();
            }
        });

    }

    public static Stage secondaryStage = new Stage();

    public void onNewItemClick() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AddItemMockup.fxml"));

            Scene scene = new Scene(root);

            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Add New Item");
            secondaryStage.showAndWait();
            displayList(visibleToDoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
