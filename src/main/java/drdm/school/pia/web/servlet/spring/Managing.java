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

@WebServlet({"/managing"})
public class Managing extends AbstractServlet {

    private static final String USERNAME_PARAMETER = "username";

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";

    private static final String REMOVE_ACTION_PARAMETER = "removeAction";
    private static final String UPDATE_ACTION_PARAMETER = "updateAction";
    private static final String REMOVE_USER_PARAMETER = "removeUser";
    private static final String UPDATE_USER_PARAMETER = "updateUser";

    private static final String FIRSTNAME_ATTRIBUTE = "firstname";
    private static final String LASTNAME_ATTRIBUTE = "lastname";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String ADDRESS_ATTRIBUTE = "address";
    private static final String CITY_ATTRIBUTE = "city";
    private static final String ZIP_ATTRIBUTE = "zip";
    private static final String CAPTCHA_USER_ATTRIBUTE = "captchaUser";



    @Value("${captcha.managing.updateUser}")
    private String captchaUserValue;
    @Value("${regex.email}")
    private String emailRegex;
    @Value("${regex.default.multipleAlphabeticWords}")
    private String alphabeticWordsRegex;
    @Value("${regex.alphanumericEnglish}")
    private String alphanumericEnglishRegex;

    private UserManager userManager;
    private User user;
    private Validator stringValidator;


    private final static Logger logger = Logger.getLogger(Banking.class);

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        req.setCharacterEncoding("UTF-8");
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
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"First name is mandatory!", req, resp);
                return;
            }

            if(lastname.isEmpty()) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"Last name is mandatory!", req, resp);
                return;
            }

            if(email.isEmpty()) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"E-mail is mandatory!", req, resp);
                return;
            }

            if(gender.isEmpty()) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"Gender is mandatory!", req, resp);
                return;
            }

            // Validate firstname and lastname
            if(!firstname.isEmpty() && !stringValidator.isValid(firstname, alphabeticWordsRegex)) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"First name is in invalid format, only letters are supported!", req, resp);
                return;
            }

            if(!lastname.isEmpty() && !stringValidator.isValid(lastname, alphabeticWordsRegex)) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip, "Last name is in invalid format, only letters are supported!", req, resp);
                return;
            }

            if(!stringValidator.isValid(email, emailRegex)) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"Email is in invalid format!", req, resp);
                return;
            }

            if(captchaUser.isEmpty()) {
                errorDispatchUpdateUser(firstname, lastname, email, gender, address, city, zip,"Captcha answer is incorrect!", req, resp);
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

    private void errorDispatch(String username, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!username.isEmpty()) {
            req.setAttribute(USERNAME_PARAMETER, username);
        }
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }

    private void errorDispatchUpdateUser(String firstname, String lastname, String email, String gender, String address, String city, String zip, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        setDefaultUserAttributes(req, user);
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }

    private void setDefaultUserAttributes(HttpServletRequest req, User user) throws ServletException, IOException {
        req.setAttribute(USERNAME_PARAMETER, user.getUsername());
        req.setAttribute(FIRSTNAME_ATTRIBUTE, user.getFirstname());
        req.setAttribute(LASTNAME_ATTRIBUTE, user.getLastname());
        req.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        req.setAttribute(GENDER_ATTRIBUTE, user.getGender());
        req.setAttribute(ADDRESS_ATTRIBUTE, user.getAddress());
        req.setAttribute(CITY_ATTRIBUTE, user.getCity());
        req.setAttribute(ZIP_ATTRIBUTE, user.getZip());
    }

    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }

}
