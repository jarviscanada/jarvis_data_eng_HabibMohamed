package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao" , "ca.jrvs.apps.trading.service"})
public class TestConfig {

  @Bean
  public MarketDataConfig marketDataConfig(){
    return new MarketDataConfig("https://cloud.iexapis.com/stable",
        System.getenv("IEX_PUB_TOKEN"));
  }

  @Bean
  public DataSource dataSource(){

    BasicDataSource basicDataSource = new BasicDataSource();

    basicDataSource.setUrl(System.getenv("PSQL_URL"));
    basicDataSource.setUsername(System.getenv("PSQL_USER"));
    basicDataSource.setPassword(System.getenv("PSQL_PASSWORD"));

    return basicDataSource;

  }

  @Bean
  public HttpClientConnectionManager httpClientConnectionManager(){

    PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
    pcm.setMaxTotal(50);
    pcm.setDefaultMaxPerRoute(50);
    return pcm;

  }

}
