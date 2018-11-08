package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends AbstractServlet {
    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("User[" + req.getSession().getAttribute("user") + "], role[" + req.getSession().getAttribute("role") + "], session[" + req.getSession().getId() + "] successfully logged out.");
        req.getSession().invalidate();
        req.getRequestDispatcher("/WEB-INF/pages/logout.jsp").forward(req, resp);
    }

}
