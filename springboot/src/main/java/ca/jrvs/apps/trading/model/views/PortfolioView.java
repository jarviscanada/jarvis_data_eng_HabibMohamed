package ca.jrvs.apps.trading.model.views;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;

public class PortfolioView {

  private Integer id;
  private Double amount;
  private List<Position> positions;

  public PortfolioView(Account account, List<Position> positions){

    this.id = account.getId();
    this.amount = account.getAmount();
    this.positions = positions;

  }

  public Integer getId() {
    return id;
  }

  public Double getAmount() {
    return amount;
  }

  public List<Position> getPositions() {
    return positions;
  }
}
