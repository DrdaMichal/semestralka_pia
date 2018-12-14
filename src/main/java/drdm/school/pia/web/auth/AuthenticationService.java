package drdm.school.pia.web.auth;

import drdm.school.pia.domain.Role;
import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Wrapper around HttpSession providing authentication functionality.
 * @author Michal Drda
 */
@Service
public class AuthenticationService {

    private static final String USER = "user";
    private static final String ROLE = "role";

    private UserManager userManager;

    @Autowired
    public AuthenticationService(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Signs in the user, if username and password match
     *
     * @param session session associated with the request
     * @param username provided username
     * @param password provided password
     * @return true if success, false otherwise
     */
    public boolean authenticate(HttpSession session, String username, String password) {
        boolean authenticated = userManager.authenticate(username, password);



        if(authenticated) {
            Role userRole = userManager.userRole(username);
            session.setAttribute(USER, username);
            session.setAttribute(ROLE, userRole.getName());
            return true;
        }

        return false;
    }

    /**
     *
     * @param session session associated with the request
     * @return true if there is a user currently logged in within this session.
     */
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(USER) != null;
    }

}
