import java.io.Serializable;
import java.time.LocalDateTime;

public class ResearchData implements Serializable {
    private LocalDateTime timestamp; // Add timestamp field

    public ResearchData(int userID, String postcode, double co2Concentration) {
        this.timestamp = LocalDateTime.now(); // Set timestamp to current time
    }

    // Getters for other fields...

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCo2Concentration() {
        return null;
    }

    public String getPostcode() {
        return null;
    }

    public String getUserID() {
        return null;
    }
}