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

/**
 * Managing page servlet
 * @author Michal Drda
 */
@WebServlet({"/managing"})
public class Managing extends AbstractServlet {

    /**
     * Username attribute
     */
    private static final String USERNAME_PARAMETER = "username";
    /**
     * Error attribute
     */
    private static final String ERROR_ATTRIBUTE = "err";
    /**
     * Success attribute
     */
    private static final String SUCCESS_ATTRIBUTE = "suc";
    /**
     * remove action button attribute
     */
    private static final String REMOVE_ACTION_PARAMETER = "removeAction";
    /**
     * update action button attribute
     */
    private static final String UPDATE_ACTION_PARAMETER = "updateAction";
    /**
     * remove user button attribute
     */
    private static final String REMOVE_USER_PARAMETER = "removeUser";
    /**
     * update user button attribute
     */
    private static final String UPDATE_USER_PARAMETER = "updateUser";
    /**
     * First name attribute
     */
    private static final String FIRSTNAME_ATTRIBUTE = "firstname";
    /**
     * Last name attribute
     */
    private static final String LASTNAME_ATTRIBUTE = "lastname";
    /**
     * E-mail attribute
     */
    private static final String EMAIL_ATTRIBUTE = "email";
    /**
     * Gender attribute
     */
    private static final String GENDER_ATTRIBUTE = "gender";
    /**
     * Address attribute
     */
    private static final String ADDRESS_ATTRIBUTE = "address";
    /**
     * City attribute
     */
    private static final String CITY_ATTRIBUTE = "city";
    /**
     * Zip code attribute
     */
    private static final String ZIP_ATTRIBUTE = "zip";
    /**
     * Captcha for user edit
     */
    private static final String CAPTCHA_USER_ATTRIBUTE = "captchaUser";

    /**
     * Properties value of the captcha for user edit
     */
    @Value("${captcha.managing.updateUser}")
    private String captchaUserValue;
    /**
     * Properties value of email regex validation
     */
    @Value("${regex.email}")
    private String emailRegex;
    /**
     * Properties value of the alphabetic words regex validatio
     */
    @Value("${regex.default.multipleAlphabeticWords}")
    private String alphabeticWordsRegex;
    /**
     * Properties value of the english string with numbers validation
     */
    @Value("${regex.alphanumericEnglish}")
    private String alphanumericEnglishRegex;

    /**
     * User manager initialization
     */
    private UserManager userManager;
    /**
     * User instance to be loaded initialization
     */
    private User user;
    /**
     * String validator initialization
     */
    private Validator stringValidator;

    /**
     * Logger used for logging of useful information
     */
    private final static Logger logger = Logger.getLogger(Managing.class);

    /**
     * Setter for a user manager
     * @param userManager provided user manager
     */
    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Setter for a string validator
     * @param stringValidator provided string validator
     */
    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    /**
     * @inheritDoc
     * Get method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    /**
     * @inheritDoc
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
        String updateAction = req.getParameter(UPDATE_ACTION_PARAMETER);
        String removeAction = req.getParameter(REMOVE_ACTION_PARAMETER);
        String updateUser = req.getParameter(UPDATE_USER_PARAMETER);
        String removeUser = req.getParameter(REMOVE_USER_PARAMETER);


        if (updateAction != null || removeAction != null) {
            if (username.isEmpty()) {
                errorDispatch(username, "Username is mandatory!", req, resp);
                return;
            } else if(!stringValidator.isValid(username, alphanumericEnglishRegex)) {
                req.setAttribute(USERNAME_PARAMETER, username);
                errorDispatch(username,"Username in invalid format!", req, resp);
                return;
            } else {
                if (null == user || !username.equals(user.getUsername())) {
                    user = userManager.findUserByUsername(username);
                }
                if (null == user) {
                    errorDispatch(username, ("User does not exist!"), req, resp);
                    return;
                }
            }
        }

        if(updateAction != null) {
            req.setAttribute(UPDATE_ACTION_PARAMETER, updateAction);
            req.setAttribute(USERNAME_PARAMETER, username);
            setDefaultUserAttributes(req, user);
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);

        }
        else if (removeAction != null) {
            req.setAttribute(REMOVE_ACTION_PARAMETER, removeAction);
            req.setAttribute(USERNAME_PARAMETER, username);
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        }
        else if (updateUser != null) {
            if (null == username) {
                username = user.getUsername();
            }
            String firstname = req.getParameter(FIRSTNAME_ATTRIBUTE);
            String lastname = req.getParameter(LASTNAME_ATTRIBUTE);
            String email = req.getParameter(EMAIL_ATTRIBUTE);
            String gender = req.getParameter(GENDER_ATTRIBUTE);
            String address = req.getParameter(ADDRESS_ATTRIBUTE);
            String city = req.getParameter(CITY_ATTRIBUTE);
            String zip = req.getParameter(ZIP_ATTRIBUTE);
            String captchaUser = req.getParameter(CAPTCHA_USER_ATTRIBUTE);

            if(firstname.isEmpty()) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"First name is mandatory!", req, resp);
                return;
            }

            if(lastname.isEmpty()) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"Last name is mandatory!", req, resp);
                return;
            }

            if(email.isEmpty()) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"E-mail is mandatory!", req, resp);
                return;
            }

            if(gender.isEmpty()) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"Gender is mandatory!", req, resp);
                return;
            }

            // Validate firstname and lastname
            if(!firstname.isEmpty() && !stringValidator.isValid(firstname, alphabeticWordsRegex)) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"First name is in invalid format, only letters are supported!", req, resp);
                return;
            }

            if(!lastname.isEmpty() && !stringValidator.isValid(lastname, alphabeticWordsRegex)) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip, "Last name is in invalid format, only letters are supported!", req, resp);
                return;
            }

            if(!stringValidator.isValid(email, emailRegex)) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"Email is in invalid format!", req, resp);
                return;
            }

            if(captchaUser.isEmpty()) {
                errorDispatchUpdateUser(username, firstname, lastname, email, gender, address, city, zip,"Captcha answer is incorrect!", req, resp);
                return;
            }

            try {
                userManager.updateUserInfo(firstname, lastname, email, gender, address, city,zip, user);
                succsessDispatch(("User information successfully updated!"), req, resp);
            } catch (UserValidationException e) {
                errorDispatch(username, ("User information could  not be updated!"), req, resp);
            }
        }
        else if (removeUser != null) {
            try {
                userManager.removeUser(user.getUsername());
                setDefaultUserAttributes(req, null);
                succsessDispatch(("User <strong>" + user.getUsername() + "</strong was successfully deleted along with it's account and card!"), req, resp);
            } catch (Exception e) {
                errorDispatch(user.getUsername(), ("User could not be deleted!"), req, resp);
            }
        }
        else {
            req.setAttribute(ERROR_ATTRIBUTE, "Unknown action error!");
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
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
        if (!username.isEmpty()) {
            req.setAttribute(USERNAME_PARAMETER, username);
        }
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }


    /**
     * Dispatcher method used for error dispatch
     * @param username provided user name
     * @param firstname provided first name
     * @param lastname provided last name
     * @param email provided e-mail
     * @param gender provided gender
     * @param address provided address
     * @param city provided city
     * @param zip provided zip code
     * @param err error message provided
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of error thrown
     * @throws IOException in case of error thrown
     */
    private void errorDispatchUpdateUser(String username, String firstname, String lastname, String email, String gender, String address, String city, String zip, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.setAttribute(USERNAME_PARAMETER, username);
        req.setAttribute(FIRSTNAME_ATTRIBUTE, firstname);
        req.setAttribute(LASTNAME_ATTRIBUTE, lastname);
        req.setAttribute(EMAIL_ATTRIBUTE, email);
        req.setAttribute(GENDER_ATTRIBUTE, gender);
        req.setAttribute(ADDRESS_ATTRIBUTE, address);
        req.setAttribute(CITY_ATTRIBUTE, city);
        req.setAttribute(ZIP_ATTRIBUTE, zip);
        user = null;
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }


    /**
     * Used for default User attributes loading
     * @param req provided request
     * @param user provided user instance
     */
    private void setDefaultUserAttributes(HttpServletRequest req, User user) throws ServletException, IOException {
        if (null == user) {
            req.setAttribute(USERNAME_PARAMETER, "");
            req.setAttribute(FIRSTNAME_ATTRIBUTE, "");
            req.setAttribute(LASTNAME_ATTRIBUTE, "");
            req.setAttribute(EMAIL_ATTRIBUTE, "");
            req.setAttribute(GENDER_ATTRIBUTE, "");
            req.setAttribute(ADDRESS_ATTRIBUTE, "");
            req.setAttribute(CITY_ATTRIBUTE, "");
            req.setAttribute(ZIP_ATTRIBUTE, "");
        }
        req.setAttribute(USERNAME_PARAMETER, user.getUsername());
        req.setAttribute(FIRSTNAME_ATTRIBUTE, user.getFirstname());
        req.setAttribute(LASTNAME_ATTRIBUTE, user.getLastname());
        req.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        req.setAttribute(GENDER_ATTRIBUTE, user.getGender());
        req.setAttribute(ADDRESS_ATTRIBUTE, user.getAddress());
        req.setAttribute(CITY_ATTRIBUTE, user.getCity());
        req.setAttribute(ZIP_ATTRIBUTE, user.getZip());
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
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }

}
