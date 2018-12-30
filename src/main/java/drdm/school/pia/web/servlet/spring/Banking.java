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

@WebServlet({"/banking"})
public class Banking extends AbstractServlet {

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";
    private static final String FIRSTNAME_ATTRIBUTE = "firstname";
    private static final String LASTNAME_ATTRIBUTE = "lastname";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String ADDRESS_ATTRIBUTE = "address";
    private static final String CITY_ATTRIBUTE = "city";
    private static final String ZIP_ATTRIBUTE = "zip";
    private static final String CAPTCHA_USER_ATTRIBUTE = "captchaUser";
    private static final String OLDPWD_ATTRIBUTE = "oldPwd";
    private static final String NEWPWD_ATTRIBUTE = "newPwd";
    private static final String CONFIRMPWD_ATTRIBUTE = "confirmPwd";
    private static final String CAPTCHA_PWD_ATTRIBUTE = "captchaPwd";

    private static List<Transaction> transactions;


    private PaymentManager paymentManager;
    private AccountManager accountManager;
    private UserManager userManager;
    private Account account;
    private User user;
    private Validator stringValidator;

    @Value("${default.currency}")
    private String currency;
    @Value("${captcha.banking.updatePsw}")
    private String captchaPwdValue;
    @Value("${captcha.banking.updateUser}")
    private String captchaUserValue;
    @Value("${regex.email}")
    private String emailRegex;

    private final static Logger logger = Logger.getLogger(Banking.class);

    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    @Autowired
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
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

            if(!Objects.equals(newPwd, confirmPwd)) {
                errorDispatch("The PIN and confirm PIN fields do not match!", req, resp);
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

            if(captchaUser.isEmpty()) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Captcha answer is incorrect!", req, resp);
                return;
            }

            if(!stringValidator.isValid(email, emailRegex)) {
                errorDispatchUser(firstname, lastname, email, gender, address, city, zip,"Email is in invalid format!", req, resp);
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

    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        setDefaultUserAttributes(req, user);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        setDefaultUserAttributes(req, user);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    private void errorDispatchUser(String firstname, String lastname, String email, String gender, String address, String city, String zip, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        setDefaultUserAttributes(req, user);
        setDefaultAccountAttributes(req, account);
        setDefaultTransactionsAttributes(req, transactions);
        req.getRequestDispatcher("/WEB-INF/pages/banking.jsp").forward(req, resp);
    }

    private void setDefaultUserAttributes(HttpServletRequest req, User user) throws ServletException, IOException {
        req.setAttribute(FIRSTNAME_ATTRIBUTE, user.getFirstname());
        req.setAttribute(LASTNAME_ATTRIBUTE, user.getLastname());
        req.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        req.setAttribute(GENDER_ATTRIBUTE, user.getGender());
        req.setAttribute(ADDRESS_ATTRIBUTE, user.getAddress());
        req.setAttribute(CITY_ATTRIBUTE, user.getCity());
        req.setAttribute(ZIP_ATTRIBUTE, user.getZip());
    }

    private void setDefaultAccountAttributes(HttpServletRequest req, Account account) throws ServletException, IOException {
        req.setAttribute("accountNumber", account.getNumber() + "/" + account.getBank());
        req.setAttribute("balance", account.getBalance() + " " + currency);
    }

    private void setDefaultTransactionsAttributes(HttpServletRequest req, List<Transaction> transactions) throws ServletException, IOException {
        req.setAttribute("transactions", transactions);
    }


}
