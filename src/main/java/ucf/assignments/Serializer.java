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

    public static Serializer serializeItem(Item item) {
        String description = item.getDescription();
        String date = item.getDate();
        boolean completed = item.getCompleted().get();
        return new Serializer(description, date, completed);
    }

}
