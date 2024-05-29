package smartphone.application.usecase.similarpriced;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import smartphone.application.port.out.similarpriced.SimilarPricedFinder;
import smartphone.domain.similarpriced.SimilarPricedIdsQueries;
import smartphone.domain.similarpriced.SmartphoneDetailQueries;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SimilarPricedFinderUseCase implements SimilarPricedFinder {

    private final SimilarPricedIdsQueries similarPricedIdsQueries;
    private final SmartphoneDetailQueries smartphoneDetailQueries;

    @Override
    public Set<SmartphoneDetail> findSimilarPriced(@NonNull String id) {
        log.debug(
                "Searching similar priced with parameter 'id': {}",
                id);
        Flux<Long> smartphoneIds = similarPricedIdsQueries.findSimilarPricedIds(id);

        Flux<SmartphoneDetail> smartphonesDetails = smartphoneDetailQueries.findSmartphoneDetail(smartphoneIds);

        Set<SmartphoneDetail> result = smartphonesDetails.collect(Collectors.toSet()).block();

        return Objects.requireNonNullElse(result, new HashSet<>());
    }
}
