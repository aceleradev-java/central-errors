package br.com.aceleradev.centralerrors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ActionNotAllowed extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ActionNotAllowed(String message) {
        super(message);
    }
}
