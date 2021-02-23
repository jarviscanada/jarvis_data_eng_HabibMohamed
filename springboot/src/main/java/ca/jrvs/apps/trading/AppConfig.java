package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  /**
   * At startup, Spring builds a MarketDataConfig object that contains the base url and access token
   * for interacting with the IEX API.
   *
   * @return MarketDataConfig object
   */
  @Bean
  public MarketDataConfig marketDataConfig() {
    return new MarketDataConfig("https://cloud.iexapis.com/stable",
        System.getenv("IEX_PUB_TOKEN"));
  }

  /**
   * At startup, Spring sets up a connection pool manager to the API we want to interact with, in
   * this case the IEX API.
   *
   * @return PoolingHttpClientConnectionManager cm
   */
  @Bean
  public HttpClientConnectionManager hCCMConfig() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }

  /**
   * Spring sets up a DataSource object at runtime in order to have a connection pool to the
   * database, reducing database operation expenses.
   *
   * @return BasicDataSource basicDataSource
   */
  @Bean
  public DataSource dataSource() {

    String jdbcUrl =
        "jdbc:postgresql://" +
            System.getenv("PSQL_HOST") + ":" +
            System.getenv("PSQL_PORT") + "/" +
            System.getenv("PSQL_DB");

    String user = System.getenv("PSQL_USER");
    String pass = System.getenv("PSQL_PASSWORD");

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(jdbcUrl);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(pass);
    return basicDataSource;
  }

}
