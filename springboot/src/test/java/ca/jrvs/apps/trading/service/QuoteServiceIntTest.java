package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setup() { quoteDao.deleteAll(); }

  private Quote insertOne() {
    Quote savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setLastPrice(10.1d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setBidPrice(10.2d);
    savedQuote.setAskSize(10);
    quoteDao.save(savedQuote);
    return savedQuote;
  }

  @Test
  public void findIexQuoteByTicker() {

    IexQuote iexQuote = quoteService.findIexQuoteByTicker("aapl");

    assertNotNull(iexQuote);
    assertEquals("AAPL", iexQuote.getSymbol());
    assertEquals("Apple Inc", iexQuote.getCompanyName());

  }

  @Test
  public void updateMarketData() {

    Quote savedQuote = insertOne();

    List<Quote > quotes = quoteService.updateMarketData();

    assertNotEquals(savedQuote.getLastPrice(), quotes.get(0).getLastPrice());

  }

  @Test
  public void saveQuotes() {

    List<String> tickers = new ArrayList<String>();
    tickers.add("AAPL");
    tickers.add("TWTR");

    List<Quote> quotes = quoteService.saveQuotes(tickers);
    List<Quote> quotes2 = quoteDao.findAll();

    assertEquals(quotes2.get(0).getId(), quotes.get(0).getId());

  }

  @Test
  public void saveQuote() {

    String ticker = "twtr";

    Quote quote = quoteService.saveQuote(ticker);
    Quote quote2 = quoteDao.findById(ticker).get();

    assertThat(quote).isEqualToComparingFieldByField(quote2);


  }

  @Test
  public void findAllQuotes() {

    List<String> tickers = new ArrayList<String>();
    tickers.add("AAPL");
    tickers.add("TWTR");

    List<Quote> quotes = quoteService.saveQuotes(tickers);

    assertEquals(tickers.get(0).toLowerCase(), quotes.get(0).getId());
    assertEquals(tickers.get(1).toLowerCase(), quotes.get(1).getId());

  }

}
