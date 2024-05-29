package smartphone.infrastructure.rest.spring.endpoint;

import org.mockito.Mock;
import smartphone.application.port.out.similarpriced.SimilarPricedFinder;
import smartphone.domain.similarpriced.model.SmartphoneDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import smartphone.infrastructure.rest.spring.mapper.SimilarPricedMapper;
import smartphone.smartphone.infrastucture.rest.spring.spring.dto.SmartphoneDetailWebDto;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class SimilarPricedControllerTest {

    @Mock
    private SimilarPricedFinder similarPricedFinder;
    @Mock
    private SimilarPricedMapper similarPricedMapper;

    @InjectMocks
    private SimilarPricedController similarPricedController;

    private static final String PHONE_ID = "1";
    private static final String NAME = "Galaxy 12";
    private static final String BRAND = "Samsung";
    private static final BigDecimal PRICE = BigDecimal.valueOf(976.98);

    @Test
    void when_getSmartphonesSimilarPrices_then_returnOKStatus() {
        // GIVEN
        Set<SmartphoneDetailWebDto> smartphoneDetailWebDtoSet = Set.of(new SmartphoneDetailWebDto(PHONE_ID, NAME, BRAND, PRICE));
        Set<SmartphoneDetail> smartphoneDetailSet = Set.of(SmartphoneDetail.builder().id(PHONE_ID).name(NAME).brand(BRAND).price(PRICE).build());
        doReturn(smartphoneDetailSet).when(similarPricedFinder).findSimilarPriced(anyString());
        doReturn(smartphoneDetailWebDtoSet).when(similarPricedMapper).toDto(anySet());

        // WHEN
        ResponseEntity<Set<SmartphoneDetailWebDto>> responseEntity = similarPricedController.getSmartphonesSimilarPrices(PHONE_ID);

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(smartphoneDetailWebDtoSet, responseEntity.getBody());
    }
}
