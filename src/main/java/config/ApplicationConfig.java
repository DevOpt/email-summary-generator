package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import model.AppointmentIdentifier;

import java.util.List;

@Data
public class ApplicationConfig {
    /**
     * List of identifiers that need to be tracked when reporting email summary
     */
    @JsonProperty("appointmentIdentifiers")
    private List<AppointmentIdentifier> appointmentIdentifiers;

    /**
     * User's gmail account email address
     */
    @NonNull
    @JsonProperty("userId")
    private String userId;

    public ApplicationConfig() {}
}
