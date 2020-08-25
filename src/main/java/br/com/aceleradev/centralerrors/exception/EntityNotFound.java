package br.com.aceleradev.centralerrors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityNotFound(String message){
        super(message);
    }

}
