package drdm.school.pia.web.servlet.spring;


import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/managing"})
public class Managing extends AbstractServlet {
    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "User role is not authorised to access this page.");
        }
    }

}
