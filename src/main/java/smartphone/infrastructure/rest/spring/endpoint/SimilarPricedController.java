package smartphone.infrastructure.rest.spring.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import smartphone.application.port.out.similarpriced.SimilarPricedFinder;
import smartphone.infrastructure.rest.spring.mapper.SimilarPricedMapper;
import smartphone.smartphone.infrastucture.rest.spring.spec.DefaultApi;
import smartphone.smartphone.infrastucture.rest.spring.spring.dto.SmartphoneDetailWebDto;

import java.util.Set;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class SimilarPricedController implements DefaultApi {

    private final SimilarPricedFinder similarPricedFinder;
    private final SimilarPricedMapper mapper;

    @Override
    public ResponseEntity<Set<SmartphoneDetailWebDto>> getSmartphonesSimilarPrices(String id) {
        log.info("Init getSmartphonesSimilarPrices with id: '{}'", id);
        return ResponseEntity.ok(mapper.toDto(similarPricedFinder.findSimilarPriced(id)));
    }
}
