package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
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
public class SecurityOrderDaoTest {

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  private Account account;

  private Trader savedTrader;

  private SecurityOrder securityOrder;

  private Quote quote;


  @Before
  public void insertOne() throws Exception {

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

    securityOrder = new SecurityOrder();
    securityOrder.setId(1);
    securityOrder.setTicker("aapl");
    securityOrder.setPrice(4.56d);
    securityOrder.setSize(15);
    securityOrder.setAccountId(1);
    securityOrder.setStatus("pending");
    securityOrder.setNotes("waiting on 2");
    securityOrderDao.save(securityOrder);

  }

  @After
  public void tearDown() throws Exception {

    securityOrderDao.deleteById(securityOrder.getId());

  }

  @Test
  public void findById(){

    SecurityOrder securityOrder2 = securityOrderDao.findById(securityOrder.getId()).get();

    assertEquals(securityOrder.getPrice(), securityOrder2.getPrice());

  }

  @Test
  public void findAll(){

    List<SecurityOrder> securityOrders = securityOrderDao.findAll();

    assertEquals(1, securityOrders.size());
    assertEquals(securityOrder.getAccountId(), securityOrders.get(0).getAccountId());

  }

  @Test
  public void findAllById(){

    List<Integer> ids = new ArrayList<Integer>();
    ids.add(securityOrder.getId());

    List<SecurityOrder> securityOrders = securityOrderDao.findAllById(ids);

    assertEquals(1, securityOrders.size());
    assertEquals(securityOrder.getStatus(), securityOrders.get(0).getStatus());

  }

  @Test
  public void count(){

    long count = securityOrderDao.count();

    assertEquals(1, count);

  }

  @Test
  public void existsById(){

    boolean isIn = securityOrderDao.existsById(securityOrder.getId());

    assertTrue(isIn);

  }

  @Test
  public void insert(){

    SecurityOrder securityOrder2 = securityOrder;
    securityOrder2.setId(2);

    securityOrderDao.save(securityOrder2);

    long count = securityOrderDao.count();

    assertEquals(2, count);

  }

}