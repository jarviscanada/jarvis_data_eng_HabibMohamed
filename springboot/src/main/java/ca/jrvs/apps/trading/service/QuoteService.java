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
  public QuoteService(MarketDataDao marketDataDao, QuoteDao quoteDao) {

    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;

  }

  /**
   * Saves the tickers as quotes within
   * the table and returns the quotes as a List.
   *
   * @param tickers
   * @return List of Quote
   */
  public List<Quote> saveQuotes(List<String> tickers) {

    List<Quote> quotes = new ArrayList<Quote>();

    tickers.stream()
        .forEach(s -> quotes.add(saveQuote(s)));

    return quotes;
  }

  /**
   * Takes the given ticker, gets the IexQuote
   * associated with it, converts the IexQuote
   * to a Quote, and saves that within the table,
   * before returning the Quote.
   *
   * @param ticker
   * @return Quote quote
   */
  public Quote saveQuote(String ticker) {

    IexQuote iexQuote = marketDataDao.findById(ticker).get();

    Quote quote = buildQuoteFromIexQuote(iexQuote);

    saveQuote(quote);

    return quote;

  }

  /**
   * Updates all the quotes within the table.
   *
   * @return List of all Quote
   */
  public List<Quote> updateMarketData() {

    List<Quote> quotes = quoteDao.findAll();
    List<Quote> updatedQuotes = new ArrayList<Quote>();

    quotes.stream()
        .forEach(
            q -> {
              IexQuote iexQuote = marketDataDao.findById(q.getId()).get();
              Quote quote = buildQuoteFromIexQuote(iexQuote);
              quoteDao.save(quote);
              updatedQuotes.add(quote);
            }
        );
    return updatedQuotes;

  }

  /**
   * Saves quote
   *
   * @param quote
   * @return Quote
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  /**
   * Gets all Quote in table
   *
   * @return List of Quote
   */
  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }

  /**
   * Finds IexQuote using provided ticker
   *
   * @param ticker
   * @return IexQuote
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker).get();
  }

  /**
   * Retrieves all IexQuotes corresponding
   * to the list of tickers provided.
   *
   * @param tickers
   * @return List of IexQuote
   */
  public List<IexQuote> findIexQuotesByTickers(List<String> tickers) {
    return marketDataDao.findAllById(tickers);
  }

  /**
   * Builds a Quote from an IexQuote
   *
   * @param iexQuote
   * @return Quote quote
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {

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
