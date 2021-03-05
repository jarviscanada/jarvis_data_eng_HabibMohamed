package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.views.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  /**
   * Builds a TraderAccountView that reflects the
   * created Trader and Account built via the passed
   * in Trader.
   *
   * @param trader
   * @return TraderAccountView tAV
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {

    if (!validateTrader(trader)) {
      throw new IllegalArgumentException("Please provide valid trader info");
    }

    Trader dbTrader = traderDao.save(trader);
    Account account = fromTraderToAccount(dbTrader);
    Account dbAccount = accountDao.save(account);

    TraderAccountView tAV = new TraderAccountView(dbTrader, dbAccount);
    return tAV;

  }

  /**
   * Deletes Trader and anything dependent on it
   * based on provided trader id.
   * @param traderId
   */
  public void deleteTraderById(Integer traderId) {

    if (traderId == null || !traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("Trader Id provided does not exist");
    }

    Account account = accountDao.findById(traderId).get();

    Position position =
        positionDao.existsById(account.getId()) ?
            positionDao.findById(account.getId()).get() : null;

    if (account.getAmount() == 0d &&
        (position == null || position.getPosition() == 0)) {

      securityOrderDao.deleteById(traderId);
      accountDao.deleteById(traderId);
      traderDao.deleteById(traderId);

    }

  }

  /**
   * Deposits the provided fund into the account specified
   * by the provided id after conducting legality checks.
   *
   * @param traderId
   * @param fund
   * @return Account account
   *
   */
  public Account deposit(Integer traderId, Double fund) {

    if (validateTraderId(traderId)) {
      throw new IllegalArgumentException("This trader id doesn't exist");
    }

    if (fund <= 0d) {
      throw new IllegalArgumentException("Funds must be greater than 0");
    }

    Account account = accountDao.findById(traderId).get();
    Double current = account.getAmount();
    account.setAmount(current + fund);

    Account updatedAccount = accountDao.save(account);
    return updatedAccount;

  }

  /**
   * Withdraws the provided fund from the account
   * specified by the provided id after conducting
   * legality checks.
   *
   * @param traderId
   * @param fund
   * @return Account account
   *
   */
  public Account withdraw(Integer traderId, Double fund) {

    if (validateTraderId(traderId)) {
      throw new IllegalArgumentException("This trader id doesn't exist");
    }

    if (fund <= 0d) {
      throw new IllegalArgumentException("Funds must be greater than 0");
    }

    Account account = accountDao.findById(traderId).get();
    Double current = account.getAmount();

    if (current <= 0) {
      throw new IllegalArgumentException("Can't withdraw from empty account");
    }

    account.setAmount(current - fund);

    Account updatedAccount = accountDao.save(account);
    return updatedAccount;

  }

  /**
   * Check if trader id is valid
   *
   * @param traderId
   * @return true if valid, false otherwise
   *
   */
  private boolean validateTraderId(Integer traderId) {
    return traderId == null || !traderDao.existsById(traderId);
  }

  /**
   * Checks if Trader object is valid
   *
   * @param trader
   * @return true if valid, false otherwise
   *
   */
  private boolean validateTrader(Trader trader) {

    if (trader == null || trader.getFirst_name().isEmpty()
        || trader.getLast_name().isEmpty()
        || trader.getCountry().isEmpty()
        || trader.getEmail().isEmpty()
        || trader.getDob() == null) {
      return false;
    }

    return true;

  }

  /**
   * Makes an Account out of provided Trader.
   *
   * @param trader
   * @return Account account
   *
   */
  private Account fromTraderToAccount(Trader trader) {

    Account account = new Account();
    account.setTraderId(trader.getId());
    account.setAmount(0d);
    return account;

  }

}
