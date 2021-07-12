package ucf.assignments;

public class Deserializer {

    // takes a serialized object as stored in the .json save files
    // converts it into items which the tableview can display
    public static Item deserializeJson(Serializer serializedItem) {
        return new Item(
                serializedItem.description,
                serializedItem.date,
                serializedItem.completed);
    }
}
