package fitconnect.management;
import fitconnect.activities.Activities;
import java.io.*;
import java.util.Scanner;

public class CoachManager {
    private ActivitiesManager activitiesManager;

    public CoachManager(ActivitiesManager activitiesManager) {
        this.activitiesManager = activitiesManager;
    }

    public void assignActivityToCoach(Scanner scanner, String coachId) {
        // Display all available activities
        activitiesManager.viewActivities();
        System.out.println("Enter the name of the activity you want to assign yourself to:");
        String activityName = scanner.nextLine();
        Activities activity = activitiesManager.getActivityByName(activityName);
        if (activity != null) {
            activity.setCoachId(coachId);
            System.out.println("You have been assigned to the activity " + activityName);
            // Save activity assignments to file after assigning a coach.
            try {
                saveActivityAssignmentsToFile("activityAssignments.txt");
            } catch (IOException e) {
                System.out.println("Error saving activity assignments to file.");
            }
        } else {
            System.out.println("Activity with the provided name was not found.");
        }
    }

    public void displayAssignedActivities(String coachId) {
        System.out.println("Your assigned activities:");
        for (Activities activity : activitiesManager.getActivitiesList()) {
            if (activity.getCoachId() != null && activity.getCoachId().equals(coachId)) {
                System.out.println(activity);
            }
        }
    }


    public void removeActivityAssignment(Scanner scanner, String coachId) {
        System.out.println("Enter the name of the activity you want to unassign from:");
        String activityName = scanner.nextLine();
        Activities activity = activitiesManager.getActivityByName(activityName);

        if (activity != null && activity.getCoachId() != null && activity.getCoachId().equals(coachId)) {
            System.out.println("Are you sure you want to unassign from " + activityName + "? Type yes to confirm.");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                activity.setCoachId(null);
                System.out.println("You have been unassigned from the activity " + activityName);
            }
        } else {
            System.out.println("You are not assigned to the activity with the provided name.");
        }
    }


    public void saveActivityAssignmentsToFile(String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Activities activity : activitiesManager.getActivitiesList()) {
                if (activity.getCoachId() != null) {
                    fileWriter.write(activity.getName() + "," + activity.getCoachId() + "\n");
                }
            }
        }
    }

    public void loadActivityAssignmentsFromFile(String filename) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Activities activity = activitiesManager.getActivityByName(data[0]);
                if (activity != null) {
                    activity.setCoachId(data[1]);
                }
            }
        }
    }

}
