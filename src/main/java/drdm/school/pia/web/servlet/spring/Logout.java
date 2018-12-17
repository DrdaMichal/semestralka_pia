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

@WebServlet("/logout")
public class Logout extends AbstractServlet {
    private UserManager userManager;
    @Value("#{'${user.roles}'.split(',')}")
    private List<String> permittedRoles;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && permittedRoles.contains(req.getSession().getAttribute("role"))) {
            System.out.println("User[" + req.getSession().getAttribute("user") + "], role[" + req.getSession().getAttribute("role") + "], session[" + req.getSession().getId() + "] successfully logged out.");
            req.getSession().invalidate();
            req.getRequestDispatcher("/WEB-INF/pages/logout.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }

    }

}
