package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.views.PortfolioView;
import ca.jrvs.apps.trading.model.views.TraderAccountView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DashboardService {

  private TraderDao traderDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(TraderDao traderDao, PositionDao positionDao,
      AccountDao accountDao, QuoteDao quoteDao) {
    this.traderDao = traderDao;
    this.positionDao = positionDao;
    this.accountDao = accountDao;
    this.quoteDao = quoteDao;
  }


  public TraderAccountView getTraderAccount(Integer traderId){

    if(traderId == null || !traderDao.existsById(traderId)){
      throw new IllegalArgumentException("Trader id is not valid");
    }

    Trader trader = traderDao.findById(traderId).get();
    Account account = accountDao.findById(traderId).get();

    TraderAccountView traderAccountView = new TraderAccountView(trader, account);

    return traderAccountView;
  }

  public PortfolioView getPortfolioViewByTraderId(Integer traderId){

    if(traderId  == null || !accountDao.existsById(traderId)){
      throw new IllegalArgumentException("Trader Id is invalid.");
    }

    List<Integer> id = new ArrayList<Integer>();
    id.add(traderId);

    Account account = accountDao.findById(traderId).get();
    List<Position> positions = positionDao.findAllById(id);

    PortfolioView portfolioView = new PortfolioView(account, positions);

    return portfolioView;

  }

}
