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

  public TraderAccountView createTraderAndAccount(Trader trader){

    if(!validateTrader(trader))
      throw new IllegalArgumentException("Please provide valid trader info");

    Trader dbTrader = traderDao.save(trader);
    Account account = fromTraderToAccount(dbTrader);
    Account dbAccount = accountDao.save(account);

    TraderAccountView tAV = new TraderAccountView(dbTrader, dbAccount);

    return tAV;
  }

  public void deleteTraderById(Integer traderId){

    if(traderId == null || !traderDao.existsById(traderId)){
      throw new IllegalArgumentException("Trader Id provided does not exist");
    }

    Account account = accountDao.findById(traderId).get();

    Position position =
        positionDao.existsById(account.getId()) ?
            positionDao.findById(account.getId()).get() : null;

    if(account.getAmount() == 0d &&
        (position == null || position.getPosition() == 0)){

      securityOrderDao.deleteById(traderId);
      accountDao.deleteById(traderId);
      traderDao.deleteById(traderId);

    }

  }

  public Account deposit(Integer traderId, Double fund){

    if(validateTraderId(traderId)){
      throw new IllegalArgumentException("This trader id doesn't exist");
    }

    if(fund <= 0d){
      throw new IllegalArgumentException("Funds must be greater than 0");
    }

    Account account = accountDao.findById(traderId).get();
    Double current = account.getAmount();
    account.setAmount(current + fund);

    Account updatedAccount = accountDao.save(account);

    return updatedAccount;
  }

  public Account withdraw(Integer traderId, Double fund){

    if(validateTraderId(traderId)){
      throw new IllegalArgumentException("This trader id doesn't exist");
    }

    if(fund <= 0d){
      throw new IllegalArgumentException("Funds must be greater than 0");
    }

    Account account = accountDao.findById(traderId).get();
    Double current = account.getAmount();

    if(current <= 0){
      throw new IllegalArgumentException("Can't withdraw from empty account");
    }

    account.setAmount(current - fund);

    Account updatedAccount = accountDao.save(account);

    return updatedAccount;
  }

  private boolean validateTraderId(Integer traderId){
    return traderId == null || !traderDao.existsById(traderId);
  }

  private boolean validateTrader(Trader trader){

    if(trader == null || trader.getFirst_name().isEmpty()
        || trader.getLast_name().isEmpty()
        || trader.getCountry().isEmpty()
        || trader.getEmail() .isEmpty()
        || trader.getDob() == null) {
      return false;
    }

    return true;

  }

  private Account fromTraderToAccount(Trader trader){

    Account account = new Account();

    account.setTraderId(trader.getId());
    account.setAmount(0d);

    return account;

  }

}
