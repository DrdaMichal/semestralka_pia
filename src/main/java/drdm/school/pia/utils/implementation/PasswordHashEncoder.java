package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.Encoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Dummy encoder using PasswordHash class as a logic source
 * @author Michal Drda
 */
@Component
public class PasswordHashEncoder implements Encoder {

    /**
     * @inheritDoc
     * Method used for calling PasswordHash encoder
     * @param text provided text to be encoded
     * @return encoded text or null in case of exception
     */
    @Override
    public String encode(String text) {
        try {
            return PasswordHash.createHash(text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @inheritDoc
     * Medhod used for validation of the text comparing it to hash
     * @param text plaintext form
     * @param hash hash for comparison
     * @return
     */
    @Override
    public boolean validate(String text, String hash) {
        try {
            return PasswordHash.validatePassword(text, hash);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

}
