package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentDataModel {
    private ObservableList<Appointment> appointmentList;

    public AppointmentDataModel() {
        appointmentList = FXCollections.observableArrayList();
    }

    public ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void addAppointment(Appointment appointment) {
        appointmentList.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointmentList.remove(appointment);
    }
}