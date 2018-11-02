package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.utils.generateNumber;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Servlet handling user registration requests.
 *
 * Date: 22.10.18
 *
 * @author Michal Drda
 */
@WebServlet("/register")
public class Register extends AbstractServlet {

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String LASTNAME_PARAMETER = "lastname";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String ZIP_PARAMETER = "zip";
    private static final String BIRTHID_PARAMETER = "birthId";
    private static final String BIRTHDATE_PARAMETER = "birthdate";
    private static final String GENDER_PARAMETER = "gender";
    //TODO: check tableName and columnName where username is stored
    //private static final String ROLE = "role";

    private static final String ERROR_ATTRIBUTE = "err";

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pattern = "yyyy-MM-dd";
        String firstname = req.getParameter(FIRSTNAME_PARAMETER);
        String lastname = req.getParameter(LASTNAME_PARAMETER);
        String email = req.getParameter(EMAIL_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);
        String address = req.getParameter(ADDRESS_PARAMETER);
        String zip = req.getParameter(ZIP_PARAMETER);
        String birthId = req.getParameter(BIRTHID_PARAMETER);
        String username = String.valueOf(generateNumber.getRandomNumber(8, "user", "username"));
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("MM-dd-yyyy").parse(req.getParameter(BIRTHDATE_PARAMETER));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String gender = req.getParameter(GENDER_PARAMETER);
        //String role = req.getParameter(ROLE);

        if(!Objects.equals(password, confirmPwd)) {
            errorDispatch("The password and confirm password fields do not match!", req, resp);
            return;
        }

        try {
            //userManager.register(new User(firstname, password, role));
            userManager.register(new User(username, firstname, lastname, email, password, address, zip, birthId, birthDate, gender));
            resp.sendRedirect("login");  //not perfect, user should get a message registration was successful!
        } catch (UserValidationException e) {
            errorDispatch(e.getMessage(), req, resp);
        }
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }
}
