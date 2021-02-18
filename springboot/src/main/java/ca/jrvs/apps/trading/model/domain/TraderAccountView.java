package ca.jrvs.apps.trading.model.domain;

public class TraderAccountView {

  private Integer traderId;
  private Integer accountId;
  private Double amount;
  private String firstName;
  private String lastName;


  public TraderAccountView(Trader trader, Account account) {

    this.traderId = trader.getId();
    this.accountId = account.getId();
    this.amount = account.getAmount();
    this.firstName = trader.getFirst_name();
    this.lastName = trader.getLast_name();

  }

  public Integer getTraderId() {
    return traderId;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public Double getAmount() {
    return amount;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return "{"
        + "                        \"traderId\":\"" + traderId + "\""
        + ",\n                         \"accountId\":\"" + accountId + "\""
        + ",\n                         \"amount\":\"" + amount + "\""
        + ",\n                         \"firstName\":\"" + firstName + "\""
        + ",\n                         \"lastName\":\"" + lastName + "\""
        + "}";
  }
}
