package fitconnect.management;


import fitconnect.activities.Measurement;

import java.io.*;
import java.util.*;

public class MeasurementsManager {
    private List<Measurement> measurements;

    public MeasurementsManager() {
        measurements = new ArrayList<>();
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        saveMeasurementsToFile("measurements.txt");
    }

    public List<Measurement> getMeasurementsByCoach(String coachId) {
        List<Measurement> coachMeasurements = new ArrayList<>();
        for (Measurement measurement : measurements) {
            if (measurement.getCoachId().equals(coachId)) {
                coachMeasurements.add(measurement);
            }
        }
        return coachMeasurements;
    }

    public void updateMeasurement(Scanner scanner) {
        System.out.println("Enter the ID of the member whose measurements you want to modify:");
        String memberId = scanner.nextLine();
        Measurement measurementToUpdate = null;
        for (Measurement measurement : measurements) {
            if (measurement.getMemberId().equals(memberId)) {
                measurementToUpdate = measurement;
                break;
            }
        }
        if (measurementToUpdate == null) {
            System.out.println("No measurements found for member with ID " + memberId);
        } else {
            System.out.println("Current measurements: " + measurementToUpdate);
            System.out.println("Enter new weight (leave blank to keep current weight):");
            String weightInput = scanner.nextLine();
            double newWeight = weightInput.isEmpty() ? measurementToUpdate.getWeight() : Double.parseDouble(weightInput);
            measurementToUpdate.setWeight(newWeight);

            System.out.println("Enter new muscle mass percentage (leave blank to keep current percentage):");
            String muscleMassInput = scanner.nextLine();
            double newMuscleMassPercentage = muscleMassInput.isEmpty() ? measurementToUpdate.getMuscleMassPercentage() : Double.parseDouble(muscleMassInput);
            measurementToUpdate.setMuscleMassPercentage(newMuscleMassPercentage);

            System.out.println("Enter new fat percentage (leave blank to keep current percentage):");
            String fatPercentageInput = scanner.nextLine();
            double newFatPercentage = fatPercentageInput.isEmpty() ? measurementToUpdate.getFatPercentage() : Double.parseDouble(fatPercentageInput);
            measurementToUpdate.setFatPercentage(newFatPercentage);

            System.out.println("Enter new main goal (leave blank to keep current goal):");
            String newMainGoal = scanner.nextLine();
            newMainGoal = newMainGoal.isEmpty() ? measurementToUpdate.getMainGoal() : newMainGoal;
            measurementToUpdate.setMainGoal(newMainGoal);

            saveMeasurementsToFile("measurements.txt");
            System.out.println("Measurement updated successfully.");
        }
    }


    public void displayMeasurementsByCoach(String coachId) {
        List<Measurement> coachMeasurements = getMeasurementsByCoach(coachId);
        if (coachMeasurements.isEmpty()) {
            System.out.println("You haven't taken any measurements yet.");
        } else {
            System.out.println("These are the measurements you have taken:");
            for (Measurement measurement : coachMeasurements) {
                System.out.println(measurement);
            }
        }
    }

    public void loadMeasurementsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String memberId = parts[0].trim();
                double weight = Double.parseDouble(parts[1].trim());
                double muscleMassPercentage = Double.parseDouble(parts[2].trim());
                double fatPercentage = Double.parseDouble(parts[3].trim());
                String mainGoal = parts[4].trim();
                String coachId = parts[5].trim();
                measurements.add(new Measurement(memberId, weight, muscleMassPercentage, fatPercentage, mainGoal, coachId));
            }
        } catch (IOException e) {
            System.out.println("Error loading measurements from file: " + e.getMessage());
        }
    }

    public void saveMeasurementsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Measurement measurement : measurements) {
                writer.write(measurement.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving measurements to file: " + e.getMessage());
        }
    }

    public void takeMeasurements(Scanner scanner, MeasurementsManager measurementsManager, UserManager userManager, String coachId) {
        System.out.println("Enter the ID of the member:");
        String memberId = scanner.nextLine();

        // Check if the member exists
        if (userManager.isUserExists(memberId)) {
            System.out.println("Enter body weight (in kg):");
            double weight = scanner.nextDouble();

            System.out.println("Enter muscle mass percentage (%):");
            double muscleMassPercentage = scanner.nextDouble();

            System.out.println("Enter fat percentage (%):");
            double fatPercentage = scanner.nextDouble();

            scanner.nextLine();  // Consume newline left-over
            System.out.println("Enter main goal:");
            String mainGoal = scanner.nextLine();

            // Crear una nueva medida
            Measurement measurement = new Measurement(memberId, weight, muscleMassPercentage, fatPercentage, mainGoal,coachId);

            // Agregar la medida al gestor de medidas
            measurementsManager.addMeasurement(measurement);

            System.out.println("Measurement successfully recorded.");
        } else {
            System.out.println("Member with ID " + memberId + " does not exist.");
        }
    }


}

