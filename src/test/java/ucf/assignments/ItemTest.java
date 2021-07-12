package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {



    @Test
    void completeItem() {
        Item testItem = new Item("Test Item", "1999-12-01", false);
        Item.completeItem(testItem);
        Assertions.assertEquals(true, testItem.getCompleted().get());
    }

    @Test
    void removeItem() {
        ArrayList<Item> testList = new ArrayList<Item>();
        testList.add(new Item("Test Item", "1999-12-01", false));
        testList.add(new Item("Test Item2", "1999-12-01", true));
        testList.add(new Item("Test Item3", "1999-12-01", true));
        Item.removeItem(testList, testList.get(1));
        Assertions.assertEquals("Test Item3", testList.get(1).getDescription());
    }

    @Test
    void editItem() {
        ToDoList testList = new ToDoList();
        Item testItem = new Item("Test Item", "1999-12-01", false);
        testList.listOfItems.add(testItem);
        Item newItem = new Item("New Test Item", "2000-12-01", true);
        Item.editItem(testList, testItem, newItem);
        Assertions.assertEquals(newItem, testList.listOfItems.get(0));
    }

    @Test
    void addItem() {
        ToDoList testList = new ToDoList();
        Item testItem = new Item("Test Item", "1999-12-01", false);
        testList.listOfItems.add(testItem);
        Item newItem = new Item("New Test Item", "2000-12-01", true);
        Item.addItem(testList, newItem);
        Assertions.assertEquals(newItem, testList.listOfItems.get(1));
    }
}