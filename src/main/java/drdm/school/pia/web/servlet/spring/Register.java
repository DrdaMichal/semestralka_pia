package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.manager.RoleManager;
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
 *
 * Date: 22.10.18
 *
 * @author Michal Drda
 */
@WebServlet("/managing/register")
public class Register extends AbstractServlet {

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String LASTNAME_PARAMETER = "lastname";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";
    private static final String ROLE_PARAMETER = "role";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String CITY_PARAMETER = "city";
    private static final String ZIP_PARAMETER = "zip";
    private static final String BIRTHID_PARAMETER = "birthId";
    private static final String GENDER_PARAMETER = "gender";
    private static final String CAPTCHA_PARAMETER = "captcha";
    private static final String TERMS_PARAMETER = "terms";

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";

    private UserManager userManager;
    private RoleManager roleManager;
    @Value("${captcha.value}")
    private String captchaValue;
    @Value("${regex.email}")
    private String emailRegex;

    private final static Logger logger = Logger.getLogger(Login.class);
    private Validator stringValidator;

    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
        //} else {
            // User is not authorised to do the action.
        //    resp.sendError(401, "You are not authorised to access this page.");
        //}
    }

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

        if(!captcha.equals(captchaValue)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Captcha answer is incorrect!", req, resp);
            return;
        }

        if(null == terms) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Please accept terms of use!", req, resp);
            return;
        }

        if(!stringValidator.isValid(email, emailRegex)) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, "Email is in invalid format!", req, resp);
            return;
        }

        try {
            userManager.register(new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender));
            succsessDispatch("User successfully registered!", req, resp);
        } catch (UserValidationException e) {
            errorDispatch(firstname, lastname, email, role, address, city, zip, birthid, gender, terms, e.getMessage(), req, resp);
        }
    }

    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
    }

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

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/managing/register.jsp").forward(req, resp);
    }
}
