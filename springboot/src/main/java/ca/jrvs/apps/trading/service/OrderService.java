package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao,
      SecurityOrderDao securityOrderDao, QuoteDao quoteDao,
      PositionDao positionDao) {
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Creates and stores a SecurityOrder based
   * on the attributes of the provided MarketOrderDto.
   *
   * @param orderDto
   * @return SecurityOrder securityOrder
   *
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {

    if (!validate(orderDto)) {
      throw new IllegalArgumentException("Market order is invalid");
    }

    SecurityOrder securityOrder = null;
    Account account = accountDao.findById(orderDto.getId()).get();

    if (orderDto.getOrderType().equalsIgnoreCase("buy")) {
      securityOrder = handleBuyMarketOrder(orderDto, account);
    } else if (orderDto.getOrderType().equalsIgnoreCase("sell")) {
      securityOrder = handleSellMarketOrder(orderDto, account);
    }

    SecurityOrder dbSecurityOrder = securityOrderDao.save(securityOrder);
    return dbSecurityOrder;

  }

  /**
   * Validates if provided MarketOrderDto
   * is valid for further manipulation.
   *
   * @param orderDto
   * @return true if valid, false otherwise
   *
   */
  private boolean validate(MarketOrderDto orderDto) {

    return orderDto.getId() != null && accountDao.existsById(orderDto.getId())
        && orderDto.getSize() > 0 && orderDto.getPrice() > 0
        && (orderDto.getOrderType().equalsIgnoreCase("buy") ||
        orderDto.getOrderType().equalsIgnoreCase("sell"))
        && !orderDto.getTicker().isEmpty()
        && quoteDao.existsById(orderDto.getTicker());

  }

  /**
   * Handles market orders of type "buy" and
   * makes a SecurityOrder out of them.
   *
   * @param marketOrderDto
   * @param account
   * @return SecurityOrder securityOrder
   */
  private SecurityOrder handleBuyMarketOrder(MarketOrderDto marketOrderDto, Account account) {

    Double total = marketOrderDto.getPrice() * marketOrderDto.getSize();
    SecurityOrder securityOrder = new SecurityOrder();

    if (account.getAmount() > total) {
      securityOrder = fillIn(marketOrderDto, account);
    }

    return securityOrder;

  }

  /**
   * Handles market orders of type "sell" and
   * makes SecurityOrder out of them.
   *
   * @param marketOrderDto
   * @param account
   * @return SecurityOrder securityOrder
   *
   */
  private SecurityOrder handleSellMarketOrder(MarketOrderDto marketOrderDto, Account account) {

    SecurityOrder securityOrder = new SecurityOrder();

    if (positionDao.existsById(account.getId())) {

      Position position = positionDao.findById(account.getId()).get();

      if (marketOrderDto.getSize() <= position.getPosition()) {
        securityOrder = fillIn(marketOrderDto, account);
      }

    }

    return securityOrder;

  }

  /**
   * Fills in a SecurityOrder with details from the
   * provided Account and MarketOrderDto
   *
   * @param marketOrderDto
   * @param account
   * @return SecurityOrder securityOrder
   *
   */
  private SecurityOrder fillIn(MarketOrderDto marketOrderDto, Account account) {

    SecurityOrder securityOrder = new SecurityOrder();

    securityOrder.setAccountId(account.getId());
    securityOrder.setStatus("OPEN");
    securityOrder.setTicker(marketOrderDto.getTicker());
    securityOrder.setPrice(marketOrderDto.getPrice());
    securityOrder.setSize(marketOrderDto.getSize());
    securityOrder.setNotes(marketOrderDto.getOrderType());

    return securityOrder;

  }

}
