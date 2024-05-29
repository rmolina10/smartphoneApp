package smartphone.infrastructure.client.webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${smartphones.call.mocks.client.url}")
    private String callMocksClientUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(callMocksClientUrl)
                .build();
    }
}
