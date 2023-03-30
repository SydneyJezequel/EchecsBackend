package com.Applications.EchecsBackend.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;





/**
 * AuthenticationEntryPoint will catch authentication error.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {





    /****************************** Attributs ******************************/

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);





    /****************************** Méthodes ******************************/

    /**
     * Méthode qui renvoie un message d'erreur en cas de problème d'authentification.
     *
     * This method will be triggerd anytime
     * unauthenticated User requests a secured HTTP resource
     * and an AuthenticationException is thrown.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }





}
