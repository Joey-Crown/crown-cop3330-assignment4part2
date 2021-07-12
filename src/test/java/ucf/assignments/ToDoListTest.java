package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {

    @Test
    void clearList() {
        ToDoList testList = new ToDoList();
        Item testItem = new Item("Test Item", "1999-12-01", false);
        Item testItem2 = new Item("Test Item", "1999-12-01", false);
        testList.listOfItems.add(testItem);
        testList.listOfItems.add(testItem2);
        ToDoList.clearList(testList);
        Assertions.assertEquals(0,testList.listOfItems.size());
    }

    @Test
    void saveListToFile() {
        ToDoList testList = new ToDoList();
        Item testItem = new Item("Test Item", "1999-12-01", false);
        Item testItem2 = new Item("Test Item", "1999-12-01", false);
        testList.listOfItems.add(testItem);
        testList.listOfItems.add(testItem2);
        File file = new File("invalid/file/location");
        Assertions.assertEquals(false, ToDoList.saveListToFile(file, testList, "name"));

    }

    @Test
    void loadListFromFile() {
        File file = new File("src/test/resources/list.json");

        ToDoList testList = new ToDoList();
        Item testItem = new Item("TestJson", "2021-07-11", false);
        testList.listOfItems.add(testItem);
        ToDoList compare = ToDoList.loadListFromFile(file);
        Assertions.assertEquals(testList.listOfItems.get(0).getDescription(), compare.listOfItems.get(0).getDescription());
        Assertions.assertEquals(testList.listOfItems.get(0).getDate(), compare.listOfItems.get(0).getDate());
        Assertions.assertEquals(testList.listOfItems.get(0).getCompleted().get(), compare.listOfItems.get(0).getCompleted().get());

    }
}