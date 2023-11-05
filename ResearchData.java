import java.io.Serializable;

public class ResearchData implements Serializable {
    private int userID;
    private String postcode;
    private double co2Concentration;

    public ResearchData(int userID, String postcode, double co2Concentration) {
        this.userID = userID;
        this.postcode = postcode;
        this.co2Concentration = co2Concentration;
    }

    public int getUserID() {
        return userID;
    }

    public String getPostcode() {
        return postcode;
    }

    public double getCo2Concentration() {
        return co2Concentration;
    }
}