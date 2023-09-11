package peaksoft.exceptions.glocalHandlerException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.exceptions.ExceptionResponse;
import peaksoft.exceptions.InvalidEmailException;
import peaksoft.exceptions.NotFoundException;

@RestControllerAdvice
public class LocalHandlerException {

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse invalidEmailException(InvalidEmailException i) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, i.getClass().getSimpleName(), i.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse notFound(NotFoundException n) {
        return new ExceptionResponse(HttpStatus.FOUND, n.getClass().getSimpleName(), n.getMessage());
    }
}
