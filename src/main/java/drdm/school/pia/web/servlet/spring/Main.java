package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.SecretManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/main"})
public class Main extends AbstractServlet {

    private SecretManager SecretManager;

    @Autowired
    public void setSecretManager(SecretManager SecretManager) {
        this.SecretManager = SecretManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String url = "/login";
        String url = "/WEB-INF/pages/banking.jsp";
        System.out.println("Role: ROLE_USER[" +  req.isUserInRole("ROLE_USER") + "]");
        System.out.println("Role: ROLE_ADMIN[" +  req.isUserInRole("ROLE_ADMIN") + "]");
        System.out.println("Role: USER[" +  req.isUserInRole("USER") + "]");
        System.out.println("Role: ADMIN[" +  req.isUserInRole("ADMIN") + "]");
        System.out.print("Session: " + req.getSession());

        if (req.isUserInRole("ROLE_USER")) {
            url = "/WEB-INF/pages/banking.jsp";
        }
        if (req.isUserInRole("ROLE_ADMIN")) {
            url = "/WEB-INF/pages/manage.jsp";
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }
}
