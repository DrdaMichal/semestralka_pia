package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * History of transactions page Servlet
 * @author Michal Drda
 */
@WebServlet("/banking/history")
public class History extends AbstractServlet {

    /**
     * Payment manager initialization
     */
    private PaymentManager paymentManager;

    /**
     * Setter for a payment manager
     * @param paymentManager provided payment manager
     */
    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    /**
     * @inheritDoc
     * Get method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
            req.setAttribute("paging", true);
            req.setAttribute("transactions", paymentManager.findTransactionsForUsername(req.getSession().getAttribute("user").toString()));
            req.getRequestDispatcher("/WEB-INF/pages/banking/history.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

}
