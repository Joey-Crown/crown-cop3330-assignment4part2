/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Item {

    private final SimpleStringProperty description = new SimpleStringProperty("");
    private final SimpleStringProperty date = new SimpleStringProperty("");
    private final BooleanProperty completed = new SimpleBooleanProperty(false);

    public Item(String description, String date) {
        setDescription(description);
        setDate(date);
        setCompleted(true);
    }

    // returns the description of an Item Object
    public String getDescription() {
        return description.get();
    }

    // sets the value of the description of an item, used by constructor
    public void setDescription(String description) {
        this.description.set(description);
    }

    // returns the date of an Item object
    public String getDate() {
        return date.get();
    }

    // sets the value of the date of an item in a todolist, used by constructor
    public void setDate(String date) {
        this.date.set(date);
    }

    public BooleanProperty getCompleted(){
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    // changes value of completed in an Item to true
    // should refresh display so that Item is displayed as red to indicate Item is completed
    public void completeItem(int pos, ArrayList<Item> items){

    }

    // takes the index of an item in an arraylist of items to remove the item in that position
    public void removeItem(int pos, ArrayList<Item> items) {

    }

    // edits the date and description of an item in the ToDoList
    // uses the index passed to the function to find it's position in the ArrayList
    public void editItem(int index, String name, String description, String date) {

    }

    // adds Item to the ArrayList<Item> which has a key matching the String provided
    public static void addItem(ToDoList toDoList, Item item) {
        toDoList.listOfItems.add(item);
    }

}
