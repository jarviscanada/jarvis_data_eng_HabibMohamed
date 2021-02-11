package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

//  private QuoteData quoteData;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao){

//    this.quoteData = quoteData;
    this.marketDataDao = marketDataDao;

  }

  public IexQuote findQuoteByTicker(String ticker){
    return marketDataDao.findById(ticker).get();
  }

  public List<IexQuote> findQuotesByTickers(List<String> tickers){
    return marketDataDao.findAllById(tickers);
  }

}
