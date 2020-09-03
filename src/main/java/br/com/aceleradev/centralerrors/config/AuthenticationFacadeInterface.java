package br.com.aceleradev.centralerrors.config;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeInterface {
    Authentication getAuthentication();
}
