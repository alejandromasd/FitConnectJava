package fitconnect.users;


public class Coaches extends Users {
    private String specialty;

    public Coaches(String firstName, String lastName, String dateOfBirth, String id, String password, String specialty) {
        super(firstName, lastName, dateOfBirth, id, password);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Coach: " + getFirstName() + " " + getLastName() + " (ID: " + getId() + ", Speciality: " + getSpecialty() + ")";
    }
}
