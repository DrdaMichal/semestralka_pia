package drdm.school.pia.web.servlet.spring;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/about")
public class About extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null == req.getSession().getAttribute("role")) {
            req.getRequestDispatcher("/WEB-INF/pages/about.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "Only non-authenticated guests can visit this page.");
        }

    }
}
