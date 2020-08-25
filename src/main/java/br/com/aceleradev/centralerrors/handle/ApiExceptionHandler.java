package br.com.aceleradev.centralerrors.handle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.aceleradev.centralerrors.error.Problem;
import br.com.aceleradev.centralerrors.error.Problem.Field;
import br.com.aceleradev.centralerrors.exception.EntityNotFound;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(EntityNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Problem handleEntityNotFound(EntityNotFound ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new Problem(status.value(), LocalDateTime.now(), ex.getMessage(), new ArrayList<Field>());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Field> invalidFields = getErrors(ex);

        Problem problem = new Problem(status.value(), LocalDateTime.now(),"One or more fields are invalid. Fill in correctly and try again.", invalidFields);
        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }
    
    private List<Field> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new Field(getInvalidFieldName(error), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
    
    private String getInvalidFieldName(ObjectError error) {
        return ((FieldError) error).getField();
    }
}
