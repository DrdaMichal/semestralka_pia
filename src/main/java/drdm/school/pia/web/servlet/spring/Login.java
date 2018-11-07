package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import drdm.school.pia.web.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends AbstractServlet {
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";

    private static final String ERR_ATTRIBUTE = "err";

    private UserManager userManager;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        boolean authenticated = authService.authenticate(req.getSession(), username, password);
        if(authenticated) {
            resp.sendRedirect("main");
        } else {
            req.setAttribute(ERR_ATTRIBUTE, "Invalid credentials!");
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }
}
