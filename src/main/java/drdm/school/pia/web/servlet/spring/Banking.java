package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.dto.implementation.Transaction;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.manager.PaymentManager;
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
import java.util.List;
import java.util.Objects;

/**
 * Servlet for banking page
 * @author Michal Drda
 */
@WebServlet({"/banking"})
public class Banking extends AbstractServlet {

    /**
     * Error attribute
     */
    private static final String ERROR_ATTRIBUTE = "err";
    /**
     * Success attribute
     */
    private static final String SUCCESS_ATTRIBUTE = "suc";
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
     * Captcha user managing attribute
     */
    private static final String CAPTCHA_USER_ATTRIBUTE = "captchaUser";
    /**
     * Old password attribute
     */
    private static final String OLDPWD_ATTRIBUTE = "oldPwd";
    /**
     * New password attribute
     */
    private static final String NEWPWD_ATTRIBUTE = "newPwd";
    /**
     * Confirm password attribute
     */
    private static final String CONFIRMPWD_ATTRIBUTE = "confirmPwd";
    /**
     * Captcha password change attribute
     */
    private static final String CAPTCHA_PWD_ATTRIBUTE = "captchaPwd";
    /**
     * Transactions list initialization
     */
    private static List<Transaction> transactions;


    /**
     * Initialization of the payment manager
     */
    private PaymentManager paymentManager;
    /**
     * Initialization of the account manager
     */
    private AccountManager accountManager;
    /**
     * Initialization of the user manager
     */
    private UserManager userManager;
    /**
     * Initialization of the account
     */
    private Account account;
    /**
     * Initialization of the user
     */
    private User user;
    /**
     * Initialization of the string validator
     */
    private Validator stringValidator;

    /**
     * Properties value of currency
     */
    @Value("${default.currency}")
    private String currency;
    /**
     * Properties value of captcha for password change
     */
    @Value("${captcha.banking.updatePsw}")
    private String captchaPwdValue;
    /**
     * Properties value of captcha for user change
     */
    @Value("${captcha.banking.updateUser}")
    private String captchaUserValue;
    /**
     * Properties value of pattern for email validation
     */
    @Value("${regex.email}")
    private String emailRegex;
    /**
     * Properties value of alphabetic words validation
     */
    @Value("${regex.default.multipleAlphabeticWords}")
    private String alphabeticWordsRegex;
    /**
     * Properties value for password validation
     */
    @Value("${regex.default.password}")
    private String passwordRegex;

    /**
     * Logger instance initialization
     */
    private final static Logger logger = Logger.getLogger(Banking.class);

    /**
     * Setter for a String validator
     * @param stringValidator provided string validator
     */
    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    /**
     * Setter for a payment manager
     * @param paymentManager provided payment manager
     */
    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    /**
     * Setter for a account manager
     * @param accountManager provided account manager
     */
    @Autowired
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            transactions = paymentManager.findTransactionsForUsername(req.getSession().getAttribute("user").toString());
            if (transactions != null && transactions.size() > 10) {
                transactions = transactions.subList(0,10);
            } else {
                // Transaction list is less than 10, no changes needed..
            }
            account = accountManager.findAccountByUsername(req.getSession().getAttribute("user").toString());
            user = userManager.findUserByUsername(req.getSession().getAttribute("user").toString());

            setDefaultUserAttributes(req, user);
            setDefaultAccountAttributes(req, account);
            setDefaultTransactionsAttributes(req, transactions);
            req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
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
        String username = req.getSession().getAttribute("user").toString();

        if (req.getParameter("updatePsw") != null) {
            String oldPwd = req.getParameter(OLDPWD_ATTRIBUTE);
            String newPwd = req.getParameter(NEWPWD_ATTRIBUTE);
            String confirmPwd = req.getParameter(CONFIRMPWD_ATTRIBUTE);
            String captchaPwd = req.getParameter(CAPTCHA_PWD_ATTRIBUTE);

            if(oldPwd.isEmpty() && newPwd.isEmpty() && confirmPwd.isEmpty()) {
                errorDispatch("PIN fields must be filled!", req, resp);
                return;
            }
            if(oldPwd.isEmpty()) {
                errorDispatch("Old PIN must be filled!", req, resp);
                return;
            }
            if(newPwd.isEmpty()) {
                errorDispatch("New PIN must be filled!", req, resp);
                return;
            }
            if(confirmPwd.isEmpty()) {
                errorDispatch("Confirm PIN must be filled!", req, resp);
                return;
            }

            if(!newPwd.isEmpty() && !stringValidator.isValid(newPwd, passwordRegex)) {
                errorDispatch("New PIN in invalid format!", req, resp);
                return;
            }

            if(!oldPwd.isEmpty() && !stringValidator.isValid(newPwd, passwordRegex)) {
                errorDispatch("Old PIN in invalid format!", req, resp);
                return;
            }

            if(!confirmPwd.isEmpty() && !stringValidator.isValid(newPwd, passwordRegex)) {
                errorDispatch("Confirm PIN in invalid format!", req, resp);
                return;
            }

            if(!Objects.equals(newPwd, confirmPwd)) {
                errorDispatch("The PIN and confirm PIN fields does not match!", req, resp);
                return;
            }

            if(!captchaPwd.equals(captchaPwdValue)) {
                errorDispatch("Captcha answer is incorrect!", req, resp);
                return;
            }

            try {
                userManager.updatePassword(oldPwd, newPwd, username);
                succsessDispatch("PIN successfully changed!", req, resp);
            } catch (UserValidationException e) {
                errorDispatch("PIN could not be changed! PINs does not match!", req, resp);
            }

        }

        if (req.getParameter("updateUser") != null) {
            String firstname = req.getParameter(FIRSTNAME_ATTRIBUTE);
            String lastname = req.getParameter(LASTNAME_ATTRIBUTE);
            String email = req.getParameter(EMAIL_ATTRIBUTE);
            String gender = req.getParameter(GENDER_ATTRIBUTE);
            String address = req.getParameter(ADDRESS_ATTRIBUTE);
            String city = req.getParameter(CITY_ATTRIBUTE);
            String zip = req.getParameter(ZIP_ATTRIBUTE);
            String captchaUser = req.getParameter(CAPTCHA_USER_ATTRIBUTE);

            if(firstname.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"First name is mandatory!", req, resp);
                return;
            }

            if(lastname.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Last name is mandatory!", req, resp);
                return;
            }

            if(email.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"E-mail is mandatory!", req, resp);
                return;
            }

            if(gender.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Gender is mandatory!", req, resp);
                return;
            }

            if(!firstname.isEmpty() && !stringValidator.isValid(firstname, alphabeticWordsRegex)) {
                errorDispatch("First name in invalid format!", req, resp);
                return;
            }

            if(!lastname.isEmpty() && !stringValidator.isValid(lastname, alphabeticWordsRegex)) {
                errorDispatch("Last name in invalid format!", req, resp);
                return;
            }

            if(!email.isEmpty() && !stringValidator.isValid(email, emailRegex)) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Email is in invalid format!", req, resp);
                return;
            }

            if(captchaUser.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Captcha answer is incorrect!", req, resp);
                return;
            }

            try {
                userManager.updateUserInfo(firstname, lastname, email, gender, address, city,zip, user);
                succsessDispatch("Usef information successfully updated!", req, resp);
            } catch (UserValidationException e) {
                errorDispatch("User information could not be updated!", req, resp);
            }

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
        setDefaultUserAttributes(req, user);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    /**
     * Dispatcher method used for error dispatch
     * @param err error message provided
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of error thrown
     * @throws IOException in case of error thrown
     */
    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        setDefaultUserAttributes(req, user);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    /**
     * Dispatcher method used for error dispatch
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
    private void errorDispatchUser(String firstname, String lastname, String email, String gender, String address, String city, String zip, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.setAttribute(FIRSTNAME_ATTRIBUTE, firstname);
        req.setAttribute(LASTNAME_ATTRIBUTE, lastname);
        req.setAttribute(EMAIL_ATTRIBUTE, email);
        req.setAttribute(GENDER_ATTRIBUTE, gender);
        req.setAttribute(ADDRESS_ATTRIBUTE, address);
        req.setAttribute(CITY_ATTRIBUTE, city);
        req.setAttribute(ZIP_ATTRIBUTE, zip);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    /**
     * Used for default User attributes loading
     * @param req provided request
     * @param user provided user instance
     */
    private void setDefaultUserAttributes(HttpServletRequest req, User user) {
        req.setAttribute(FIRSTNAME_ATTRIBUTE, user.getFirstname());
        req.setAttribute(LASTNAME_ATTRIBUTE, user.getLastname());
        req.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        req.setAttribute(GENDER_ATTRIBUTE, user.getGender());
        req.setAttribute(ADDRESS_ATTRIBUTE, user.getAddress());
        req.setAttribute(CITY_ATTRIBUTE, user.getCity());
        req.setAttribute(ZIP_ATTRIBUTE, user.getZip());
    }

    /**
     * Used for default Account attributes loading
     * @param req provided request
     * @param account provided account instance
     */
    private void setDefaultAccountAttributes(HttpServletRequest req, Account account) {
        req.setAttribute("accountNumber", account.getNumber() + "/" + account.getBank());
        req.setAttribute("balance", String.format("%.3f",account.getBalance()) + " " + currency);
    }

    /**
     * Used for default Transaction attributes loading
     * @param req provided request
     * @param transactions provided transactions List instance
     */
    private void setDefaultTransactionsAttributes(HttpServletRequest req, List<Transaction> transactions) {
        req.setAttribute("transactions", transactions);
    }


}
