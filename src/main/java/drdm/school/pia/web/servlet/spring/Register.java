package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.utils.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Servlet handling user registration requests.
 * @author Michal Drda
 */
@WebServlet("/managing/register")
public class Register extends AbstractServlet {

    /**
     * First name attriute
     */
    private static final String FIRSTNAME_PARAMETER = "firstname";
    /**
     * Last name attribute
     */
    private static final String LASTNAME_PARAMETER = "lastname";
    /**
     * E-mail attribute
     */
    private static final String EMAIL_PARAMETER = "email";
    /**
     * Password attribute
     */
    private static final String PASSWORD_PARAMETER = "password";
    /**
     * Password confirm attribute
     */
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";
    /**
     * Role attribute
     */
    private static final String ROLE_PARAMETER = "role";
    /**
     * Address attribute
     */
    private static final String ADDRESS_PARAMETER = "address";
    /**
     * City attribute
     */
    private static final String CITY_PARAMETER = "city";
    /**
     * Zip code attribute
     */
    private static final String ZIP_PARAMETER = "zip";
    /**
     * Birth id attribute
     */
    private static final String BIRTHID_PARAMETER = "birthId";
    /**
     * Gender attribute
     */
    private static final String GENDER_PARAMETER = "gender";
    /**
     * Captcha attribute
     */
    private static final String CAPTCHA_PARAMETER = "captcha";
    /**
     * Terms of use accept attribute
     */
    private static final String TERMS_PARAMETER = "terms";
    /**
     * Error message attribute
     */
    private static final String ERROR_ATTRIBUTE = "err";
    /**
     * Success message attribute
     */
    private static final String SUCCESS_ATTRIBUTE = "suc";

    /**
     * Validator initialization
     */
    private Validator stringValidator;
    /**
     * User manager initialization
     */
    private UserManager userManager;
    /**
     * Properties value of the captcha
     */
    @Value("${captcha.register.value}")
    private String captchaValue;
    /**
     * Properties value of the email regex validation pattern
     */
    @Value("${regex.email}")
    private String emailRegex;
    /**
     * Properties value of the alphabetic words validation regex pattern
     */
    @Value("${regex.default.multipleAlphabeticWords}")
    private String alphabeticWordsRegex;
    /**
     * Properties value of the password regex validation pattern
     */
    @Value("${regex.default.password}")
    private String passwordRegex;

    /**
     * Logger for logging useful information
     */
    private final static Logger logger = Logger.getLogger(Register.class);

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
     * {@inheritDoc}
     * Get method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstname = req.getParameter(FIRSTNAME_PARAMETER);
        String lastname = req.getParameter(LASTNAME_PARAMETER);
        String email = req.getParameter(EMAIL_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);
        String role = req.getParameter(ROLE_PARAMETER);
        String address = req.getParameter(ADDRESS_PARAMETER);
        String city = req.getParameter(CITY_PARAMETER);
        String zip = req.getParameter(ZIP_PARAMETER);
        String birthid = req.getParameter(BIRTHID_PARAMETER);
        String gender = req.getParameter(GENDER_PARAMETER);
        String captcha = req.getParameter(CAPTCHA_PARAMETER);
        String terms = req.getParameter(TERMS_PARAMETER);

        logger.debug("Password [" + password + "]");

        if(!Objects.equals(password, confirmPwd)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "The password and confirm password fields do not match!", req, resp);
            return;
        }

        if(null == terms) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Please accept terms of use!", req, resp);
            return;
        }

        // Validate firstname and lastname
        if(!firstname.isEmpty() && !stringValidator.isValid(firstname, alphabeticWordsRegex)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "First name is in invalid format, only letters are supported!", req, resp);
            return;
        }
        if(!lastname.isEmpty() && !stringValidator.isValid(lastname, alphabeticWordsRegex)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Last name is in invalid format, only letters are supported!", req, resp);
            return;
        }

        // Validate email
        if(!stringValidator.isValid(email, emailRegex)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Email is in invalid format!", req, resp);
            return;
        }

        // Validate password
        if(!stringValidator.isValid(password, passwordRegex)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Password is in invalid format, only alpha-numeric chars and !@#$%^&* are supported!", req, resp);
            return;
        }

        if(!captcha.equals(captchaValue)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Captcha answer is incorrect!", req, resp);
            return;
        }

        try {
            userManager.register(new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender), null);
            succsessDispatch("User " + firstname + " " + lastname + " successfully registered!", req, resp);
        } catch (UserValidationException e) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, e.getMessage(), req, resp);
        }
    }

    /**
     * Dispatcher method used for success dispatch
     * @param suc success message provided
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of error thrown
     * @throws IOException in case of error thrown
     */
    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
    }

    /**
     * Dispatcher method used for error dispatch
     * @param firstname provided first name value
     * @param lastname provided last name value
     * @param email provided e-mail value
     * @param role provided role value
     * @param address provided address value
     * @param city provided city value
     * @param zip provided zip code value
     * @param birthId provided birth id value
     * @param gender provided gender value
     * @param terms provided terms of use checkbox value
     * @param err provided error message
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of the error
     * @throws IOException in case of the error
     */
    private void errorDispatch(String firstname, String lastname, String email, String role, String address, String city, String zip, String birthId, String gender, String terms, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(FIRSTNAME_PARAMETER, firstname);
        req.setAttribute(LASTNAME_PARAMETER, lastname);
        req.setAttribute(EMAIL_PARAMETER, email);
        req.setAttribute(ROLE_PARAMETER, role);
        req.setAttribute(ADDRESS_PARAMETER, address);
        req.setAttribute(CITY_PARAMETER, city);
        req.setAttribute(ZIP_PARAMETER, zip);
        req.setAttribute(BIRTHID_PARAMETER, birthId);
        req.setAttribute(GENDER_PARAMETER, gender);
        req.setAttribute(TERMS_PARAMETER, terms);
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
    }

}
