package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.ExpirationGenerator;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Used for generation of Expiration Date for a Credit Card
 * @author Michal Drda
 */
@Component
public class CardExpirationGenerator implements ExpirationGenerator {

    private final Date currentDate = new Date();
    private final Calendar c = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * Adds months to date in parameter (not implemented) yet)
     * @param startDate date of period start (yyyy/MM/dd)
     * @param monthsToAdd months to be added
     * @return expirationDate in format MM/yy
     */
    @Override
    public String generateExpiration(Date startDate, int monthsToAdd) {
        return null;
        //Not implemented
    }

    /**
     * Adds months to current date
     * @param monthsToAdd months to be added
     * @return expirationDate in format MM/yy
     */
    @Override
    public String generateExpiration(int monthsToAdd) {
        c.setTime(currentDate);
        c.add(Calendar.MONTH, monthsToAdd);
        Date exactExpirationDate = c.getTime();
        String date = dateFormat.format(exactExpirationDate);
        return date.substring(5, 7) + "/" + date.substring(2, 4);
    }
}
