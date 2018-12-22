package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.domain.Account;
import drdm.school.pia.domain.Card;
import drdm.school.pia.domain.User;
import drdm.school.pia.manager.CardManager;
import drdm.school.pia.utils.ExpirationGenerator;
import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.utils.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Michal Drda
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class DefaultCardManager implements CardManager {

    private CardDao cardDao;
    private StringGenerator stringGenerator;
    private LongGenerator numberGenerator;
    private ExpirationGenerator cardExpirationGenerator;

    @Value("${cardNo.length}")
    private int cardNoLength;
    @Value("${cvcNo.length}")
    private int cvcNoLength;
    @Value("${pin.length}")
    private int pinLength;
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

    public CardDao getCardDao() {
        return cardDao;
    }

    @Autowired
    public void setCardDao(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public StringGenerator getStringGenerator() { return stringGenerator; }
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    public LongGenerator getIntGenerator() { return numberGenerator; }
    @Autowired
    public void setIntGenerator(LongGenerator generator) { this.numberGenerator = generator; }

    public ExpirationGenerator getCardExpirationGenerator() { return cardExpirationGenerator; }
    @Autowired
    public void setcardExpirationGenerator(ExpirationGenerator generator) { this.cardExpirationGenerator = generator; }

    /**
     * Creates a card for user - implementation
     * @param user user to own the card created
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
    }

    public Card findByUsername(String username) {
        return cardDao.findByUsername(username);
    }
}
