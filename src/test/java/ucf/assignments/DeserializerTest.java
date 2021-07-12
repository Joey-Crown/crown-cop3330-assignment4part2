package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeserializerTest {

    @Test
    void deserializeJson() {
        Serializer serializedItem = new Serializer("Test Item", "1999-12-01", false);
        Item testItem = new Item("Test Item", "1999-12-01", false);

        Item compare = Deserializer.deserializeJson(serializedItem);
        Assertions.assertEquals(testItem.getDescription(), compare.getDescription());
        Assertions.assertEquals(testItem.getDate(), compare.getDate());
        Assertions.assertEquals(testItem.getCompleted().get(), compare.getCompleted().get());
    }
}