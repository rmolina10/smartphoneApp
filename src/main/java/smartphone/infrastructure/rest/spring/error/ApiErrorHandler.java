package smartphone.infrastructure.rest.spring.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import smartphone.domain.exception.ErrorsEnum;
import smartphone.domain.exception.SmartphoneException;
import smartphone.domain.exception.SmartphoneNotFoundException;
import smartphone.domain.exception.SmartphoneRepositoryException;
import smartphone.infrastructure.rest.spring.dto.ErrorResponseWebDto;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
    private static final String DEFAULT_LOG_MESSAGE = "Error performing Rest operation.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseWebDto> handleException(
            Exception ex, HttpServletRequest request) {
        return buildErrorResponseException(ex, request);
    }

    @ExceptionHandler(SmartphoneException.class)
    public ResponseEntity<ErrorResponseWebDto> handleSmartphoneException(
            SmartphoneException ex, HttpServletRequest request) {
        return buildErrorResponseSmartphoneException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(SmartphoneNotFoundException.class)
    public ResponseEntity<ErrorResponseWebDto> handleSmartphoneNotFoundException(
            SmartphoneNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponseSmartphoneException(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SmartphoneRepositoryException.class)
    public ResponseEntity<ErrorResponseWebDto> handleSmartphoneRepositoryException(
            SmartphoneRepositoryException ex, HttpServletRequest request) {
        return buildErrorResponseSmartphoneException(ex, HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    private void logException(Throwable t) {
        log.error(DEFAULT_LOG_MESSAGE, t);
    }

    private ResponseEntity<ErrorResponseWebDto> buildErrorResponseException(
            Exception ex, HttpServletRequest request) {

        logException(ex);

        return new ResponseEntity<>(
                ErrorResponseWebDto.builder()
                        .code(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode())
                        .description(ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription())
                        .message(ex.getLocalizedMessage())
                        .timestamp(OffsetDateTime.now())
                        .path(request.getServletPath())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseWebDto> buildErrorResponseSmartphoneException(
            SmartphoneException ex, HttpStatus httpStatus, HttpServletRequest request) {
        logException(ex);
        return new ResponseEntity<>(
                ErrorResponseWebDto.builder()
                        .code(ex.getError().getCode())
                        .description(ex.getError().getDescription())
                        .message(getFormattedMessage(ex.getError(), ex.getValue()))
                        .timestamp(OffsetDateTime.now())
                        .path(request.getServletPath())
                        .build(),
                httpStatus);
    }

    private String getFormattedMessage(ErrorsEnum errorsEnum, Object[] params) {
        return String.format(errorsEnum.getMessage(), params);
    }
}
