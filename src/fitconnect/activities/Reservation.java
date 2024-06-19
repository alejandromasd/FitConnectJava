package fitconnect.activities;

import fitconnect.activities.Activities;
import fitconnect.users.Members;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Members member;
    private Activities activity;
    private Date reservationDate;

    public Reservation(Members member, Activities activity, Date reservationDate) {
        this.member = member;
        this.activity = activity;
        this.reservationDate = reservationDate;
    }

    public Members getMember() {
        return member;
    }

    public Activities getActivity() {
        return activity;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return "Member: " + member.getFirstName() + " " +
                member.getLastName() + ", Activity: " + activity.getName() + ", Reservation Date: " + sdf.format(reservationDate);
    }
}

