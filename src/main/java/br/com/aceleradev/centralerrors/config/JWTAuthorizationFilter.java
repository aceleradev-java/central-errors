package br.com.aceleradev.centralerrors.config;

import static br.com.aceleradev.centralerrors.config.SecurityConstants.HEADER_STRING;
import static br.com.aceleradev.centralerrors.config.SecurityConstants.SECRET;
import static br.com.aceleradev.centralerrors.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.aceleradev.centralerrors.exception.ExpiredJwtToken;
import br.com.aceleradev.centralerrors.service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) return null;
        
        try {
            String username = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            return username != null ?
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
                    
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtToken("Expired token");
        }
    }
}
