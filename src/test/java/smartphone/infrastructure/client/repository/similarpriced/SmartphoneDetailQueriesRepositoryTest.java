package smartphone.infrastructure.client.repository.similarpriced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smartphone.domain.exception.SmartphoneNotFoundException;
import smartphone.domain.exception.SmartphoneRepositoryException;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmartphoneDetailQueriesRepositoryTest {

    @Mock
    private WebClient webClient;

    @Value("${smartphones.call.mocks.client.endpoints.get-smartphone}")
    private String callMocksClientEndpointsGetSmartphone;

    @InjectMocks
    private SmartphoneDetailQueriesRepository smartphoneDetailQueriesRepository;

    private static final Long PHONE_ID_1 = 1L;
    private static final SmartphoneDetail PHONE_DETAIL_1 = SmartphoneDetail.builder().id(PHONE_ID_1.toString()).name("Model1").brand("Brand1").price(BigDecimal.valueOf(100.0)).build();

    @Test
    void when_findSmartphoneDetail_then_returnOK() {
        // GIVEN
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSmartphone, PHONE_ID_1)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SmartphoneDetail.class)).thenReturn(Mono.just(PHONE_DETAIL_1));

        // WHEN
        Flux<SmartphoneDetail> smartphoneDetailFlux = smartphoneDetailQueriesRepository.findSmartphoneDetail(Flux.just(PHONE_ID_1));

        // THEN
        List<SmartphoneDetail> resultList = smartphoneDetailFlux.collectList().block();
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(PHONE_DETAIL_1, resultList.get(0));
    }

    @Test
    void when_findSmartphoneDetail_then_returnEmptyResponse() {
        // GIVEN
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSmartphone, PHONE_ID_1)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SmartphoneDetail.class)).thenReturn(Mono.empty());

        // WHEN
        Flux<SmartphoneDetail> smartphoneDetailFlux = smartphoneDetailQueriesRepository.findSmartphoneDetail(Flux.just(PHONE_ID_1));

        // THEN
        List<SmartphoneDetail> resultList = smartphoneDetailFlux.collectList().block();
        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
    }

    @Test
    void when_findSmartphoneDetail_then_returnInternalServerError() {
        // GIVEN
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSmartphone, PHONE_ID_1)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SmartphoneDetail.class)).thenReturn(Mono.error(WebClientResponseException.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null, null)));

        // WHEN
        Flux<SmartphoneDetail> smartphoneDetailFlux = smartphoneDetailQueriesRepository.findSmartphoneDetail(Flux.just(PHONE_ID_1));

        // THEN
        List<SmartphoneDetail> resultList = smartphoneDetailFlux.collectList().block();
        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
    }
}
