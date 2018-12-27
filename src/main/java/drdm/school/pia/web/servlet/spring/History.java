package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.modules.Transaction;
import drdm.school.pia.manager.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/banking/history")
public class History extends AbstractServlet {

    private static ArrayList<Transaction> transactions;

    private PaymentManager paymentManager;

    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
            transactions = paymentManager.findTransactionsForUsername(req.getSession().getAttribute("user").toString());
            req.setAttribute("transactions", transactions);
            req.getRequestDispatcher("/WEB-INF/pages/banking/history.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
