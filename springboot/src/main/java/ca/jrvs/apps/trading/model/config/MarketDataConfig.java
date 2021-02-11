package ca.jrvs.apps.trading.model.config;

public class MarketDataConfig {

  private String host;
  private String token;

  public MarketDataConfig(String host, String token) {
    this.host = host;
    this.token = token;
  }

  public String getHost() {
    return host;
  }

  public String getToken() {
    return token;
  }

}
