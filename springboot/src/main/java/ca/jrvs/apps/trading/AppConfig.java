package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
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
}
