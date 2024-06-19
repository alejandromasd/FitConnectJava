package fitconnect.management;
import fitconnect.activities.Activities;
import fitconnect.users.Members;

import java.io.*;
import java.util.*;

public class InvitationManager {
    private static Map<String, Boolean> invitationCodes;

    public InvitationManager() {
        invitationCodes = new HashMap<>();
    }

    public static Map<String, Boolean> getInvitationCodes() {
        return invitationCodes;
    }


    public static void reserveActivityForGuest(ActivitiesManager activitiesManager,ReservationsManager reservationsManager, Scanner scanner) {
        System.out.println("--------Welcome guest to Menu---------");
        System.out.println("Enter your invitation code:");
        String invitationCode = scanner.nextLine();
        if (isValidCode(invitationCode)) {
            markCodeAsUsed(invitationCode);
            System.out.println("Invitation code is valid. You can now reserve an activity.");
            activitiesManager.viewActivities();
            System.out.println("Enter the name of the activity you want to reserve:");
            String activityName = scanner.nextLine();
            Activities activityToReserve = activitiesManager.getActivityByName(activityName);
            if (activityToReserve != null) {
                reservationsManager.saveGuestReservation(invitationCode, activityToReserve);
                System.out.println("Activity reserved successfully.");
                System.out.println("Enjoy!:");
            } else {
                System.out.println("Activity with the provided name was not found.");
            }
        } else {
            System.out.println("Invalid or already used invitation code.");
        }
    }


    public static String generateUniqueCode() {
        String code;
        do {
            code = String.format("%04d", new Random().nextInt(10000)) + (char) (new Random().nextInt(26) + 'A');
        } while (invitationCodes.containsKey(code));
        return code;
    }

    public static boolean isValidCode(String code) {
        return invitationCodes.containsKey(code) && !invitationCodes.get(code);
    }

    public static void markCodeAsUsed(String code) {
        if (invitationCodes.containsKey(code)) {
            invitationCodes.put(code, true);
            saveInvitationCodesToFile("invitation_codes.txt");
        }
    }

    public void loadInvitationCodesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String code = parts[0].trim();
                boolean used = Boolean.parseBoolean(parts[1].trim());
                invitationCodes.put(code, used);
            }
        } catch (IOException e) {
            System.out.println("Error loading invitation codes from file: " + e.getMessage());
        }
    }

    public static void saveInvitationCodesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Boolean> entry : invitationCodes.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving invitation codes to file: " + e.getMessage());
        }
    }
}

