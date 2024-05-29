package smartphone.infrastructure.client.repository.similarpriced;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smartphone.domain.similarpriced.SmartphoneDetailQueries;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SmartphoneDetailQueriesRepository implements SmartphoneDetailQueries {

    private final WebClient webClient;

    @Value("${smartphones.call.mocks.client.endpoints.get-smartphone}")
    private String callMocksClientEndpointsGetSmartphone;

    @Override
    public Flux<SmartphoneDetail> findSmartphoneDetail(Flux<Long> smartphoneIds) {
        log.debug("Getting smartphone details: 'smartphoneIds: {}'", smartphoneIds);
        return smartphoneIds.flatMap(smartphoneId ->
                webClient.get()
                        .uri(callMocksClientEndpointsGetSmartphone, smartphoneId)
                        .retrieve()
                        .bodyToMono(SmartphoneDetail.class)
                        .onErrorResume(WebClientResponseException.class, ex -> {
                            log.error("Error fetching details of smartphone ID {}: {}", smartphoneId, ex.getMessage());
                            return Mono.empty();
                        })
        );
    }
}
