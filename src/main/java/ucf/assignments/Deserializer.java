package ucf.assignments;

public class Deserializer {

    public static Item deserializeJson(Serializer serializedItem) {
        return new Item(
                serializedItem.description,
                serializedItem.date,
                serializedItem.completed);
    }
}
