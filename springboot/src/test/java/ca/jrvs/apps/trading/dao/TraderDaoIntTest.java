package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader;

  @Before
  public void insertOne(){
    savedTrader = new Trader();
    savedTrader.setId(9181);
    savedTrader.setFirst_name("Jeff");
    savedTrader.setLast_name("Mynameis");
    savedTrader.setCountry("USA");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("mandem@yahoo.com");
    traderDao.save(savedTrader);
  }

  @After
  public void deleteOne(){
    traderDao.deleteById(savedTrader.getId());
  }

  @Test
  public void findById(){

    Trader trader = traderDao.findById(savedTrader.getId()).get();

    assertEquals(savedTrader.getEmail(), trader.getEmail());

  }

  @Test
  public void findALl(){

    List<Trader> traders = traderDao.findAll();

    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());

  }

  @Test
  public void findAllById(){

    List<Integer> ids = new ArrayList<Integer>();
    ids.add(savedTrader.getId());

    List<Trader> traders = traderDao.findAllById(ids);

    assertEquals(1, traders.size());
    assertEquals(savedTrader.getDob().toString(), traders.get(0).getDob().toString());

  }

  @Test
  public void count(){

    long count = traderDao.count();

    assertEquals(1, count);

  }

  @Test
  public void existsById(){

    boolean isIn = traderDao.existsById(savedTrader.getId());

    assertTrue(isIn);

  }

  @Test
  public void insert(){

    Trader newTrader = savedTrader;
    newTrader.setId(19829);

    traderDao.save(newTrader);

    long count = traderDao.count();

    assertEquals(2, count);

  }

}