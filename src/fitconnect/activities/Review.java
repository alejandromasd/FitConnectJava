package fitconnect.activities;

public class Review {
    private String memberId;
    private String comment;
    private int rating;

    public Review(String memberId, String comment, int rating) {
        this.memberId = memberId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + "\nComment: " + comment + "\nRating: " + rating + " stars";
    }
}

