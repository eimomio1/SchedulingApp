package application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {

    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();

    public String getDate() {
        return date.get();
    }

    public void setDate(String dateVar) {
        date.set(dateVar);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String descriptionVar) {
        description.set(descriptionVar);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String locationVar) {
        location.set(locationVar);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String titleVar) {
        title.set(titleVar);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String typeVar) {
        type.set(typeVar);
    }

    public StringProperty typeProperty() {
        return type;
    }
}
