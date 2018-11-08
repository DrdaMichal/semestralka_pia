package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managing/manage_user")
public class ManageUser extends AbstractServlet {
    private static final String USERNAME_PARAMETER = "username";
    private static final String ACTION_PARAMETER = "action";

    private static final String ERR_ATTRIBUTE = "err";
    private static final String SUC_ATTRIBUTE = "suc";
    private static final String CHOOSE_ACTION_ATTRIBUTTE = "chosenAction";

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("temp");
        req.getRequestDispatcher("/WEB-INF/pages/managing/manage_user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        String username = req.getParameter(USERNAME_PARAMETER);
        String action = req.getParameter(ACTION_PARAMETER);

        if(action.equals("edit")) {
            req.setAttribute(SUC_ATTRIBUTE, "You can edit user <strong>" + username + "</strong> now! (TODO check db if exists)");
            req.setAttribute(CHOOSE_ACTION_ATTRIBUTTE, action);
            req.getRequestDispatcher("/WEB-INF/pages/managing/manage_user.jsp").forward(req, resp);
            //resp.sendRedirect("/");
        }
        else if (action.equals("remove")) {
            req.setAttribute(SUC_ATTRIBUTE, "User <strong>" + username + "</strong> will be removed! (TODO check db if exists)");
            req.setAttribute(CHOOSE_ACTION_ATTRIBUTTE, action);
            req.getRequestDispatcher("/WEB-INF/pages/managing/manage_user.jsp").forward(req, resp);
        }
        else {
            req.setAttribute(ERR_ATTRIBUTE, "Unknown error!");
            req.getRequestDispatcher("/WEB-INF/pages/managing/manage_user.jsp").forward(req, resp);
        }
    }
}
