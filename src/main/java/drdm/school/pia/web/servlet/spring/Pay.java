package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.manager.UserManager;
import drdm.school.pia.utils.StringValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/banking/pay")
public class Pay extends AbstractServlet {

    private static final String SELECTEDTEMPLATE_PARAMETER = "selecttemplate";
    private static final String SENDTO_PARAMETER = "sendto";
    private static final String BANKCODE_PARAMETER = "bankcode";
    private static final String VS_PARAMETER = "vs";
    private static final String CS_PARAMETER = "cs";
    private static final String SS_PARAMETER = "ss";
    private static final String RECIPIENTMESSAGE_PARAMETER = "msgrec";
    private static final String MYMESSAGE_PARAMETER = "msgme";
    private static final String AMOUNT_PARAMETER = "amount";
    private static final String CURRENCY_PARAMETER = "CZK";
    private static final String TRANSACTIONDATE_PARAMETER = "transactiondate";
    private static final String ISTEMPLATE_PARAMETER = "istemplate";
    private static final String TEMPLATE_PARAMETER = "template";

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";

    private UserManager userManager;

    private final static Logger logger = Logger.getLogger(Login.class);
    private final StringValidator stringValidator = new StringValidator();

    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
            req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedTemplate = req.getParameter(SELECTEDTEMPLATE_PARAMETER);
        String sendTo = req.getParameter(SENDTO_PARAMETER);
        String bankCode = req.getParameter(BANKCODE_PARAMETER);
        String vs = req.getParameter(VS_PARAMETER);
        String cs = req.getParameter(CS_PARAMETER);
        String ss = req.getParameter(SS_PARAMETER);
        String recipientMessage = req.getParameter(RECIPIENTMESSAGE_PARAMETER);
        String myMessage = req.getParameter(MYMESSAGE_PARAMETER);
        String amount = req.getParameter(AMOUNT_PARAMETER);
        String currency = CURRENCY_PARAMETER;
        String transactionDate = req.getParameter(TRANSACTIONDATE_PARAMETER);
        String isTemplate = req.getParameter(ISTEMPLATE_PARAMETER);
        String template = req.getParameter(TEMPLATE_PARAMETER);



        if (req.getParameter("loadtemplate") != null) {
            //TODO fill the fields with selectedTemplate data..

            if (null != selectedTemplate) {
                logger.debug("Selected template: [" + selectedTemplate + "]");
            }
        }

        else if (req.getParameter("pay") != null) {

            logger.debug("Sending payment with these parameters: [" + sendTo + "/"
                    + bankCode + ", "
                    + vs + ", "
                    + cs + ", "
                    + ss + ", "
                    + recipientMessage + ", "
                    + myMessage + ", "
                    + amount + " "
                    + currency + ","
                    + transactionDate + "]");

            if (null != isTemplate) {
                logger.debug("Saving as a template: [" + template + "]");
            }

            if (null != isTemplate && null == template) {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, transactionDate, isTemplate, template, "Please fill the template name you want to save or uncheck 'Save as a template'!", req, resp);
                return;
            }

            //TODO add checking of Amount (shouldn't we read it as an integer?)

            try {
                logger.info("New payment created!. (but not really - to be deleted and replaced by PaymentManager call");
                succsessDispatch("Payment was successfully created!", req, resp);
            } catch(Exception e) {
                //TODO when payment is not successful.
            }
        }

    }

    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

    private void errorDispatch(String selectedTemplate, String sendTo, String bankCode, String vs, String cs, String ss, String recipientMessage, String myMessage, String amount, String transactionDate, String isTemplate, String template, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SELECTEDTEMPLATE_PARAMETER, selectedTemplate);
        req.setAttribute(SENDTO_PARAMETER, sendTo);
        req.setAttribute(BANKCODE_PARAMETER, bankCode);
        req.setAttribute(VS_PARAMETER, vs);
        req.setAttribute(CS_PARAMETER, cs);
        req.setAttribute(SS_PARAMETER, ss);
        req.setAttribute(RECIPIENTMESSAGE_PARAMETER, recipientMessage);
        req.setAttribute(MYMESSAGE_PARAMETER, myMessage);
        req.setAttribute(AMOUNT_PARAMETER, amount);
        req.setAttribute(TRANSACTIONDATE_PARAMETER, transactionDate);
        req.setAttribute(ISTEMPLATE_PARAMETER, isTemplate);
        req.setAttribute(TEMPLATE_PARAMETER, template);
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }


}
