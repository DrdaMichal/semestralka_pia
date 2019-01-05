package drdm.school.pia.web.auth;

import drdm.school.pia.domain.entities.Role;
import drdm.school.pia.manager.RoleManager;
import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Wrapper around HttpSession providing authentication functionality.
 * Used for security
 * @author Michal Drda
 */
@Service
public class AuthenticationService {

    /**
     * User parameter
     */
    private static final String USER = "user";
    /**
     * Role parameter
     */
    private static final String ROLE = "role";
    /**
     * Initialization of the user manager
     */
    private UserManager userManager;
    /**
     * Initialization of the role manager
     */
    private RoleManager roleManager;

    /**
     * Constructor used for spring linking of sources
     * @param userManager provided user manager
     * @param roleManager provided role manager
     */
    @Autowired
    public AuthenticationService(UserManager userManager, RoleManager roleManager) {
        this.userManager = userManager;
        this.roleManager = roleManager;
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
            Role userRole = roleManager.getUserRoleByUsername(username);
            session.setAttribute(USER, username);
            session.setAttribute(ROLE, userRole.getName());
            return true;
        }
        return false;
    }

    /**
     * Method used for checking if user is logged or not
     * @param session session associated with the request
     * @return true if there is a user currently logged in within this session.
     */
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(USER) != null;
    }

}
