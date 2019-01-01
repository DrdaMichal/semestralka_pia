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

@WebServlet("/login")
public class Login extends AbstractServlet {
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";

    @Value("${regex.alphanumericEnglish}")
    private String alphanumericEnglishRegex;
    @Value("${regex.default.password}")
    private String passwordRegex;

    final static Logger logger = Logger.getLogger(Login.class);

    private static final String ERR_ATTRIBUTE = "err";

    private UserManager userManager;
    private Validator stringValidator;

    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private AuthenticationService authService;

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        req.setCharacterEncoding("UTF-8");
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

    private void errorDispatch(String username, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(USERNAME_PARAMETER, username);
        req.setAttribute(ERR_ATTRIBUTE, err);
        req.getRequestDispatcher("/").forward(req, resp);
    }
}
