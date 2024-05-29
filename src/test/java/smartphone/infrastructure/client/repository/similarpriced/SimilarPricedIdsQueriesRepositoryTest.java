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
import smartphone.domain.exception.SmartphoneNotFoundException;
import smartphone.domain.exception.SmartphoneRepositoryException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarPricedIdsQueriesRepositoryTest {

    @Mock
    private WebClient webClient;

    @Value("${smartphones.call.mocks.client.endpoints.get-similar-priced-id}")
    private String callMocksClientEndpointsGetSimilarPriced;

    @InjectMocks
    private SimilarPricedIdsQueriesRepository similarPricedIdsQueriesRepository;

    private static final String PHONE_ID = "1";


    @Test
    void when_findSimilarPricedIds_then_returnOK() {
        // GIVEN
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSimilarPriced, "1")).thenReturn(requestHeadersSpecMock);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Flux<Long> expectedSimilarPricedIdsFlux = Flux.just(2L, 3L);
        when(responseSpecMock.bodyToFlux(Long.class)).thenReturn(Flux.just(2L, 3L));

        // WHEN
        Flux<Long> similarPricedIdsFlux =
                similarPricedIdsQueriesRepository.findSimilarPricedIds(PHONE_ID);

        // THEN
        List<Long> expectedSimilarPricedIdsList = expectedSimilarPricedIdsFlux.collectList().block();
        List<Long> resultSimilarPricedIdsList = similarPricedIdsFlux.collectList().block();
        assertEquals(expectedSimilarPricedIdsList, resultSimilarPricedIdsList);
    }

    @Test
    void when_findSimilarPricedIds_then_returnEmptyResponse() {
        // GIVEN
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSimilarPriced, PHONE_ID)).thenReturn(requestHeadersSpecMock);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(Long.class)).thenReturn(Flux.empty());

        // WHEN
        Flux<Long> similarPricedIdsFlux = similarPricedIdsQueriesRepository.findSimilarPricedIds(PHONE_ID);

        // THEN
        assertNull(similarPricedIdsFlux.blockFirst());
    }

    @Test
    void when_findSimilarPricedIds_then_returnInternalServerError() {
        // GIVEN
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSimilarPriced, PHONE_ID)).thenReturn(requestHeadersSpecMock);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(Long.class)).thenReturn(Flux.error(WebClientResponseException.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), null, null, null)));

        // WHEN
        Flux<Long> similarPricedIdsFlux = similarPricedIdsQueriesRepository.findSimilarPricedIds(PHONE_ID);

        // THEN
        assertThrows(SmartphoneRepositoryException.class, similarPricedIdsFlux::blockFirst);
    }

    @Test
    void when_findSimilarPricedIds_then_returnNotFound() {
        // GIVEN
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        when(requestHeadersUriSpecMock.uri(callMocksClientEndpointsGetSimilarPriced, PHONE_ID)).thenReturn(requestHeadersSpecMock);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(Long.class)).thenReturn(Flux.error(WebClientResponseException.create(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), null, null, null)));

        // WHEN
        Flux<Long> similarPricedIdsFlux = similarPricedIdsQueriesRepository.findSimilarPricedIds(PHONE_ID);

        // THEN
        assertThrows(SmartphoneNotFoundException.class, () -> similarPricedIdsFlux.blockFirst());
    }
}
