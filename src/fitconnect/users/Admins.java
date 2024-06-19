package fitconnect.users;


import fitconnect.management.UserManager;

public class Admins extends Users {
    public Admins(String firstName, String lastName, String dateOfBirth, String id, String password) {
        super(firstName, lastName, dateOfBirth, id, password);
    }

    public void addMember(UserManager userManager, Members member) {
        userManager.addUser(member);
    }

    public void removeMember(UserManager userManager, Members member) {
        userManager.removeUser(member);
    }

    @Override
    public String toString() {
        return "Admin: " + getFirstName() + " " + getLastName() + " (ID: " + getId() + ")";
    }


}

