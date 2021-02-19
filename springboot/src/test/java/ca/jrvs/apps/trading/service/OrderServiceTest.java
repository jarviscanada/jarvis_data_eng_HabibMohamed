package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Captor
  ArgumentCaptor<SecurityOrder> captorOrder;

  @Mock
  private AccountDao accountDao;
  @Mock
  private SecurityOrderDao securityOrderDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;

  @InjectMocks
  private OrderService orderService;

  private SecurityOrder securityOrder;
  private Account account;
  private Position position;


  @Before
  public void setUp(){

    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(1);
    securityOrder.setNotes("buy");
    securityOrder.setPrice(2.4d);
    securityOrder.setTicker("twtr");
    securityOrder.setStatus("OPEN");
    securityOrder.setSize(5);

    account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(100.5d);

    position = new Position();
    position.setId(1);
    position.setTicker("twtr");
    position.setPosition(10);

  }

  @Test(expected = IllegalArgumentException.class)
  public void marketFail(){
    MarketOrderDto marketOrderDto = new MarketOrderDto();
    marketOrderDto.setId(null);


    orderService.executeMarketOrder(marketOrderDto);

  }

  @Test
  public void testBuy(){

    when(securityOrderDao.save(any())).thenReturn(securityOrder);
    when(accountDao.findById(any())).thenReturn(Optional.of(account));
    when(accountDao.existsById(any())).thenReturn(true);
    when(quoteDao.existsById(any())).thenReturn(true);

    MarketOrderDto marketOrderDto = new MarketOrderDto();

    marketOrderDto.setId(1);
    marketOrderDto.setOrderType("buy");
    marketOrderDto.setPrice(2.4d);
    marketOrderDto.setSize(5);
    marketOrderDto.setTicker("twtr");

    SecurityOrder returnedOrder = orderService.executeMarketOrder(marketOrderDto);

    assertEquals(securityOrder.getNotes(), returnedOrder.getNotes());

  }

  @Test
  public void testSell(){

    when(securityOrderDao.save(any())).thenReturn(securityOrder);
    when(accountDao.findById(any())).thenReturn(Optional.of(account));
    when(accountDao.existsById(any())).thenReturn(true);
    when(quoteDao.existsById(any())).thenReturn(true);
    when(positionDao.existsById(any())).thenReturn(true);
    when(positionDao.findById(any())).thenReturn(Optional.of(position));

    MarketOrderDto marketOrderDto = new MarketOrderDto();

    marketOrderDto.setId(1);
    marketOrderDto.setOrderType("sell");
    marketOrderDto.setPrice(2.4d);
    marketOrderDto.setSize(5);
    marketOrderDto.setTicker("twtr");

    SecurityOrder returnedOrder = orderService.executeMarketOrder(marketOrderDto);

    assertEquals(securityOrder.getNotes(), returnedOrder.getNotes());

  }


}