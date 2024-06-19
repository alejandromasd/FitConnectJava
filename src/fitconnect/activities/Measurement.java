package fitconnect.activities;


public class Measurement {
    private String memberId;
    private String coachId;
    private double weight;
    private double muscleMassPercentage;
    private double fatPercentage;
    private String mainGoal;

    public Measurement(String memberId, double weight, double muscleMassPercentage, double fatPercentage, String mainGoal, String coachId) {
        this.memberId = memberId;
        this.weight = weight;
        this.muscleMassPercentage = muscleMassPercentage;
        this.fatPercentage = fatPercentage;
        this.mainGoal = mainGoal;
        this.coachId = coachId;
    }

    public String getMemberId() {
        return memberId;
    }

    public double getWeight() {
        return weight;
    }

    public double getMuscleMassPercentage() {
        return muscleMassPercentage;
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public String getMainGoal() {
        return mainGoal;
    }
    public String getCoachId() {
        return coachId;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setMuscleMassPercentage(double muscleMassPercentage) {
        this.muscleMassPercentage = muscleMassPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }


    @Override
    public String toString() {
        return memberId + ", " + weight + ", " + muscleMassPercentage + ", " + fatPercentage + ", " + mainGoal + ", " + coachId;
    }
}
