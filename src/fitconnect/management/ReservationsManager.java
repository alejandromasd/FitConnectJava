package fitconnect.management;
import fitconnect.activities.Activities;
import fitconnect.activities.Reservation;
import fitconnect.users.Members;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ReservationsManager {
    private ArrayList<Reservation> reservations;

    public ReservationsManager() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void reserveActivityForMember(Scanner scanner, Members currentUser, ActivitiesManager activitiesManager) {
        activitiesManager.viewActivities();
        System.out.println("Enter the name of the activity you want to reserve:");
        String activityName = scanner.nextLine();
        Activities activityToReserve = activitiesManager.getActivityByName(activityName);

        if (activityToReserve != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                Date reservationDate = sdf.parse(activityToReserve.getDate() + " " + activityToReserve.getTime());
                addReservation(new Reservation(currentUser, activityToReserve, reservationDate));
                System.out.println("Activity reserved successfully.");
                saveReservationsToFile("reservations.txt"); // Guardar las reservas en el archivo
            } catch (ParseException e) {
                System.out.println("Error parsing date and time. Please try again.");
            }
        } else {
            System.out.println("Activity with the provided name was not found.");
        }
    }
    public void cancelActivityReservation(Scanner scanner, Members currentUser) {
        viewMemberReservations(currentUser);

        System.out.println("Enter the name of the activity you want to cancel:");
        String activityName = scanner.nextLine();
        Reservation reservationToCancel = null;

        for (Reservation reservation : reservations) {
            if (reservation.getMember().equals(currentUser) && reservation.getActivity().getName().equalsIgnoreCase(activityName)) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Are you sure you want to cancel this reservation? (yes/no)");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                reservations.remove(reservationToCancel);
                saveReservationsToFile("reservations.txt");
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Reservation not canceled.");
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }


    public void viewMemberReservations(Members member) {
        System.out.println("Your reservations:");
        boolean hasReservations = false;
        for (Reservation reservation : reservations) {
            Members reservationMember = reservation.getMember();
            if (reservationMember != null && reservationMember.equals(member)) {
                System.out.println(reservation);
                System.out.println("--------------------");
                hasReservations = true;
            }
        }
        if (!hasReservations) {
            System.out.println("You have no reservations.");
        }
    }

    public void saveReservationsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.getMember().getId() + "," + reservation.getActivity().getName() + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reservation.getReservationDate()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations to file: " + e.getMessage());
        }
    }



    public void loadReservationsFromFile(String filename, UserManager userManager, ActivitiesManager activitiesManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String memberId = parts[0].trim();
                String activityName = parts[1].trim();
                Date reservationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(parts[2].trim());

                Members member = (Members) userManager.getUserById(memberId);
                Activities activity = activitiesManager.getActivityByName(activityName);
                Reservation reservation = new Reservation(member, activity, reservationDate);
                reservations.add(reservation);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error loading reservations from file: " + e.getMessage());
        }
    }
    public void saveGuestReservation(String invitationCode, Activities activity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String reservationDate = outputFormat.format(inputFormat.parse(activity.getDate() + " " + activity.getTime()));
            writer.write(invitationCode + "," + activity.getName() + "," + reservationDate);
            writer.newLine();
        } catch (IOException | ParseException e) {
            System.out.println("Error saving guest reservation to file: " + e.getMessage());
        }
    }


}

