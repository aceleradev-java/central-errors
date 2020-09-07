package br.com.aceleradev.centralerrors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class ExpiredJwtToken extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExpiredJwtToken(String message) {
        super(message);
    }

}
