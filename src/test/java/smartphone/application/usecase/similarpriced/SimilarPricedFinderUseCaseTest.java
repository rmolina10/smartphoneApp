package smartphone.application.usecase.similarpriced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import smartphone.domain.similarpriced.SimilarPricedIdsQueries;
import smartphone.domain.similarpriced.SmartphoneDetailQueries;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimilarPricedFinderUseCaseTest {

    @Mock
    private SimilarPricedIdsQueries similarPricedIdsQueries;
    @Mock
    private SmartphoneDetailQueries smartphoneDetailQueries;

    @InjectMocks
    private SimilarPricedFinderUseCase similarPricedFinderUseCase;

    private static final String PHONE_ID_1 = "1";
    private static final String PHONE_ID_2 = "2";
    private static final String NAME_2 = "12 pro";
    private static final String BRAND_2 = "Xiaomi";
    private static final BigDecimal PRICE_2 = BigDecimal.valueOf(1099.99);
    private static final String PHONE_ID_3 = "3";
    private static final String NAME_3 = "Magic 5 Pro";
    private static final String BRAND_3 = "Honor";
    private static final BigDecimal PRICE_3 = BigDecimal.valueOf(645.98);

    @Test
    void when_findSimilarPriced_then_returnOK() {
        // GIVEN
        Set<SmartphoneDetail> expectedSmartphoneDetailSet = Set.of(SmartphoneDetail.builder().id(PHONE_ID_2).name(NAME_2).brand(BRAND_2).price(PRICE_2).build(),
                SmartphoneDetail.builder().id(PHONE_ID_3).name(NAME_3).brand(BRAND_3).price(PRICE_3).build());
        Flux<Long> expectedSimilarPricedIdsFlux = Flux.just(2L, 3L);
        Flux<SmartphoneDetail> expectedSmartphoneDetailFlux = Flux.fromIterable(expectedSmartphoneDetailSet);
        when(similarPricedIdsQueries.findSimilarPricedIds(PHONE_ID_1)).thenReturn(expectedSimilarPricedIdsFlux);
        when(smartphoneDetailQueries.findSmartphoneDetail(expectedSimilarPricedIdsFlux)).thenReturn(expectedSmartphoneDetailFlux);

        // WHEN
        Set<SmartphoneDetail> smartphoneDetailsResponse = similarPricedFinderUseCase.findSimilarPriced(PHONE_ID_1);

        // THEN
        assertEquals(expectedSmartphoneDetailSet, smartphoneDetailsResponse);
        verify(similarPricedIdsQueries).findSimilarPricedIds(PHONE_ID_1);
        verify(smartphoneDetailQueries).findSmartphoneDetail(expectedSimilarPricedIdsFlux);
    }
}
