package ucf.assignments;

public class Serializer {

    String description;
    String date;
    boolean completed;

    public Serializer(String description, String date, boolean completed) {
        this.description = description;
        this.date = date;
        this.completed = completed;
    }

    // converts Items into a collection of simple primitives, easier for gson to parse
    public static Serializer serializeItem(Item item) {
        String description = item.getDescription();
        String date = item.getDate();
        boolean completed = item.getCompleted().get();
        return new Serializer(description, date, completed);
    }

}
