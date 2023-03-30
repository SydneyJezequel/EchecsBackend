package com.Applications.EchecsBackend.configuration;

import com.Applications.EchecsBackend.security.jwt.AuthEntryPointJwt;
import com.Applications.EchecsBackend.security.jwt.AuthTokenFilter;
import com.Applications.EchecsBackend.service.connexion.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;





/**
 * Classe qui configure la sécurité sur l'application.
 */
@Configuration
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig {





    /****************************** Dépendances ******************************/

    // Dépendance : Service qui charge les objets Users.
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    // Dépendance : Classe qui charge les erreurs d'authentification.
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;





    /****************************** Méthodes ******************************/


    /**
     * Méthode qui authentifie les requêtes.
     * @return
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }




    /**
     * Méthode qui authentifie le User via ses credentials.
     *
     * DaoAuthenticationProvider is an AuthenticationProvider implementation
     * that uses a UserDetailsService and PasswordEncoder
     * to authenticate a username and password.
     * @return
     * @throws Exception
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }




    /**
     * Cette méthode exécute une requête d'authentification.
     *
     * AuthenticationManager is the API that defines
     * how Spring Security’s Filters perform authentication.
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }




    /**
     * Cette méthode encode le mot de passe.
     * @return
     * @throws Exception
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    /**
     * Cette méthode définit les Filtres de sécurité appliqués sur les requêtes exécutées dans le Backend.
     * @param http
     * @return
     * @throws Exception
     * As explained in the CSRF post, cross-origin resource sharing (CORS) is a
     * safety mechanism that prevents scripts from executing malicious code in websites
     * and lets scripts do cross-domain calls.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .shouldFilterAllDispatcherTypes(false)
                .requestMatchers("/api/auth/**").permitAll() // All requests are allowed (permitted) - the user does not have to authenticate
                .requestMatchers("/api/test/**").permitAll() // All requests are allowed (permitted) - the user does not have to authenticate
                .requestMatchers("/api/user/**").permitAll() // All requests are allowed (permitted) - the user does not have to authenticate
                .anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }





}
/*
AUTHENTIFICATION PAR TOKEN :
On va activer l’authentification basée sur un Token dans Spring MVC en suivant les étapes suivantes :

1-L’utilisateur envoie ses informations d’identification (nom d’utilisateur et mot de passe) au serveur.
2-Le serveur authentifie les informations d’identification et génère un Token.
3-Le serveur stocke le Token généré précédemment dans une zone de stockage avec l’identifiant de l’utilisateur et une date d’expiration.
4-Le serveur envoie le Token généré à l’utilisateur.
5-Le serveur, dans chaque demande, extrait le Token de la demande entrante. Avec ce Token, le serveur recherche les détails de l’utilisateur pour effectuer l’authentification et l’autorisation:
* Si le Token est valide, le serveur accepte la demande.
* Si le Token n’est pas valide, le serveur refuse la demande
 */