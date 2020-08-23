package br.com.aceleradev.centralerrors.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    static final String SECRET = "1Glx42TVpZu1TuPmNf5TBOXFxPHzdji1EpkmfeEa2kQg7HpNTeDQi";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000L;
}
