package drdm.school.pia.web.servlet.spring;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.Payment;
import drdm.school.pia.manager.AccountManager;
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

/**
 * Servlet user for a pay page
 * @author Michal Drda
 */
@WebServlet("/banking/pay")
public class Pay extends AbstractServlet {

    /**
     * Selected template attribute (in case that user want's to load exisitng template)
     */
    private static final String SELECTEDTEMPLATE_PARAMETER = "selecttemplate";
    /**
     * Recipient of the payment attribute
     */
    private static final String SENDTO_PARAMETER = "sendto";
    /**
     * Recipient's bank code attribute
     */
    private static final String BANKCODE_PARAMETER = "bankcode";
    /**
     * Variable symbol attribute
     */
    private static final String VS_PARAMETER = "vs";
    /**
     * Constant symbol attribute
     */
    private static final String CS_PARAMETER = "cs";
    /**
     * Specific symbol attribute
     */
    private static final String SS_PARAMETER = "ss";
    /**
     * Recipient message note attribute
     */
    private static final String RECIPIENTMESSAGE_PARAMETER = "msgrec";
    /**
     * Sender message note attribute
     */
    private static final String MYMESSAGE_PARAMETER = "msgme";
    /**
     * Amount attribute
     */
    private static final String AMOUNT_PARAMETER = "amount";
    /**
     * Currency attribute
     */
    private static final String CURRENCY_PARAMETER = "currency";
    /**
     * Transaction date attribute
     */
    private static final String TRANSACTIONDATE_PARAMETER = "transactiondate";
    /**
     * Template true/false attribute
     */
    private static final String ISTEMPLATE_PARAMETER = "istemplate";
    /**
     * Template name attribute
     */
    private static final String TEMPLATE_PARAMETER = "template";
    /**
     * Captcha for payment attribute
     */
    private static final String CAPTCHA_PARAMETER = "captcha";
    /**
     * Success message attribute
     */
    private static final String ERROR_ATTRIBUTE = "err";
    /**
     * Error message attribute
     */
    private static final String SUCCESS_ATTRIBUTE = "suc";

    /**
     * User manager initialization
     */
    private UserManager userManager;
    /**
     * Payment manager initialization
     */
    private PaymentManager paymentManager;
    /**
     * Account manager initialization
     */
    private AccountManager accountManager;
    /**
     * Account instance initialization
     */
    private Account account;
    /**
     * Properties value for regex for symbols validation
     */
    @Value("${regex.symbol}")
    private String symbolRegex;
    /**
     * Properties value for numbers validation
     */
    @Value("${regex.default.numeric}")
    private String numberRegex;
    /**
     * Properties value for captcha value
     */
    @Value("${captcha.payment.value}")
    private String paymentCaptcha;
    /**
     * Properties value bank codes list
     */
    @Value("#{'${bank.codes}'.split(',')}")
    private List<String> bankCodes;
    /**
     * Properties value for number with decimals validation
     */
    @Value("${regex.numberWithDecimals}")
    private String numberWithDecimalsRegex;
    /**
     * Properties value for pre account number validation
     */
    @Value("${regex.preAccountNumber}")
    private String preAccountRegex;
    /**
     * Properties value for account number validation
     */
    @Value("${regex.account}")
    private String accountRegex;

    /**
     * Logger class used for logging of useful info
     */
    private final static Logger logger = Logger.getLogger(Pay.class);

    /**
     * Validator initialization
     */
    private Validator stringValidator;

    /**
     * Setter for a string validator
     * @param stringValidator provided string validator
     */
    @Autowired
    public void setStringValidator(Validator stringValidator) {
        this.stringValidator = stringValidator;
    }

    /**
     * Setter for a user manager
     * @param userManager provided user manager
     */
    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    /**
     * Setter for an account manager
     * @param accountManager provided account manager
     */
    @Autowired
    public void setAccountManager(AccountManager accountManager) { this.accountManager = accountManager; }

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
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
            req.setAttribute("bankcodes", bankCodes);
            req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
        } else {
            // User is not authorised to do the action.
            resp.sendError(401, "You are not authorised to access this page.");
        }
    }

    /**
     * @inheritDoc
     * Post method implementation
     * @param req servletRequest provided
     * @param resp servletResponse provided
     * @throws ServletException thrown in case of error
     * @throws IOException thrown in case of error
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String selectedTemplate = req.getParameter(SELECTEDTEMPLATE_PARAMETER);
        String sendTo = "";
        String recPreAccount = "";
        if (selectedTemplate == null && !req.getParameter(SENDTO_PARAMETER).isEmpty()) {
            sendTo = req.getParameter(SENDTO_PARAMETER).split("-")[(req.getParameter(SENDTO_PARAMETER).split("-").length == 2 ? 1 : 0)];
            recPreAccount = req.getParameter(SENDTO_PARAMETER).split("-").length == 2 ? req.getParameter(SENDTO_PARAMETER).split("-")[0] : "";
        }
        String bankCode = req.getParameter(BANKCODE_PARAMETER);
        String vs = req.getParameter(VS_PARAMETER);
        String cs = req.getParameter(CS_PARAMETER);
        String ss = req.getParameter(SS_PARAMETER);
        String recipientMessage = req.getParameter(RECIPIENTMESSAGE_PARAMETER);
        String myMessage = req.getParameter(MYMESSAGE_PARAMETER);
        String amount = req.getParameter(AMOUNT_PARAMETER);
        String currency = req.getParameter(CURRENCY_PARAMETER);
        String transactionDate = req.getParameter(TRANSACTIONDATE_PARAMETER);
        String isTemplate = req.getParameter(ISTEMPLATE_PARAMETER);
        String template = req.getParameter(TEMPLATE_PARAMETER);
        String captcha = req.getParameter(CAPTCHA_PARAMETER);

        if (req.getParameter("loadtemplate") != null) {
            if ("" != selectedTemplate) {
                Payment loadedTemplatePayment = paymentManager.loadPaymentByTemplate(req.getSession().getAttribute("user").toString(), selectedTemplate);

                req.setAttribute(SELECTEDTEMPLATE_PARAMETER, loadedTemplatePayment.getTemplate());
                req.setAttribute(SENDTO_PARAMETER, ((!loadedTemplatePayment.getRecipientPreAccountNumber().isEmpty() ? (loadedTemplatePayment.getRecipientPreAccountNumber() + "-") : "") + loadedTemplatePayment.getRecipientAccount()));
                req.setAttribute(BANKCODE_PARAMETER, loadedTemplatePayment.getRecipientBankCode());
                req.setAttribute(VS_PARAMETER, loadedTemplatePayment.getVs());
                req.setAttribute(CS_PARAMETER, loadedTemplatePayment.getCs());
                req.setAttribute(SS_PARAMETER, loadedTemplatePayment.getSs());
                req.setAttribute(RECIPIENTMESSAGE_PARAMETER, loadedTemplatePayment.getRecipientMessage());
                req.setAttribute(MYMESSAGE_PARAMETER, loadedTemplatePayment.getSenderMessage());
                req.setAttribute(AMOUNT_PARAMETER, String.format( "%.3f", loadedTemplatePayment.getAmount()));
                req.setAttribute(CURRENCY_PARAMETER, loadedTemplatePayment.getCurrency());
                req.setAttribute(TRANSACTIONDATE_PARAMETER, null != loadedTemplatePayment.getTransactionDate() ? loadedTemplatePayment.getTransactionDate().toString().substring(0, 10) : "");
                req.setAttribute(ISTEMPLATE_PARAMETER, "on");
                req.setAttribute(TEMPLATE_PARAMETER, loadedTemplatePayment.getTemplate());

                logger.debug("Selected template: [" + selectedTemplate + "]");

                try {
                    logger.info("Template loaded: " + selectedTemplate + "...");
                    succsessDispatch("Template loaded: " + selectedTemplate + "...", req, resp);
                } catch(Exception e) {
                    errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency,isTemplate, template, e.getMessage(), req, resp);
                }

            } else {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Template name has to be selected!", req, resp);
                return;
            }
        }

        else if (req.getParameter("pay") != null) {

            account = accountManager.findAccountByUsername(req.getSession().getAttribute("user").toString());

            Date transactionDateDate = null;
            try {
                transactionDateDate = new SimpleDateFormat("yyyy-MM-dd").parse(transactionDate);
            } catch (ParseException e) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Date provided was in invalid format!", req, resp);
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

            if(!vs.isEmpty() && !stringValidator.isValid(vs, symbolRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Variable symbol in invalid format!", req, resp);
                return;
            }

            if(!cs.isEmpty()  && !stringValidator.isValid(cs, symbolRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Constant symbol in invalid format!", req, resp);
                return;
            }

            if(!ss.isEmpty()  && !stringValidator.isValid(ss, symbolRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Specific symbol in invalid format!", req, resp);
                return;
            }

            if(!stringValidator.isValid(sendTo, accountRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Account number in invalid format!", req, resp);
                return;
            }

            if(!recPreAccount.isEmpty() && !stringValidator.isValid(recPreAccount, preAccountRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Account number in invalid format!", req, resp);
                return;
            }

            if(!stringValidator.isValid(bankCode, numberRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Bank code in invalid format!", req, resp);
                return;
            }

            if(!stringValidator.isValid(amount, numberWithDecimalsRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Amount in invalid format!", req, resp);
                return;
            }

            if(!stringValidator.isValid(captcha, numberRegex)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Captcha in invalid format!", req, resp);
                return;
            }

            if (!captcha.equals(paymentCaptcha)) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Please answer the captcha question 'One + one ='!", req, resp);
                return;
            }

            if (null != isTemplate && null == template) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, "Please fill the template name you want to save or uncheck 'Save as a template'!", req, resp);
                return;
            }

            // When user uncheck isTemplate, set template to null, so it's not saved
            if (null == isTemplate) {
                template = null;
            }

            if (null != isTemplate) {
                logger.debug("Saving as a template: [" + template + "]");
            }

            try {
                logger.info("New payment created!");
                paymentManager.createPayment(new Payment(selectedTemplate, sendTo, bankCode, recPreAccount, account.getNumber(), account.getBank(), "", vs, cs, ss, recipientMessage, myMessage, amount, currency, transactionDateDate, template), req.getSession().getAttribute("user").toString());
                succsessDispatch("Payment was successfully created!", req, resp);
            } catch(Exception e) {
                errorDispatch(selectedTemplate, sendTo, bankCode, recPreAccount, vs, cs, ss, recipientMessage, myMessage, transactionDate, amount, currency, isTemplate, template, e.getMessage(), req, resp);
            }
        }

    }

    /**
     * Dispatcher method used for success dispatch
     * @param suc success message provided
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of error thrown
     * @throws IOException in case of error thrown
     */
    private void succsessDispatch(String suc, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, suc);
        req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
        req.setAttribute("bankcodes", bankCodes);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

    /**
     * Dispatcher method used for error dispatch
     * @param selectedTemplate provided template selected to be loaded for recipient
     * @param sendTo provided account number for recipient
     * @param bankCode provided bank code for recipient
     * @param recPreAccount provided pre account number for recipient
     * @param vs provided variable symbol
     * @param cs provided constant symbol
     * @param ss provided specific symbol
     * @param recipientMessage provided message note for recipient
     * @param myMessage provided message note for sender
     * @param transactionDate provided transaction date
     * @param amount provided amount code
     * @param currency provided currency code
     * @param isTemplate provided boolean value if template should be saved or not
     * @param template provided template name
     * @param err provided error message
     * @param req provided request
     * @param resp provided response
     * @throws ServletException in case of the error
     * @throws IOException in case of the error
     */
    private void errorDispatch(String selectedTemplate, String sendTo, String bankCode, String recPreAccount, String vs, String cs, String ss, String recipientMessage, String myMessage, String transactionDate, String amount, String currency, String isTemplate, String template, String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SELECTEDTEMPLATE_PARAMETER, selectedTemplate);
        req.setAttribute(SENDTO_PARAMETER, (!recPreAccount.isEmpty() ? (recPreAccount + "-") : "" ) + sendTo);
        req.setAttribute(BANKCODE_PARAMETER, bankCode);
        req.setAttribute(VS_PARAMETER, vs);
        req.setAttribute(CS_PARAMETER, cs);
        req.setAttribute(SS_PARAMETER, ss);
        req.setAttribute(RECIPIENTMESSAGE_PARAMETER, recipientMessage);
        req.setAttribute(MYMESSAGE_PARAMETER, myMessage);
        req.setAttribute(AMOUNT_PARAMETER, amount);
        req.setAttribute(CURRENCY_PARAMETER, currency);
        req.setAttribute(TRANSACTIONDATE_PARAMETER, null != transactionDate ? transactionDate.substring(0, 10) : "");
        req.setAttribute(ISTEMPLATE_PARAMETER, isTemplate);
        req.setAttribute(TEMPLATE_PARAMETER, template);
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.setAttribute("templates", paymentManager.loadPaymentTemplate(req.getSession().getAttribute("user").toString()));
        req.setAttribute("bankcodes", bankCodes);
        req.getRequestDispatcher("/WEB-INF/pages/banking/pay.jsp").forward(req, resp);
    }

}
