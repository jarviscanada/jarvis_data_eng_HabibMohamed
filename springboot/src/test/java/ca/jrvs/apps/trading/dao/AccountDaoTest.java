package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
public class AccountDaoTest {
  
  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;
  
  private Account account;

  private Trader savedTrader;


  @Before
  public void insertOne() {

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

    account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(45.3d);
    accountDao.save(account);

  }

  @After
  public void deleteOne() {

    accountDao.deleteById(account.getId());

  }

  @Test
  public void findById(){

    Account accountNew = accountDao.findById(account.getId()).get();

    assertEquals(account.getAmount(), accountNew.getAmount() );

  }

  @Test
  public void findALl(){

    List<Account> accounts = accountDao.findAll();

    assertEquals(1, accounts.size());
    assertEquals(account.getTraderId(), accounts.get(0).getTraderId());

  }

  @Test
  public void findAllById(){

    List<Integer> ids = new ArrayList<Integer>();
    ids.add(account.getId());

    List<Account> accounts = accountDao.findAllById(ids);

    assertEquals(1, accounts.size());
    assertEquals(account.getTraderId(), accounts.get(0).getTraderId());

  }

  @Test
  public void count(){

    long count = accountDao.count();

    assertEquals(1, count);

  }

  @Test
  public void existsById(){

    boolean isIn = accountDao.existsById(account.getId());

    assertTrue(isIn);

  }

  @Test
  public void insert(){

    Account newAccount = account;
    newAccount.setId(19829);

    accountDao.save(newAccount);

    long count = accountDao.count();

    assertEquals(2, count);

  }
  
}