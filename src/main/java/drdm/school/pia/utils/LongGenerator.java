package drdm.school.pia.utils;

/**
 * Long generator interface
 * @author Michal Drda
 */
public interface LongGenerator {

    /**
     * Returns generated long
     * @param length number of digits of the Long to be generated
     * @return generated Long value
     */
    long generate(int length);

}
