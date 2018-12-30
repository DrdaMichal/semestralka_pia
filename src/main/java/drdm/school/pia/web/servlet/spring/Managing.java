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

    private static final String USERNAME_PARAMETER = "username";

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";
    private static final String REMOVE_ACTION_PARAMETER = "removeAction";
    private static final String UPDATE_ACTION_PARAMETER = "updateAction";
    private static final String ACTIVATE_ACTION_PARAMETER = "activateAction";
    private static final String REMOVE_USER_PARAMETER = "removeUser";
    private static final String UPDATE_USER_PARAMETER = "updateUser";
    private static final String ACTIVATE_USER_PARAMETER = "activateUser";

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("ADMIN")) {
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
        String username = req.getParameter(USERNAME_PARAMETER);
        String updateAction = req.getParameter(UPDATE_ACTION_PARAMETER);
        String removeAction = req.getParameter(REMOVE_ACTION_PARAMETER);
        String activateAction = req.getParameter(ACTIVATE_ACTION_PARAMETER);

        if(username.isEmpty()) {
            errorDispatch("Username is a mandatory field!", req, resp);
            return;
        }

        if(updateAction != null) {
            req.setAttribute(UPDATE_ACTION_PARAMETER, updateAction);
            req.setAttribute(USERNAME_PARAMETER, username);
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        }
        else if (removeAction != null) {
            req.setAttribute(REMOVE_ACTION_PARAMETER, removeAction);
            req.setAttribute(USERNAME_PARAMETER, username);
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        } else if (activateAction != null) {
            req.setAttribute(ACTIVATE_ACTION_PARAMETER, activateAction);
            req.setAttribute(USERNAME_PARAMETER, username);
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);

        }
        else {
            req.setAttribute(ERROR_ATTRIBUTE, "Unknown action error!");
            req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
        }
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/managing.jsp").forward(req, resp);
    }

}
