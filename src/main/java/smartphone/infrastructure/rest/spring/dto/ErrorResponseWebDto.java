package smartphone.infrastructure.rest.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Schema
@Generated
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseWebDto {

    @Schema(description = "HTTP status code")
    private String code;

    @Schema(description = "Error description")
    private String description;

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Timestamp")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime timestamp;

    @Schema(description = "Path")
    private String path;
}
