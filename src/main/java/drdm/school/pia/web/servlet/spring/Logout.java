package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Logout page servlet
 * @author Michal Drda
 */
@WebServlet("/logout")
public class Logout extends AbstractServlet {
    /**
     * User manager initialization
     */
    private UserManager userManager;
    /**
     * Properties value of list of roles permitted
     */
    @Value("#{'${user.roles}'.split(',')}")
    private List<String> permittedRoles;
    /**
     * Interval used for redirect to the main page (login page)
     */
    @Value("${redirect.logout.interval}")
    private String redirectInterval;

    /**
     * Setter of a user manager
     * @param userManager provided user manager
     */
    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
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
        if (null != req.getSession().getAttribute("role") && permittedRoles.contains(req.getSession().getAttribute("role"))) {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            req.setAttribute("redirectInterval", redirectInterval);
            System.out.println("User[" + req.getSession().getAttribute("user") + "], role[" + req.getSession().getAttribute("role") + "], session[" + req.getSession().getId() + "] successfully logged out.");
            req.getSession().invalidate();
            req.getRequestDispatcher("/WEB-INF/pages/logout.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

}
