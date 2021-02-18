package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
public class PositionDaoTest {

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private PositionDao positionDao;

  private Account account;

  private Trader savedTrader;

  private SecurityOrder securityOrder;

  private Quote quote;



  @Before
  public void setUp() throws Exception {

    if(!traderDao.existsById(1)){

      savedTrader = new Trader();
      savedTrader.setId(1);
      savedTrader.setFirst_name("Jeff");
      savedTrader.setLast_name("Mynameis");
      savedTrader.setCountry("USA");
      savedTrader.setDob(new Date(System.currentTimeMillis()));
      savedTrader.setEmail("mandem@yahoo.com");
      traderDao.save(savedTrader);

    }

    if(!accountDao.existsById(1)){

      account = new Account();
      account.setId(1);
      account.setTraderId(1);
      account.setAmount(45.3d);
      accountDao.save(account);

    }

    if(!quoteDao.existsById("aapl")){

      quote = new Quote();
      quote.setAskPrice(10d);
      quote.setLastPrice(10.1d);
      quote.setBidSize(10);
      quote.setId("aapl");
      quote.setBidPrice(10.2d);
      quote.setAskSize(10);
      quoteDao.save(quote);

    }

    if(!securityOrderDao.existsById(1)){
      securityOrder = new SecurityOrder();
      securityOrder.setId(1);
      securityOrder.setTicker("aapl");
      securityOrder.setPrice(4.56d);
      securityOrder.setSize(15);
      securityOrder.setAccountId(1);
      securityOrder.setStatus("FILLED");
      securityOrder.setNotes("waiting no more");
      securityOrderDao.save(securityOrder);
    }


  }

  @Test
  public void findById(){

    Position position = positionDao.findById(securityOrder.getId()).get();

    assertEquals(securityOrder.getTicker(), position.getTicker());

  }

  @Test
  public void findAll(){

    List<Position> positions = positionDao.findAll();

    assertEquals(1, positions.size());
    assertEquals(securityOrder.getSize(), positions.get(0).getPosition());

  }

  @Test
  public void findAllById(){

    List<Integer> ids = new ArrayList<Integer>();
    ids.add(securityOrder.getId());

    List<Position> positions = positionDao.findAllById(ids);

    assertEquals(1, positions.size());
    assertEquals(securityOrder.getTicker(), positions.get(0).getTicker());

  }

  @Test
  public void count(){

    long count = positionDao.count();

    assertEquals(1, count);

  }

  @Test
  public void existsById(){

    boolean isIn = positionDao.existsById(securityOrder.getId());

    assertTrue(isIn);

  }


}