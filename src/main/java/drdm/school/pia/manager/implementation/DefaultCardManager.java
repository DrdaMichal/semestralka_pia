package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.Card;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.manager.CardManager;
import drdm.school.pia.utils.ExpirationGenerator;
import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.utils.StringGenerator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @inheritDoc
 * Implementation of AccountManager interface
 * @author Michal Drda
 */
@Service
@Transactional
public class DefaultCardManager implements CardManager {

    /**
     * CardDao initialization
     * used for accessing card objects
     */
    private CardDao cardDao;
    /**
     * StringGenerator initialization
     * used for generating strings
     */
    private StringGenerator stringGenerator;
    /**
     * LongGenerator initialization
     * used for generating numbers
     */
    private LongGenerator numberGenerator;
    /**
     * ExpirationGenerator initialization
     * used for creating card expirations from current date
     */
    private ExpirationGenerator cardExpirationGenerator;

    /**
     * Logger used for loging of important events
     */
    final static Logger logger = Logger.getLogger(DefaultCardManager.class);

    /**
     * Length of card number to be generated
     */
    @Value("${cardNo.length}")
    private int cardNoLength;
    /**
     * Length of cvc code to be generated
     */
    @Value("${cvcNo.length}")
    private int cvcNoLength;
    /**
     * Length of pin code to be generated
     */
    @Value("${pin.length}")
    private int pinLength;
    /**
     * Months count of the period to be added to the current date to create card expiration
     */
    @Value("${cardExpiration.months}")
    private int cardExpirationInMonthsLength;

    /**
     * Default constructor
     */
    public DefaultCardManager() {

    }

    /**
     * Constructor to inject CardDao
     * @param cardDao
     */
    public DefaultCardManager(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    /**
     * Getter of the card Dao
     * @return cardDao
     */
    public CardDao getCardDao() {
        return cardDao;
    }

    /**
     * Setter of the card dao
     * @param cardDao provided card dao
     */
    @Autowired
    public void setCardDao(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    /**
     * Getter of the string generator
     * @return string generator
     */
    public StringGenerator getStringGenerator() { return stringGenerator; }

    /**
     * Setter of the string generator
     * @param generator provided string generator
     */
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    /**
     * Getter for a number generator
     * @return number generator
     */
    public LongGenerator getNumberGenerator() { return numberGenerator; }

    /**
     * Setter for a number generator
     * @param generator provided number generator
     */
    @Autowired
    public void setNumberGenerator(LongGenerator generator) { this.numberGenerator = generator; }

    /**
     * Getter for an expiration generator (card)
     * @return card expiration generator
     */
    public ExpirationGenerator getCardExpirationGenerator() { return cardExpirationGenerator; }

    /**
     * Setter for a card expiration generator
     * @param generator provided card expiration generator
     */
    @Autowired
    public void setcardExpirationGenerator(ExpirationGenerator generator) { this.cardExpirationGenerator = generator; }

    /**
     * @inheritDoc
     * creates a new card with provided user and account
     */
    @Override
    public void createCard(User user, Account account) {
        Card newCard = new Card();
        newCard.setCardNumber(String.valueOf(numberGenerator.generate(cardNoLength)));
        newCard.setCvc(String.valueOf(numberGenerator.generate(cvcNoLength)));
        newCard.setCardExpiration(cardExpirationGenerator.generateExpiration(cardExpirationInMonthsLength));
        newCard.setPin(String.valueOf(numberGenerator.generate(pinLength)));
        newCard.setAccount(account);

        // Check that card is unique and generate new one in case that it's not
        Card cardExistingCheck = cardDao.findByCardnumber(newCard.getCardNumber());
        if (cardExistingCheck != null) {
            numberGenerator.generate(cardNoLength);
        }

        account.getCards().add(newCard);
        cardDao.save(newCard);
        logger.info("Card created for user< " + user.getUsername() + ">: " + user.getAccount().getCards().toString());
    }

}
