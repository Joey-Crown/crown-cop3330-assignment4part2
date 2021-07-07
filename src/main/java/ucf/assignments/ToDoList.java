/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoList {

    /*
    deleteList()
    takes the name of the list as provided in the ListView and then uses it as a key for the hashmap
    deletes key entry in the ToDoList hashmap
    deletes name of list
    refresh list display

    editListName()
    takes a ToDoList as a parameter
    change name variable
    refresh list

    saveListToFile()
    takes a String as a parameter, uses it as the Map key to get the ArrayList of items
    Stores key as name for gson format
    use these variables to create a new ToDoList with th same properties
    make directory for saved file if it doesn't exist already
    uses a gson parser to convert ToDoList to a json formatted string
    write sting to new file named '~listname~.json
    return boolean true or false for controller method to display an alert of success or failure

    loadListFromFile()
    open file explorer to choose file
    take file name, using split to get the name of file before .json
    store name as string
    use gson to parse file into ToDoList object
    use addList in controller to initialize th list
    use Display list to show list in GUI

    SaveAllLists()
    takes Map itemsList
    use Gson parser to store lists as a json format using class format ArrayList<ToDoList>
    name parameter can be set as the key for the list by storing all keys in an array
    I'm not super positive on how to do parsing correctly without having an ArrayList of all ToDoLists
        (I could us all the keys to get all the items to create a new arraylist<ToDoLists>
        that duplicates all lists from keys.
        Using keys as the name parameter and map.get(key) to get the arrayList)
    creates file directory if one doesn't exist

    LoadAllLists(File)
    load .json file from file explorer
    use Gson to parse file into an ArrayList of ToDoLists
    loop through the ArrayList and use a customer constructor to add all of the info to a HashMap
    return the ArrayList<ToDoList> to the controller
     */

    ArrayList<Item> listOfItems;
    String name;

    public ToDoList(String name) {
        this.listOfItems = new ArrayList<Item>();
        listOfItems.add(new Item("default list item", "2021-07-07"));
        this.name = name;
    }

}
