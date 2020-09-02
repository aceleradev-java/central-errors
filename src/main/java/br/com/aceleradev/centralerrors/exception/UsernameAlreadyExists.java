package br.com.aceleradev.centralerrors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsernameAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExists(String message) {
        super(message);
    }
    
}
