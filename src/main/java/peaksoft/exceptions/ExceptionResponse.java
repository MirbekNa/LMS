package peaksoft.exceptions;


import org.springframework.http.HttpStatus;

public record ExceptionResponse(HttpStatus httpStatus, String exeptionClassName, String message) {

}
