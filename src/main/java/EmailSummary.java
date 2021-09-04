import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApplicationConfig;

import java.io.InputStream;

public class EmailSummary {

    private static final String CONFIG_FILE_PATH = "/config.json";

    public static void main(String... args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        InputStream in = EmailSummary.class.getResourceAsStream(CONFIG_FILE_PATH);

        ApplicationConfig config = om.readValue(in, ApplicationConfig.class);
        System.out.println("Email: " + config.getUserId());

        EmailSummaryGenerator summaryGenerator = new EmailSummaryGenerator(config);
        summaryGenerator.generate();

        // TODO: NLP spam detection
    }
}
