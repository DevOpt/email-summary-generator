import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public class EmailSummary {

    public static void main(String... args) throws GeneralSecurityException, IOException, ParseException {
        EmailSummaryGenerator summaryGenerator = new EmailSummaryGenerator();
        summaryGenerator.generate();

        // TODO: Add BH to calendar schedule
        // TODO: NLP spam detection
    }
}
