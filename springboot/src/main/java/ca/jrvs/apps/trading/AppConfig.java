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

  public static final String HOST_NAME = "";

  @Bean
  public MarketDataConfig marketDataConfig(){
    return new MarketDataConfig("https://cloud.iexapis.com/stable",
        System.getenv("IEX_PUB_TOKEN"));
  }

  @Bean
  public HttpClientConnectionManager hCCMConfig(){
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }

  @Bean
  public DataSource dataSource(){

   String jdbcUrl =
        "jdbc:postgresql://" +
            System.getenv("PSQL_HOST") + ":" +
            System.getenv("PSQL_POST") + "/" +
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
