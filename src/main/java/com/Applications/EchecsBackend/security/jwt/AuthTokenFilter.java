package com.Applications.EchecsBackend.security.jwt;

import java.io.IOException;
import com.Applications.EchecsBackend.service.connexion.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;




/**
 * This class define a filter that executes once per request.
 * OncePerRequestFilter makes a single execution for each request to our API.
 * It provides a doFilterInternal() method that we will implement parsing & validating JWT,
 * loading User details (using UserDetailsService),
 * checking Authorization (using UsernamePasswordAuthenticationToken).
 */
public class AuthTokenFilter extends OncePerRequestFilter {




    /****************************** Dépendances ******************************/

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);




    /****************************** Méthodes ******************************/

    /**
     * Méthode qui authentifie le user.
     *
     *
     * This method is used for create an Authentification object from a JWT from HTTP Cookies.
     * It used filter to create the Authentification object.
     *
     * What we do inside doFilterInternal():
     * – get JWT from the HTTP Cookies
     * – if the request has JWT, validate it, parse username from it
     * – from username, get UserDetails to create an Authentication object
     * – set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // Conversion requête HTTP en String.
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // Contrôle/Validation du token.
                String username = jwtUtils.getUserNameFromJwtToken(jwt); // Récupération du username à partir du Token.

                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Récupération du User
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities()); // Authentification du user (username, password, rôles).
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // builds the details object from an HttpServletRequest object
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }



    /**
     * Requête qui traduit le token d'un Cookie en String.
     *
     * This request parse Jwt from Cookie to String.
     * @param request
     * @return
     */
    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        return jwt;
    }




}