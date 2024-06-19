package fitconnect.management;
import fitconnect.activities.Activities;
import fitconnect.activities.Reservation;
import fitconnect.users.Admins;
import fitconnect.users.Coaches;
import fitconnect.users.Members;
import fitconnect.users.Users;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private ArrayList<Users> usersList;

    public UserManager() {
        this.usersList = new ArrayList<>();
    }

    public void addUser(Users user) {
        this.usersList.add(user);
    }

    public void removeUser(Users user) {
        this.usersList.remove(user);
    }

    public ArrayList<Users> getUsersList() {
        return usersList;
    }

    public void saveUsersToFile(String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Users user : usersList) {
                if (user instanceof Admins) {
                    fileWriter.write("Admin," + user.getFirstName() + "," + user.getLastName() + "," + user.getDateOfBirth() + "," + user.getId() + "," + user.getPassword() + "\n");
                } else if (user instanceof Members) {
                    Members member = (Members) user;
                    fileWriter.write("Member," + user.getFirstName() + "," + user.getLastName() + "," + user.getDateOfBirth() + "," + user.getId() + "," + user.getPassword() + "," + member.getMembershipType() + "," + member.getDateOfMembership() + "\n");
                } else if (user instanceof Coaches) {
                    Coaches coach = (Coaches) user;
                    fileWriter.write("Coach," + user.getFirstName() + "," + user.getLastName() + "," + user.getDateOfBirth() + "," + user.getId() + "," + user.getPassword() + "," + coach.getSpecialty() + "\n");
                }
            }
        }
    }
    public Users getUserById(String id) {
        for (Users user : usersList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public static int mainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*Welcome to FitConnect!*");
        System.out.println("Please select an option:");
        System.out.println("1. Log in as administrator");
        System.out.println("2. Log in as a member");
        System.out.println("3. Log in as coach");
        System.out.println("4. Login as Guest");
        System.out.println("5. Log out");

        int option = scanner.nextInt();
        scanner.nextLine();

        return option;
    }

    public static int showAdminMenu(Scanner scanner) {
        System.out.println("--------Administrator menu----------");
        System.out.println("1. Add new member");
        System.out.println("2. Modify data of a member");
        System.out.println("3. Delete a member");
        System.out.println("4. Add new activity");
        System.out.println("5. Delete an activity");
        System.out.println("6. Modify data of an activity");
        System.out.println("7. Add a trainer");
        System.out.println("8. View club data");
        System.out.println("9. Security save");
        System.out.println("10. Exit to main menu");

        int adminOption = scanner.nextInt();
        scanner.nextLine();

        return adminOption;
    }
    public static int showMemberMenu(Scanner scanner) {
        System.out.println("-------Member menu---------");
        System.out.println("1. View activities");
        System.out.println("2. Reserve an activity");
        System.out.println("3. View my reservations");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. Write a review");
        System.out.println("6. View my reviews");
        System.out.println("7. Generate and Invitation Code");
        System.out.println("8. Exit");

        int memberOption = scanner.nextInt();
        scanner.nextLine();

        return memberOption;
    }

    public static int showCoachMenu(Scanner scanner) {
        System.out.println("--------Coach menu------------");
        System.out.println("1. Take member measurements");
        System.out.println("2. View member measurements");
        System.out.println("3. Modify member measurements");
        System.out.println("4. Assign yourself an activity ");
        System.out.println("5. View my activities");
        System.out.println("6. Cancel assignment");

        System.out.println("7. Exit");

        int coachOption = scanner.nextInt();
        scanner.nextLine();

        return coachOption;
    }


    public boolean isUserExists(String userId) {
        return usersList.stream().anyMatch(user -> user.getId().equals(userId));
    }


    public static boolean userExists(String id, UserManager userManager) {
        for (Users user : userManager.getUsersList()) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    public static void addNewMember(Scanner scanner, UserManager userManager) {
        System.out.println("Enter name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter the date of birth (dd/mm/yyyy):");
        String dateOfBirth = scanner.nextLine();
        System.out.println("Enter the ID:");
        String id = scanner.nextLine();
        if(userExists(id,userManager)) {
            System.out.println("Error:A member with this ID already exists.");
        }
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String membershipType;
        do {
            System.out.println("Enter the membership type (gold or platinum):");
            membershipType = scanner.nextLine().toLowerCase();

            if (!membershipType.equals("gold") && !membershipType.equals("platinum")) {
                System.out.println("Error: Please enter 'gold' or 'platinum'.");
            }
        } while (!membershipType.equals("gold") && !membershipType.equals("platinum"));

        System.out.println("Enter the membership date (dd/mm/yyyy):");
        String dateOfMembership = scanner.nextLine();

        userManager.addUser(new Members(firstName, lastName, dateOfBirth, id, password, membershipType, dateOfMembership));

        // Save the updated list of users in the file
        try {
            userManager.saveUsersToFile("users.txt");
            System.out.println("Member successfully added.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public static void editMember(Scanner scanner, UserManager userManager) {
        System.out.println("Enter the ID of the member you wish to modify:");
        String memberId = scanner.nextLine();

        Members memberToModify = null;
        for (Users user : userManager.getUsersList()) {
            if (user instanceof Members && user.getId().equals(memberId)) {
                memberToModify = (Members) user;
                break;
            }
        }

        if (memberToModify != null) {
            System.out.println("Enter the new name or press Enter to keep the current name:");
            String newFirstName = scanner.nextLine();
            if (!newFirstName.isEmpty()) {
                memberToModify.setFirstName(newFirstName);
            }

            System.out.println("Enter the new surname or press Enter to keep the current name:");
            String newLastName = scanner.nextLine();
            if (!newLastName.isEmpty()) {
                memberToModify.setLastName(newLastName);
            }

            System.out.println("Enter the new date of birth (dd/mm/yyyy) or press Enter to keep the current one:");
            String newDateOfBirth = scanner.nextLine();
            if (!newDateOfBirth.isEmpty()) {
                memberToModify.setDateOfBirth(newDateOfBirth);
            }

            System.out.println("Enter the new ID or press Enter to keep the current one:");
            String newId = scanner.nextLine();
            if (!newId.isEmpty()) {
                memberToModify.setId(newId);
            }

            System.out.println("Enter the new password or press Enter to keep the current password:");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                memberToModify.setPassword(newPassword);
            }

            System.out.println("Enter the new membership type or press Enter to keep the current one:");
            String newMembershipType = scanner.nextLine();
            if (!newMembershipType.isEmpty()) {
                memberToModify.setMembershipType(newMembershipType);
            }

            System.out.println("Enter the new membership date (dd/mm/yyyy) or press Enter to keep the current one:");
            String newDateOfMembership = scanner.nextLine();
            if (!newDateOfMembership.isEmpty()) {
                memberToModify.setDateOfMembership(newDateOfMembership);
            }

            System.out.println("Partner data successfully modified.");
            try {
                userManager.saveUsersToFile("users.txt");
            } catch (IOException e) {
                System.out.println("Error saving user data to file.");
            }
        } else {
            System.out.println("The partner with the ID provided was not found.");
        }
    }

    public static void deleteMember(Scanner scanner, UserManager userManager){
        System.out.println("Enter the ID of the member you wish to delete:");
        String memberToDeleteId = scanner.nextLine();

        Users memberToDelete = null;
        for (Users user : userManager.getUsersList()) {
            if (user instanceof Members && user.getId().equals(memberToDeleteId)) {
                memberToDelete = user;
                break;
            }
        }

        if (memberToDelete != null) {
            userManager.removeUser(memberToDelete);
            System.out.println("Partner successfully removed.");

            // Save user data in the file after user deletion
            try {
                userManager.saveUsersToFile("users.txt");
            } catch (IOException e) {
                System.out.println("Error saving user data to file.");
            }
        } else {
            System.out.println("Partner with the provided ID was not found.");
        }
    }

    public static void addNewCoach(Scanner scanner, UserManager userManager) {
        System.out.println("Enter name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter the date of birth (dd/mm/yyyy):");
        String dateOfBirth = scanner.nextLine();
        System.out.println("Enter the ID:");
        String id = scanner.nextLine();
        if(userExists(id,userManager)) {
            System.out.println("Error:A coach with this ID already exists.");
        }
        System.out.println("Enter password");
        String password = scanner.nextLine();
        System.out.println("Enter the specialty");
        String speciality = scanner.nextLine();
        userManager.addUser(new Coaches(firstName, lastName, dateOfBirth, id, password, speciality));

        // Save the updated list of users in the file
        try {
            userManager.saveUsersToFile("users.txt");
            System.out.println("Coach successfully added.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }


    public void loadUsersFromFile(String filename) throws IOException {
        usersList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String userType = data[0];

                if (userType.equals("Admin")) {
                    usersList.add(new Admins(data[1], data[2], data[3], data[4], data[5]));
                } else if (userType.equals("Member")) {
                    usersList.add(new Members(data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
                } else if (userType.equals("Coach")) {
                    usersList.add(new Coaches(data[1], data[2], data[3], data[4], data[5], data[6]));
                }
            }
        }
    }
    public static Users login(Scanner scanner, UserManager userManager) {
        System.out.println("Please enter your ID:");
        String id = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        Users userFound = null;
        for (Users user : userManager.getUsersList()) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                userFound = user;
                break;
            }
        }

        if (userFound == null) {
            System.out.println("Login failed, verify your credentials.");
        }

        return userFound;
    }

    public static void dataClub(UserManager userManager, ActivitiesManager activitiesManager) {
        try {
            userManager.loadUsersFromFile("users.txt");
            activitiesManager.loadActivitiesFromFile("activities.txt");
        } catch (IOException e) {
            System.out.println("Error loading file data.");

        }

        System.out.println("Club data:");

        System.out.println("Admins:");
        for (Users user : userManager.getUsersList()) {
            if (user instanceof Admins) {
                System.out.println(user.toString());
            }
        }

        System.out.println("\nMembers:");
        for (Users user : userManager.getUsersList()) {
            if (user instanceof Members) {
                System.out.println(user.toString());
            }
        }

        System.out.println("\nCoaches:");
        for (Users user : userManager.getUsersList()) {
            if (user instanceof Coaches) {
                System.out.println(user.toString());
            }
        }

        System.out.println("\nActivities:");
        for (Activities activity : activitiesManager.getActivitiesList()) {
            System.out.println(activity.toString());
        }
    }


    public static void securityBackup(ActivitiesManager activitiesManager, UserManager userManager) {
        try {
            userManager.saveUsersToFile("backup_users.txt");
            activitiesManager.saveActivitiesToFile("backup_activities.txt");
            System.out.println("Data saved successfully!");
        } catch(IOException e) {
            System.out.println("Error saving data.");
        }
    }



}

