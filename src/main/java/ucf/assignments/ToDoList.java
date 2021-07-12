/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joseph Crown
 */
package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoList {

    /*
    clearList()
    take ToDoList as a parameter
    iterate through the list removing all items in ToDoList.listofItems

    saveListToFile()
    Serialize TodoList in order to make it easier for gson to parse it
    uses a gson parser to convert serialized ToDoList to a json formatted string
    write sting to new file named '~listname~.json
    return boolean true or false for controller method to display an alert of success or failure

    loadListFromFile()
    open file explorer to choose file
    use gson to parse file into ToDoList object using deserializer class methods
    use displayList in controller to initialize th list
     */

    ArrayList<Item> listOfItems;

    public ToDoList() {
        this.listOfItems = new ArrayList<Item>();
    }

    public static void clearList(ToDoList toDoList) {
        for (int i = 0; i < toDoList.listOfItems.size(); i++) {
            toDoList.listOfItems.remove(i);
        }
    }

    public static boolean saveListToFile(File file, ToDoList toDoList, String name) {
       try {
            // takes the user input and makes a filepath with the information
           String fileName = file.getPath() + "/" + name + ".json";
           List<Serializer> serializedList = new ArrayList<Serializer>();

           // loops through ToDoList to produce a lit of serialized items
           // gson has problems parsing the current Item class so this was necessary
           for (Item item: toDoList.listOfItems) {
               serializedList.add(Serializer.serializeItem(item));
           }
           // create new gson and write array to file
           Gson gson = new Gson();
           String jsonString = gson.toJson(serializedList);
           // create new file
           Path filePath = Paths.get(fileName);
           Files.createFile(filePath);
           //write to file
           FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
           gson.toJson(serializedList, fileWriter);
           fileWriter.flush();
           fileWriter.close();
           return true;
       } catch (IOException e) {
           System.out.println("Error writing to file");
       }
       return false;
    }

    // using Gson to load list from file
    public static ToDoList loadListFromFile(File file) {
        try {
            Gson gson = new Gson();

            Reader readFromFile = Files.newBufferedReader(file.toPath());

            // Gson uses Reader to parse .json file info and create a ProductList object
            Serializer[] list = gson.fromJson(readFromFile, Serializer[].class);

            ToDoList deserializedToDoList = new ToDoList();

            for (Serializer serialized: list) {
                Item.addItem(deserializedToDoList, Deserializer.deserializeJson(serialized));
            }

            return deserializedToDoList;

        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return null;
    }
}
