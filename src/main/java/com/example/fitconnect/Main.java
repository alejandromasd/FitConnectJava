//Alejandro Mas Diego 1ºDAM
package com.example.fitconnect;

import fitconnect.management.*;
import fitconnect.users.Admins;
import fitconnect.users.Coaches;
import fitconnect.users.Members;
import fitconnect.users.Users;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import static fitconnect.management.ActivitiesManager.*;
import static fitconnect.management.UserManager.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        ActivitiesManager activitiesManager = new ActivitiesManager();
        ReservationsManager reservationsManager = new ReservationsManager();

        try {
            userManager.loadUsersFromFile("users.txt");
        } catch (IOException e) {
            System.out.println("Error loading user data from file.");
        }
        try {
            activitiesManager.loadActivitiesFromFile("activities.txt");
        } catch (IOException e) {
            System.out.println("Error loading activity data from file.");
        }
        reservationsManager.loadReservationsFromFile("reservations.txt", userManager, activitiesManager);
        ReviewsManager reviewsManager = new ReviewsManager();
        reviewsManager.loadReviewsFromFile("reviews.txt");
        InvitationManager invitationManager = new InvitationManager();
        invitationManager.loadInvitationCodesFromFile("invitation_codes.txt");
        MeasurementsManager measurementsManager = new MeasurementsManager();
        measurementsManager.loadMeasurementsFromFile("measurements.txt"); // Cargar medidas de un archivo
        CoachManager coachManager = new CoachManager(activitiesManager);
        try {
            coachManager.loadActivityAssignmentsFromFile("activityAssignments.txt");
        } catch (IOException e) {
            System.out.println("Error loading activity assignments from file.");
        }

        //Access to admin menu is: id admin password admin
        while (true) {
            int option = mainMenu();

            switch (option) {
                //Admin menu: id admin password admin
                case 1:
                    Users currentUser = login(scanner, userManager);
                    if (currentUser instanceof Admins) {
                        System.out.println("Successful login as administrator!");
                        boolean exitAdmin = true;
                        while(exitAdmin) {
                            int adminOption = showAdminMenu(scanner);
                            switch (adminOption) {
                                case 1:
                                    addNewMember(scanner,userManager);
                                    break;
                                case 2:
                                    editMember(scanner, userManager);
                                    break;
                                case 3:
                                    deleteMember(scanner, userManager);
                                    break;
                                case 4:
                                    addActivity(scanner, activitiesManager);
                                    break;
                                case 5:
                                    deleteActivity(scanner,activitiesManager);
                                    break;
                                case 6:
                                    modifyActivity(scanner, activitiesManager);
                                    break;
                                case 7:
                                    addNewCoach(scanner,userManager);
                                    break;
                                case 8:
                                    dataClub(userManager,activitiesManager);
                                    break;
                                case 9:
                                    securityBackup(activitiesManager, userManager);
                                    break;
                                case 10:
                                    exitAdmin = false;
                                    System.out.println("Returning to the main menu...");
                                    break;
                                default:
                                    System.out.println("Invalid option. Please try again.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Incorrect ID or password. Please try again.");
                    }
                    break;
                case 2:
                    //Member menu
                    Users currentUser2 = login(scanner, userManager);
                    if (currentUser2 instanceof Members) {
                        System.out.println("Successful partner login!");
                        int memberOption;
                        do {
                            memberOption = showMemberMenu(scanner);
                            switch (memberOption) {
                                case 1:
                                    activitiesManager.viewActivities();
                                    break;
                                case 2:
                                    reservationsManager.reserveActivityForMember(scanner, (Members) currentUser2, activitiesManager);
                                    break;
                                case 3:
                                    reservationsManager.viewMemberReservations((Members) currentUser2);
                                    break;
                                case 4:
                                    reservationsManager.cancelActivityReservation(scanner, (Members) currentUser2);
                                    break;
                                case 5:
                                    reviewsManager.createReviewFromUserInput(scanner, currentUser2);
                                    break;
                                case 6:
                                    if (currentUser2 instanceof Members) {
                                        reviewsManager.displayMemberReviews(scanner, (Members) currentUser2);
                                    } else {
                                        System.out.println("Error: Current user is not a member.");
                                    }
                                    break;
                                case 7:
                                    //GenerateCode Window JAVAFX
                                    InvitationCodeWindow.run(args);
                                    break;
                                case 8:
                                    System.out.println("Returning to the main menu...");
                                    break;
                                default:
                                    System.out.println("Invalid option. Please try again.");
                                    break;
                            }
                        } while (memberOption != 8);
                    } else {
                        System.out.println("Incorrect ID or password. Please try again.");
                    }
                    break;
                //Coach menu
                case 3:
                    Users currentUser3 = login(scanner, userManager);
                    if (currentUser3 instanceof Coaches) {
                        System.out.println("Successful login as trainer!");
                        int coachOption;
                        do {
                            coachOption = showCoachMenu(scanner);
                            switch (coachOption) {
                                case 1:
                                    measurementsManager.takeMeasurements(scanner, measurementsManager, userManager, currentUser3.getId());
                                    break;
                                case 2:
                                    measurementsManager.displayMeasurementsByCoach(currentUser3.getId());
                                    break;
                                case 3:
                                    measurementsManager.updateMeasurement(scanner);
                                    break;
                                case 4:
                                    coachManager.assignActivityToCoach(scanner, currentUser3.getId());
                                    break;
                                case 5:
                                    coachManager.displayAssignedActivities(currentUser3.getId());
                                    break;
                                case 6:
                                    coachManager.removeActivityAssignment(scanner, currentUser3.getId());
                                    break;
                                default:
                                    System.out.println("Invalid option. Please try again.");
                                    break;
                            }
                        } while (coachOption != 7);
                    } else {
                        System.out.println("Incorrect ID or password. Please try again.");
                    }
                    break;
                case 4:
                    //Guest Menu
                    InvitationManager.reserveActivityForGuest(activitiesManager, reservationsManager, scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using FitConnect app!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
