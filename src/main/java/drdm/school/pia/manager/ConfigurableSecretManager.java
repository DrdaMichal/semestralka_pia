package drdm.school.pia.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Date: 29.11.16
 *
 * @author Jakub Danek
 */
@Service
//This is a bug, when the annotation is commented-out, we dont have access to the method protected!
//commented out just for demonstration purposes
@PreAuthorize("authenticated")
public class ConfigurableSecretManager implements SecretManager {

    @Value("${secret.value}")
    private String secret;

    @Override
    public String getSecret() {
        return secret;
    }
}
