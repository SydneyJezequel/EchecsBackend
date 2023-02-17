package com.Applications.EchecsBackend.controller.connexion;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * TestController has accessing protected resource methods with role based validations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {




    /****************************** Méthodes ******************************/

    /**
     * Méthode de test accessible à tous les internautes.
     * @return
     */
        @GetMapping("/all")
        public String allAccess() {
            return "Bienvenue sur Echecs Tournament. " +
                    "Connectez-vous pour jouer.";
        }



    /**
     * Méthode de test accessible aux utilisateurs inscrits sur le site.
     * @return
     */
        @GetMapping("/user")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public String userAccess() {
            return "User Content.";
        }



    /**
     * Méthode de test accessible aux modérateurs de l'application.
     * @return
     */
    @GetMapping("/mod")
        @PreAuthorize("hasRole('MODERATOR')")
        public String moderatorAccess() {
            return "Moderator Board.";
        }



    /**
     * Méthode de test accessible aux administrateurs de l'application.
     * @return
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Bienvenue sur l'Admin Board. Dans cette écran, vous pouvez gérer les utilisateurs de ce site.";
    }




}
