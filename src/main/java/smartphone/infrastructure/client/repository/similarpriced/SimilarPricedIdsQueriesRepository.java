package smartphone.infrastructure.client.repository.similarpriced;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smartphone.domain.exception.ErrorsEnum;
import smartphone.domain.exception.SmartphoneNotFoundException;
import smartphone.domain.exception.SmartphoneRepositoryException;
import smartphone.domain.similarpriced.SimilarPricedIdsQueries;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SimilarPricedIdsQueriesRepository implements SimilarPricedIdsQueries {

    private final WebClient webClient;

    @Value("${smartphones.call.mocks.client.endpoints.get-similar-priced-id}")
    private String callMocksClientEndpointsGetSimilarPriced;

    @Override
    public Flux<Long> findSimilarPricedIds(String id) {
        log.debug("Getting similar priced ids: 'id: {}'", id);
        return webClient.get()
                .uri(callMocksClientEndpointsGetSimilarPriced, id)
                .retrieve()
                .bodyToFlux(Long.class)
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientResponseException.NotFound) {
                        log.error("Smartphone not found: {}", id);
                        return Mono.error(new SmartphoneNotFoundException(ErrorsEnum.SMARTPHONE_NOT_FOUND));
                    } else {
                        log.error("Error occurred during called: {}", throwable.getMessage());
                        return Mono.error(new SmartphoneRepositoryException(ErrorsEnum.REPOSITORY_EXCEPTION, throwable, throwable.getMessage()));
                    }
                });
    }
}
