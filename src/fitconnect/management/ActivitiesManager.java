package fitconnect.management;
import fitconnect.activities.Activities;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivitiesManager {
    private ArrayList<Activities> activitiesList;

    public ActivitiesManager() {
        this.activitiesList = new ArrayList<>();
    }

    public void addActivity(Activities activity) {
        this.activitiesList.add(activity);
    }

    public void removeActivity(Activities activity) {
        this.activitiesList.remove(activity);
    }

    public ArrayList<Activities> getActivitiesList() {
        return activitiesList;
    }

    public Activities getActivityByName(String name) {
        for (Activities activity : activitiesList) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public void viewActivities() {
        if (activitiesList.isEmpty()) {
            System.out.println("No activities available.");
        } else {
            System.out.println("Activities:");
            for (Activities activity : activitiesList) {
                System.out.println("Name: " + activity.getName());
                System.out.println("Description: " + activity.getDescription());
                System.out.println("Date: " + activity.getDate());
                System.out.println("Time: " + activity.getTime());
                System.out.println("--------------------");
            }
        }
    }


    public void saveActivitiesToFile(String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Activities activity : activitiesList) {
                fileWriter.write(activity.getName() + "," + activity.getDescription() + "," + activity.getDate() + "," + activity.getTime() + "\n");
            }
        }
    }
    public void loadActivitiesFromFile(String filename) throws IOException {
        activitiesList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                activitiesList.add(new Activities(data[0], data[1], data[2], data[3]));
            }
        }
    }

    public static void addActivity(Scanner scanner,ActivitiesManager activitiesManager){
        // Add new activity
        System.out.println("Enter the name of the activity:");
        String activityName = scanner.nextLine();
        System.out.println("Enter the description of the activity:");
        String activityDescription = scanner.nextLine();
        System.out.println("Enter the date of the activity (dd/mm/yyyy):");
        String activityDate = scanner.nextLine();
        System.out.println("Enter the time of the activity (hh:mm):");
        String activityTime = scanner.nextLine();

        activitiesManager.addActivity(new Activities(activityName, activityDescription, activityDate, activityTime));
        System.out.println("Activity added successfully.");

        // Save the activities in the file after adding a new activity.
        try {
            activitiesManager.saveActivitiesToFile("activities.txt");
        } catch (IOException e) {
            System.out.println("Error saving activities data to file.");
        }
    }


    public static void deleteActivity(Scanner scanner, ActivitiesManager activitiesManager) {
        // Delete an activity.
        System.out.println("Enter the name of the activity you want to delete:");
        String activityToDeleteName = scanner.nextLine();

        Activities activityToDelete = null;
        for (Activities activity : activitiesManager.getActivitiesList()) {
            if (activity.getName().equals(activityToDeleteName)) {
                activityToDelete = activity;
                break;
            }
        }

        if (activityToDelete != null) {
            activitiesManager.removeActivity(activityToDelete);
            System.out.println("Activity successfully deleted.");

            // Save the changes to the file
            try {
                activitiesManager.saveActivitiesToFile("activities.txt");
            } catch (IOException e) {
                System.out.println("Error saving activities data to file.");
            }
        } else {
            System.out.println("Activity with the provided name was not found.");
        }

    }
    public static void modifyActivity(Scanner scanner, ActivitiesManager activitiesManager) {
        // Modify data of an activity.
        System.out.println("Enter the name of the activity you want to modify:");
        String activityToModifyName = scanner.nextLine();

        Activities activityToModify = null;
        for (Activities activity : activitiesManager.getActivitiesList()) {
            if (activity.getName().equals(activityToModifyName)) {
                activityToModify = activity;
                break;
            }
        }

        if (activityToModify != null) {
            System.out.println("Enter the new name or press Enter to keep the current one:");
            String newActivityName = scanner.nextLine();
            if (!newActivityName.isEmpty()) {
                activityToModify.setName(newActivityName);
            }

            System.out.println("Enter the new description or press Enter to keep the current one:");
            String newActivityDescription = scanner.nextLine();
            if (!newActivityDescription.isEmpty()) {
                activityToModify.setDescription(newActivityDescription);
            }

            System.out.println("Enter the new date (dd/mm/yyyy) or press Enter to keep the current one:");
            String newActivityDate = scanner.nextLine();
            if (!newActivityDate.isEmpty()) {
                activityToModify.setDate(newActivityDate);
            }

            System.out.println("Activity data modified successfully.");

            // Save activities to file after modifying an activity.
            try {
                activitiesManager.saveActivitiesToFile("activities.txt");
            } catch (IOException e) {
                System.out.println("Error saving activities data to file.");
            }
        } else {
            System.out.println("Activity with the provided name was not found.");
        }
    }






}


