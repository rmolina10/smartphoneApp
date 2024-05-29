package smartphone.domain.similarpriced;

import reactor.core.publisher.Flux;

public interface SimilarPricedIdsQueries {

    Flux<Long> findSimilarPricedIds(String id);
}
