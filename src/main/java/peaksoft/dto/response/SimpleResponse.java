package peaksoft.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SimpleResponse(HttpStatus httpStatus, String message) {
    public SimpleResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
