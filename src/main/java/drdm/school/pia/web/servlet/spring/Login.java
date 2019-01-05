package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import drdm.school.pia.utils.Validator;
import drdm.school.pia.web.auth.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for a login page
 * @author Michal Drda
 */
@WebServlet("/login")
public class Login extends AbstractServlet {

    /**
     * username parameter
     */
    private static final String USERNAME_PARAMETER = "username";
    /**
     * password parameter
     */
    private static final String PASSWORD_PARAMETER = "password";
    /**
     * Error attribute
     */
    private static final String ERR_ATTRIBUTE = "err";
    /**
     * Properties value of regex for alphanumeric english string validation
     */
    @Value("${regex.alphanumericEnglish}")
    private String alphanumericEnglishRegex;
    /**
     * Properties value of regex for password validation
     */
    @Value("${regex.default.password}")
    private String passwordRegex;

    /**
     * Logger used for logging of useful information
     */
    final static Logger logger = Logger.getLogger(Login.class);

    /**
     * User manager initialization
     */
    private UserManager userManager;
    /**
     * String validator initialization
     */
    private Validator stringValidator;
    /**
     * Authentication service initialization
     */
    private AuthenticationService authService;

    /**
     * Setter for a string validator
     * @param stringValidator provided string validator
     */
    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    /**
     * Setter for a user manager
     * @param userManager provided user manager
     */
    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Setter for an auth service
     * @param authService provided authentication service
     */
    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * {@inheritDoc}
     * Get method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    /**
     * {@inheritDoc}
     * Post method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);


        if(!username.isEmpty() && !stringValidator.isValid(username, alphanumericEnglishRegex)) {
            errorDispatch(username, "Username or password in invalid format!", req, resp);
            return;
        }

        if(!password.isEmpty() && !stringValidator.isValid(password, passwordRegex)) {
            errorDispatch(username, "Username or password in invalid format!", req, resp);
            return;
        }

        boolean authenticated = authService.authenticate(req.getSession(), username, password);
        if(authenticated) {

            logger.info("Custom log > User [" + req.getSession().getAttribute("user") + "], Role [" + req.getSession().getAttribute("role") +  "], Session [" + req.getSession().getId() + "] | Authentication successful.");

            String dir = "/login";
            if (null == req.getSession().getAttribute("role") || req.getSession().getAttribute("role").equals("NOTSET")) {
                req.setAttribute(ERR_ATTRIBUTE, "Unknown user role!");
                logger.warn("Custom log > User [" + req.getSession().getAttribute("user") + "], Role [" + req.getSession().getAttribute("role") +  "], Session [" + req.getSession().getId() + "] | Unknown user role!");
            }
            else {
                if (req.getSession().getAttribute("role").equals("USER")) {
                    dir = "/banking";
                }
                else if (req.getSession().getAttribute("role").equals("ADMIN")) {
                    dir = "/managing";
                }
            }

            logger.info("Custom log > User [" + req.getSession().getAttribute("user") + "], Role [" + req.getSession().getAttribute("role") +  "], Session [" + req.getSession().getId() + "] | Redirecting to [" + dir + "] page...");
            resp.sendRedirect(dir);

        } else {
            logger.info("Custom log > User [" + req.getSession().getAttribute("user") + "], Role [" + req.getSession().getAttribute("role") +  "], Session [" + req.getSession().getId() + "] | Login failed!");
            errorDispatch(username, "Invalid credentials!", req, resp);
        }
    }

    /**
     * Dispatcher method used for error dispatch
     * @param username provided username
     * @param err error message provided
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of error thrown
     * @throws IOException in case of error thrown
     */
    private void errorDispatch(String username, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(USERNAME_PARAMETER, username);
        req.setAttribute(ERR_ATTRIBUTE, err);
        req.getRequestDispatcher("/").forward(req, resp);
    }
}
