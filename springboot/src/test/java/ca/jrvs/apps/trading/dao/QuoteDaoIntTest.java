package ca.jrvs.apps.trading.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private static Quote savedQuote;

  @BeforeClass
  public static void setUp(){
    savedQuote = new Quote();
  }

  @Before
  public void insertOne() {
    savedQuote.setAskPrice(10d);
    savedQuote.setLastPrice(10.1d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setBidPrice(10.2d);
    savedQuote.setAskSize(10);
    quoteDao.save(savedQuote);
  }

  @Test
  public void daoTest(){

    boolean isExist = quoteDao.existsById("aapl");
    assertEquals(true, isExist);

    long count = quoteDao.count();
    assertEquals(1, count);

    Quote foundQuote = quoteDao.findById("aapl").get();
    assertThat(savedQuote).isEqualToComparingFieldByField(foundQuote);

  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getId());
  }

}
