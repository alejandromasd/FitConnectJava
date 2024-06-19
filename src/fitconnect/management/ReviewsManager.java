package fitconnect.management;


import fitconnect.activities.Review;
import fitconnect.users.Members;
import fitconnect.users.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReviewsManager {
    private ArrayList<Review> reviews;

    public ReviewsManager() {
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void createReviewFromUserInput(Scanner scanner, Users currentUser2) {
        System.out.println("Enter your comment about the club:");
        String comment = scanner.nextLine();
        System.out.println("Enter your rating from 1 to 5 stars:");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        Review review = new Review(currentUser2.getId(), comment, rating);
        addReview(review);
        saveReviewsToFile("reviews.txt");
        System.out.println("Review submitted successfully.");
    }


    public void saveReviewsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Review review : reviews) {
                writer.write(review.getMemberId() + "," + review.getComment() + "," + review.getRating());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reviews to file: " + e.getMessage());
        }
    }
    public void displayAllReviews() {
        System.out.println("Club Reviews:");
        for (Review review : reviews) {
            System.out.println("Rating: " + review.getRating() + " stars");
            System.out.println("Comment: " + review.getComment());
            System.out.println("--------------------");
        }
    }
    public void displayMemberReviews(Scanner scanner, Members currentUser2) {
        System.out.println("Your Reviews:");
        ArrayList<Review> memberReviews = new ArrayList<>();
        int reviewCount = 0;
        for (Review review : reviews) {
            if (review.getMemberId().equals(currentUser2.getId())) {
                reviewCount++;
                memberReviews.add(review);
                System.out.println(reviewCount + ". ");
                System.out.println("Rating: " + review.getRating() + " stars");
                System.out.println("Comment: " + review.getComment());
                System.out.println("--------------------");
            }
        }
        System.out.println("Would you like to delete any review? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Enter the number of the review you want to delete:");
            int reviewNumber = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (reviewNumber > 0 && reviewNumber <= memberReviews.size()) {
                System.out.println("Are you sure you want to delete this review? (yes/no)");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    Review reviewToDelete = memberReviews.get(reviewNumber - 1);
                    reviews.remove(reviewToDelete);
                    System.out.println("Review deleted successfully.");
                    saveReviewsToFile("reviews.txt");
                }
            } else {
                System.out.println("Invalid review number.");
            }
        }
    }



    public void loadReviewsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String memberId = parts[0].trim();
                String comment = parts[1].trim();
                int rating = Integer.parseInt(parts[2].trim());

                Review review = new Review(memberId, comment, rating);
                reviews.add(review);
            }
        } catch (IOException e) {
            System.out.println("Error loading reviews from file: " + e.getMessage());
        }
    }
}

