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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ToDoListController {

    /*
    The following functions will be used to present the user with a way to interact with the GUI
    The goal of these functions is to get mouse or keyboard input from the user which then call other methods
    with explicit functions to either add ToDoLists to the display or allow the user to edit elements of the display

    The class ToDoListApplication contains a breakdown of program structure and how th GUI should be understood


    displayList()
    creates an ObservableList variable based on the arraylist of items from the key provided by the public ToDoList
    sets the values of the cells in table view to "description" and "date" of a ToDoList object
    set table view using ObservableList
    will need to implement a custom cell value for when 'completed' property of item is set to 'true'
    checkbox cell column indicates if complete/incomplete
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
    when user clicks on 'Edit Item' it will take the selection of the currently selected item in the ArrayList
    it opens a similar dialog to that which opens when making a new item
    EditItemController then handles input
    refresh the list display by calling DisplayList with the name of the list


    removeItemMenu Event Handler:
    right click on item in table view
    option pops up in menu 'remove'
    clicking on it calls method removeItem() in class Item
    calls Display list to display list again without the removed item

    saveList EventHandler:
    when user clicks on menu item 'Save List'
    opens dialog asking for name of save file
    Then opens file chooser so user can choose directory
    file and list is then passed to ToDoList.SaveListToFile

   loadList EventHandler:
   opens file chooser that let's user select for .json file
   passes file to ToDoList.LoadListFromFile

    */


    // menu controller

    @FXML public Button editItemButton;
    @FXML public Button clearListButton;

    // table elements
    @FXML public TableView<Item> tableView;
    @FXML public TableColumn<Item, String> itemsView;
    @FXML public TableColumn<String, Item> dateView;
    @FXML public TableColumn<Item, Boolean> completedView;

    // context menu in tableview elements
    @FXML public ContextMenu itemMenu;
    @FXML public MenuItem completeItemMenu;
    @FXML public MenuItem removeItemMenu;

    // sidebar button elements
    @FXML public ToggleGroup sidebar;
    @FXML public ToggleButton homeButton;
    @FXML public ToggleButton completedItemsButton;
    @FXML public ToggleButton incompletedItemsButton;

    // Save menu elements
    @FXML public MenuItem saveList;
    @FXML public MenuItem loadList;

    // generates an observable lit based on what the user has clicked on the sidebar
    // uses toggle button group to decide what will be displayed on the tableview
    public void displayList() {
        ObservableList<Item> data = FXCollections.observableArrayList();
        ToggleButton selectedListButton = (ToggleButton) sidebar.getSelectedToggle();

        if (selectedListButton == homeButton) {
            data = collectAllItems(visibleToDoList);
        } else if (selectedListButton == completedItemsButton) {
            data = collectCompletedItems(visibleToDoList);
        } else if (selectedListButton == incompletedItemsButton) {
            data = collectIncompletedItems(visibleToDoList);
        }

        tableView.setItems(data);
        itemsView.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        dateView.setCellValueFactory(new PropertyValueFactory<String, Item>("date"));
        // creates checkbox cell column for visualizing the completeness of each item
        completedView.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Item, Boolean> param) {
                return param.getValue().getCompleted();
            }
        });
        completedView.setCellFactory(CheckBoxTableCell.forTableColumn(completedView));

        tableView.setItems(data);
    }

    // stores all items in an observable list
    public ObservableList<Item> collectAllItems(ToDoList toDoList) {
        ObservableList<Item> data = FXCollections.observableArrayList(toDoList.listOfItems);
        return data;
    }

    // takes all items with property 'completed' == true, and stores them in observable list
    public ObservableList<Item> collectCompletedItems(ToDoList toDoList) {
        ObservableList<Item> data = FXCollections.observableArrayList();
        for (Item item: visibleToDoList.listOfItems) {
            if (item.getCompleted().get()) {
                data.add(item);
            }
        }
        return data;
    }

    // takes all items with property 'completed' == false, and stores them in observable list
    public ObservableList<Item> collectIncompletedItems(ToDoList toDoList) {
        ObservableList<Item> data = FXCollections.observableArrayList();
        for (Item item: visibleToDoList.listOfItems) {
            if (!item.getCompleted().get()) {
                data.add(item);
            }
        }
        return data;
    }

    //stores the visible list
    public static ToDoList visibleToDoList = new ToDoList();
    //stores currently selected item
    public static Item currentlySelected;

    public void initialize() {

        itemMenu.getItems().add(completeItemMenu);
        // set selection mode for tableview
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // disable context menu if tableview has no selection
        completeItemMenu.disableProperty().bind(tableView.selectionModelProperty().isNull());

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                currentlySelected = newValue;
            }
        });

        // clicking on item 'Complete/Incomplete' in context menu calls completeItem on selected item in tableview
        // displays changes on tableview
        completeItemMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Item.completeItem(tableView.getSelectionModel().getSelectedItem());
                displayList();
            }
        });

        // clicking on item 'Remove Item' in context menu calls RemoveItem on selected item in tableview
        // displays changes on tableview
        removeItemMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Item.removeItem(visibleToDoList.listOfItems, tableView.getSelectionModel().getSelectedItem());
                displayList();
            }
        });

        // calls displayList whenever a toggle button is clicked
        sidebar.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                displayList();
            }
        });

        // Clear List Button removes all items from the list and displays an empty list
        clearListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToDoList.clearList(visibleToDoList);
                displayList();
            }
        });

        //Alert for errors in saving or loading file
        Alert fileAlert = new Alert(Alert.AlertType.ERROR);
        // loading list from file
        loadList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = getFileFromFileChooser();
                if (file != null) {
                    visibleToDoList = ToDoList.loadListFromFile(file);
                    if (visibleToDoList != null)  {
                        displayList();
                    } else {
                        fileAlert.show();
                    }
                } else {
                    fileAlert.show();
                }
            }
        });

        TextInputDialog saveFileName = new TextInputDialog();
        saveFileName.setTitle("Save File");
        saveFileName.setHeaderText("Enter name of save file");
        saveFileName.setContentText("List");

        // when menuItem 'Save List' is clicked, opens dialog to get name of file to be saved
        // then let's user choose directory for saving
        saveList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveFileName.showAndWait();
                String name = saveFileName.getEditor().getText();
                File file = getDirectoryFromChooser();
                if (file != null) {
                    ToDoList.saveListToFile(file, visibleToDoList, name);
                } else {
                    fileAlert.show();
                }
            }
        });

    }

    public static Stage secondaryStage = new Stage();

    // opens new window when "Add Item" button is clicked
    // AddItemController handles user input
    public void onAddItemClick() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AddItemWindow.fxml"));

            Scene scene = new Scene(root);

            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Add New Item");

            secondaryStage.showAndWait();
            displayList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // opens new window when "Edit Item" button is clicked
    // EditItemController handles user input
    public void onEditItemClick() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("EditItemWindow.fxml"));

            Scene scene = new Scene(root);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Edit Item");
            secondaryStage.showAndWait();
            displayList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // file chooser that let's you select a json file
    public File getFileFromFileChooser() {
        FileChooser findFile = new FileChooser();
        findFile.setTitle("Select File");
        findFile.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", ".json")
        );
        return findFile.showOpenDialog(secondaryStage);
    }

    // directory chooser that let's you select a directory
    public File getDirectoryFromChooser() {
        DirectoryChooser findDirectory = new DirectoryChooser();
        findDirectory.setTitle("Select Directory to Save to");
        return findDirectory.showDialog(secondaryStage);
    }


}
