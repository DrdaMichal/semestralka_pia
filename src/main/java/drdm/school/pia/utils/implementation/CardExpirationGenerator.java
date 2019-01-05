package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.ExpirationGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * {@inheritDoc}
 * Used for card expiration generation
 * @author Michal Drda
 */
@Component
public class CardExpirationGenerator implements ExpirationGenerator {

    /**
     * Initialization of the date with current date value
     */
    private final Date currentDate = new Date();
    /**
     * Initialization of the calendar instance
     */
    private final Calendar c = Calendar.getInstance();
    /**
     * Validation pattern link
     */
    @Value("${expiration.pattern.date}")
    String validationPattern;

    /**
     * {@inheritDoc}
     * Used for expiration generation
     */
    @Override
    public String generateExpiration(int monthsToAdd) {
        DateFormat dateFormat = new SimpleDateFormat(validationPattern);
        c.setTime(currentDate);
        c.add(Calendar.MONTH, monthsToAdd);
        Date exactExpirationDate = c.getTime();
        String date = dateFormat.format(exactExpirationDate);
        return date.substring(5, 7) + "/" + date.substring(2, 4);
    }
}
