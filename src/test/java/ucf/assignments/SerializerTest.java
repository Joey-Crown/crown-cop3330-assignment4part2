package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest {

    @Test
    void serializeItem() {
        Item testItem = new Item("Test Item", "1999-12-01", false);
        Serializer compare = Serializer.serializeItem(testItem);
        Assertions.assertEquals("Test Item", compare.description);
        Assertions.assertEquals("1999-12-01", compare.date);
        Assertions.assertEquals(false, compare.completed);
    }
}