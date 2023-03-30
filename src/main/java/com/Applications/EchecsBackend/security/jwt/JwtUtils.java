package com.Applications.EchecsBackend.security.jwt;

import java.util.Date;
import com.Applications.EchecsBackend.service.connexion.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import io.jsonwebtoken.*;





/**
 * Composant qui contient les fonctionnalités qui gèrent les Tokens.
 *
 * JwtUtils provides methods for generating, parsing, validating JWT
 * This class has 3 main funtions:
 * getJwtFromCookies: get JWT from Cookies by Cookie name
 * generateJwtCookie: generate a Cookie containing JWT from username, date, expiration, secret
 * getCleanJwtCookie: return Cookie with null value (used for clean Cookie)
 * getUserNameFromJwtToken: get username from JWT
 * validateJwtToken: validate a JWT with a secret
 */
@Component
public class JwtUtils {





    /****************************** Attributs ******************************/

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${bezkoder.app.jwtCookieName}")
    private String jwtCookie;





    /****************************** Méthodes ******************************/

    /**
     * Méthode qui renvoie un Token à partir d'un cookie.
     * getJwtFromCookies: get JWT from Cookies by Cookie name
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }




    /**
     * Méthode qui génère un cookie qui contient un token.
     * generateJwtCookie: generate a Cookie containing JWT from username, date, expiration, secret
     * @param userPrincipal
     * @return
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }




    /**
     * Méthode qui nettoie un cookie.
     * getCleanJwtCookie: return Cookie with null value (used for clean Cookie)
     * @return
     */
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }




    /**
     * Méthode qui récupère le username d'un user dans un token.
     * getUserNameFromJwtToken: get username from JWT
     * @param token
     * @return
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }




    /**
     * Méthode qui contrôle la validité du token via un secret.
     * validateJwtToken: validate a JWT with a secret
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }




    /**
     * Méthode qui génère un token à partir du username d'un user.
     * Generate a token from username passed in parameter.
     * @param username
     * @return
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }





}
