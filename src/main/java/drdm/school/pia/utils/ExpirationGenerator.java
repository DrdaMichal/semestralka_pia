package drdm.school.pia.utils;

import java.util.Date;

public interface ExpirationGenerator {
    public String generateExpiration(Date startDate, int monthsToAdd);

    public String generateExpiration(int monthsToAdd);
}
