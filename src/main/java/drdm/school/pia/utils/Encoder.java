package drdm.school.pia.utils;

/**
 * Encoder interface used for encoding secret values
 * @author Michal Drda
 */
public interface Encoder {

    /**
     * Returns hash of the passed text.
     * @param text to be encoded
     * @return hashed text
     */
    String encode(String text);

    /**
     * Validates that the text is the plaintext form associated with the hash.
     * @param text plaintext form
     * @param hash hash for comparison
     * @return true of correct
     */
    boolean validate(String text, String hash);
}
