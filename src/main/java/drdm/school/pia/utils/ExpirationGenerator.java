package drdm.school.pia.utils;

import java.util.Date;

/**
 * Expiration generator interface
 * @author Michal Drda
 */
public interface ExpirationGenerator {

    /**
     * Generates an expiration by adding months to the current date
     * @param monthsToAdd months to be added to the start date (count)
     * @return string in format MM/YY
     */
    public String generateExpiration(int monthsToAdd);

}
