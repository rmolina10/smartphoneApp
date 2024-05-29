package smartphone.infrastructure.rest.spring.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import smartphone.domain.exception.ErrorsEnum;
import smartphone.domain.exception.SmartphoneException;
import smartphone.domain.exception.SmartphoneNotFoundException;
import smartphone.domain.exception.SmartphoneRepositoryException;
import smartphone.infrastructure.rest.spring.dto.ErrorResponseWebDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ApiErrorHandlerTest {

    private static final String DEFAULT_PATH = "some path";
    private static final String ERROR_MESSAGE = "some error message";
    private final ApiErrorHandler apiErrorHandler = new ApiErrorHandler();

    @Test
    void handleException() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);
        Exception exception = new RuntimeException(ERROR_MESSAGE);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleException(exception, request);

        // THEN
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
        assertEquals(ERROR_MESSAGE, errorResponseWebDto.getMessage());
    }

    @Test
    void handleSmartphoneException() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);
        SmartphoneException smartphoneException = new SmartphoneException(ErrorsEnum.INTERNAL_SERVER_ERROR);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleSmartphoneException(smartphoneException, request);

        // THEN
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
        assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getMessage(), errorResponseWebDto.getMessage());
        assertEquals(
                ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription(), errorResponseWebDto.getDescription());
        assertNotNull(errorResponseWebDto.getTimestamp());
        assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());
    }

    @Test
    void handleSmartphoneException_withThrowableAndMessage() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);

        Throwable throwable = new RuntimeException(ERROR_MESSAGE);
        SmartphoneException smartphoneException =
                new SmartphoneException(ErrorsEnum.INTERNAL_SERVER_ERROR, throwable, ERROR_MESSAGE);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleSmartphoneException(smartphoneException, request);

        // THEN
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
        assertEquals(
                ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription(), errorResponseWebDto.getDescription());
        assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getMessage(), errorResponseWebDto.getMessage());
        assertNotNull(errorResponseWebDto.getTimestamp());
        assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());
    }

    @Test
    void handleSmartphoneNotFoundException() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);

        SmartphoneNotFoundException smartphoneNotFoundException =
                new SmartphoneNotFoundException(ErrorsEnum.SMARTPHONE_NOT_FOUND);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleSmartphoneNotFoundException(smartphoneNotFoundException, request);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.SMARTPHONE_NOT_FOUND.getCode(), errorResponseWebDto.getCode());
        assertEquals(ErrorsEnum.SMARTPHONE_NOT_FOUND.getDescription(), errorResponseWebDto.getDescription());
        assertEquals(ErrorsEnum.SMARTPHONE_NOT_FOUND.getMessage(), errorResponseWebDto.getMessage());
        assertNotNull(errorResponseWebDto.getTimestamp());
        assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());
    }

    @Test
    void handleSmartphoneRepositoryException_withThrowableAndMessage() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);

        SmartphoneRepositoryException smartphoneRepositoryException =
                new SmartphoneRepositoryException(ErrorsEnum.REPOSITORY_EXCEPTION);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleSmartphoneRepositoryException(smartphoneRepositoryException, request);

        // THEN
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getCode(), errorResponseWebDto.getCode());
        assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getMessage(), errorResponseWebDto.getMessage());
        assertEquals(
                ErrorsEnum.REPOSITORY_EXCEPTION.getDescription(), errorResponseWebDto.getDescription());
        assertNotNull(errorResponseWebDto.getTimestamp());
        assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());
    }

    @Test
    void handleSmartphoneRepositoryException() {
        // GIVEN
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(DEFAULT_PATH);

        Throwable throwable = new RuntimeException(ERROR_MESSAGE);
        SmartphoneRepositoryException smartphoneRepositoryException =
                new SmartphoneRepositoryException(ErrorsEnum.REPOSITORY_EXCEPTION, throwable, ERROR_MESSAGE);

        // WHEN
        ResponseEntity<ErrorResponseWebDto> response =
                apiErrorHandler.handleSmartphoneRepositoryException(smartphoneRepositoryException, request);

        // THEN
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        ErrorResponseWebDto errorResponseWebDto = response.getBody();
        assertNotNull(errorResponseWebDto);

        assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getCode(), errorResponseWebDto.getCode());
        assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getMessage(), errorResponseWebDto.getMessage());
        assertEquals(
                ErrorsEnum.REPOSITORY_EXCEPTION.getDescription(), errorResponseWebDto.getDescription());
        assertNotNull(errorResponseWebDto.getTimestamp());
        assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());
    }
}
