package drdm.school.pia.utils;

public interface LongGenerator {

    /**
     * Returns generated int
     * @param length
     * @return
     */
    long generate(int length);

    String generateBankAccount(int accountNoLength, String bankcode);
}
