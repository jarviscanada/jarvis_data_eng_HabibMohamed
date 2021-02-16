package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao, QuoteDao quoteDao){

    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;

  }

  public List<Quote> saveQuotes(List<String> tickers){

    List<Quote> quotes = new ArrayList<Quote>();

    tickers.stream()
        .forEach(s -> quotes.add(saveQuote(s)));

    return quotes;
  }

  public Quote saveQuote(String ticker){

    IexQuote iexQuote = marketDataDao.findById(ticker).get();

    Quote quote = buildQuoteFromIexQuote(iexQuote);

    quoteDao.save(quote);

    return quote;

  }

  public void updateMarketData(){

    List<Quote> quotes = quoteDao.findAll();

    quotes.stream()
        .forEach(
            q -> {
              IexQuote iexQuote = marketDataDao.findById(q.getId()).get();
              Quote quote = buildQuoteFromIexQuote(iexQuote);
              quoteDao.save(quote);
            }
        );

  }

  public Quote saveQuote(Quote quote) { return quoteDao.save(quote); }

  public List<Quote> findAllQuotes() { return quoteDao.findAll(); }

  public IexQuote findIexQuoteByTicker(String ticker){
    return marketDataDao.findById(ticker).get();
  }

  public List<IexQuote> findIexQuotesByTickers(List<String> tickers){
    return marketDataDao.findAllById(tickers);
  }

  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){

    int askSize = iexQuote.getIexAskSize() == null ? 0 : iexQuote.getIexAskSize().intValue();
    double askPrice = iexQuote.getIexAskPrice() == null ? 0d : iexQuote.getIexAskPrice();
    int bidSize = iexQuote.getIexBidSize() == null ? 0 : iexQuote.getIexBidSize().intValue();
    double bidPrice = iexQuote.getIexBidPrice() == null ? 0 : iexQuote.getIexBidPrice();
    double lastPrice = iexQuote.getLatestPrice() == null ? 0 : iexQuote.getLatestPrice();

    Quote quote = new Quote();
    quote.setId(iexQuote.getSymbol().toLowerCase());
    quote.setAskSize(askSize);
    quote.setAskPrice(askPrice);
    quote.setBidSize(bidSize);
    quote.setBidPrice(bidPrice);
    quote.setLastPrice(lastPrice);

    return quote;

  }

}
