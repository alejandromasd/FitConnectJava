package fitconnect.users;

import fitconnect.activities.Activities;
import fitconnect.activities.Reservation;
import fitconnect.management.ReservationsManager;

import java.util.Date;

public class Members extends Users {
    private String membershipType;
    private String dateOfMembership;
    private int invitationCount;

    public Members(String firstName, String lastName, String dateOfBirth, String id, String password, String membershipType, String dateOfMembership) {
        super(firstName, lastName, dateOfBirth, id, password);
        this.membershipType = membershipType;
        this.dateOfMembership = dateOfMembership;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getDateOfMembership() {
        return dateOfMembership;
    }

    public void setDateOfMembership(String dateOfMembership) {
        this.dateOfMembership = dateOfMembership;
    }

    public int getInvitationCount() {
        return this.invitationCount;
    }
    public void decrementInvitationCount() {
        if (this.invitationCount > 0) {
            this.invitationCount--;
        }
    }
    public void reserveActivity(ReservationsManager reservationsManager, Activities activity, Date reservationDate) {
        Reservation reservation = new Reservation(this, activity, reservationDate);
        reservationsManager.addReservation(reservation);
    }


    @Override
    public String toString() {
        return "Member: " + getFirstName() + " " + getLastName() + " (ID: " + getId() + ", Type of membership: " + getMembershipType() + ", Date of membership: " + getDateOfMembership() + ")";
    }
}





