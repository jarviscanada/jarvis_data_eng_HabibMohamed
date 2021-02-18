package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.sql.Date;
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
public class TraderAccountServiceTest {

  private TraderAccountView savedView;

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountDao accountDao;


  @Test
  public void createTraderAndAccount() {

    Trader trader = new Trader();

    trader.setId(1);
    trader.setFirst_name("Jeff");
    trader.setLast_name("Mynameis");
    trader.setCountry("USA");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("mandem@yahoo.com");

    savedView = traderAccountService.createTraderAndAccount(trader);

    assertEquals(trader.getId(), savedView.getAccountId());
    assertEquals(0d, savedView.getAmount().doubleValue(), 0.0001);

  }

  @Test
  public void deleteTraderById() {

    Trader trader = new Trader();

    trader.setId(1);
    trader.setFirst_name("Mike");
    trader.setLast_name("Jeff");
    trader.setCountry("Ireland");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("crazy@gmail.com");

    savedView = traderAccountService.createTraderAndAccount(trader);

    traderAccountService.deleteTraderById(savedView.getTraderId());

    assertFalse(traderDao.existsById(savedView.getTraderId()));

  }

  @Test
  public void deposit() {

    Trader trader = new Trader();

    trader.setId(1);
    trader.setFirst_name("Larry");
    trader.setLast_name("Jeff");
    trader.setCountry("Scotland");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("legend@gmail.com");

    savedView = traderAccountService.createTraderAndAccount(trader);

    Account updatedAccount = traderAccountService.deposit(savedView.getTraderId(), 2.4d);

    assertNotEquals(savedView.getAmount(), updatedAccount.getAmount());

  }

  @Test
  public void withdraw() {

    Trader trader = new Trader();

    trader.setId(1);
    trader.setFirst_name("Lionel");
    trader.setLast_name("Wessi");
    trader.setCountry("Argentina");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("leftfoot@gmail.com");

    savedView = traderAccountService.createTraderAndAccount(trader);

    Account updatedAccount = traderAccountService.deposit(savedView.getTraderId(), 2.4d);
    Account depletedAccount = traderAccountService.withdraw(savedView.getTraderId(), 1.3d);

    assertNotEquals(updatedAccount.getAmount(), depletedAccount.getAmount());

  }
}