package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.Payment;
import drdm.school.pia.domain.User;
import drdm.school.pia.manager.PaymentManager;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.utils.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private static final String CAPTCHA_PARAMETER = "captcha";

    private static final String ERROR_ATTRIBUTE = "err";
    private static final String SUCCESS_ATTRIBUTE = "suc";

    private UserManager userManager;
    private PaymentManager paymentManager;
    @Value("${captcha.payment.value}")
    private String paymentCaptcha;
    @Value("#{'${bank.codes}'.split(',')}")
    private List<String> bankCodes;

    private final static Logger logger = Logger.getLogger(Pay.class);

    private Validator stringValidator;

    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("role") && req.getSession().getAttribute("role").equals("USER")) {
            req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
            req.setAttribute("bankcodes", bankCodes);
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
        String captcha = req.getParameter(CAPTCHA_PARAMETER);



        if (req.getParameter("loadtemplate") != null) {
            if (null != selectedTemplate) {
                Payment loadedTemplatePayment = paymentManager.loadPaymentByTemplate(req.getSession().getAttribute("user").toString(), selectedTemplate);

                req.setAttribute(SELECTEDTEMPLATE_PARAMETER, selectedTemplate);
                req.setAttribute(SENDTO_PARAMETER, loadedTemplatePayment.getSendTo());
                req.setAttribute(BANKCODE_PARAMETER, loadedTemplatePayment.getBankCode());
                req.setAttribute(VS_PARAMETER, loadedTemplatePayment.getVs());
                req.setAttribute(CS_PARAMETER, loadedTemplatePayment.getCs());
                req.setAttribute(SS_PARAMETER, loadedTemplatePayment.getSs());
                req.setAttribute(RECIPIENTMESSAGE_PARAMETER, loadedTemplatePayment.getRecipientMessage());
                req.setAttribute(MYMESSAGE_PARAMETER, loadedTemplatePayment.getMyMessage());
                req.setAttribute(AMOUNT_PARAMETER, loadedTemplatePayment.getAccount());
                req.setAttribute(TRANSACTIONDATE_PARAMETER, loadedTemplatePayment.getTransactionDate());
                req.setAttribute(ISTEMPLATE_PARAMETER, "on");
                req.setAttribute(TEMPLATE_PARAMETER, selectedTemplate);

                logger.debug("Selected template: [" + selectedTemplate + "]");

                try {
                    logger.info("Template loaded: " + selectedTemplate + "...");
                    succsessDispatch("Template loaded: " + selectedTemplate + "...", req, resp);
                } catch(Exception e) {
                    errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, e.getMessage(), req, resp);
                }

            } else {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, "Template name has to be selected!", req, resp);
                return;
            }
        }

        else if (req.getParameter("pay") != null) {

            Date transactionDateDate = null;
            try {
                transactionDateDate = new SimpleDateFormat("yyyy-MM-dd").parse(transactionDate);
            } catch (ParseException e) {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, "Date provided was in invalid format!", req, resp);
                return;
            }

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

            if (!captcha.equals(paymentCaptcha)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, "Please answer the captcha question 'One + one ='!", req, resp);
                return;
            }

            if (null != isTemplate) {
                logger.debug("Saving as a template: [" + template + "]");
            }

            if (null != isTemplate && null == template) {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, "Please fill the template name you want to save or uncheck 'Save as a template'!", req, resp);
                return;
            }

            // When user uncheck isTemplate, set template to null, so it's not saved
            if (null == isTemplate) {
                template = null;
            }

            //TODO add checking of Amount (shouldn't we read it as an integer?)

            try {
                logger.info("New payment created!");
                paymentManager.createPayment(new Payment(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, amount, currency, transactionDateDate, template), req.getSession().getAttribute("user").toString());
                succsessDispatch("Payment was successfully created!", req, resp);
            } catch(Exception e) {
                errorDispatch(selectedTemplate, sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, isTemplate, template, e.getMessage(), req, resp);
            }
        }

    }

    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
        req.setAttribute("bankcodes", bankCodes);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

    private void errorDispatch(String selectedTemplate, String sendTo, String bankCode, String vs, String cs, String ss, String recipientMessage, String myMessage, String transactionDate, String amount, String isTemplate, String template, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
        req.setAttribute("bankcodes", bankCodes);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }


}
