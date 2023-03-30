package com.Applications.EchecsBackend.payload.request;

import javax.validation.constraints.NotBlank;





/**
 * Classe qui récupère les données du user pour la requête de connexion
 */
public class LoginRequest {





    /****************************** Attributs ******************************/

    @NotBlank
    private String username;

    @NotBlank
    private String password;





    /****************************** Getters & Setters ******************************/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
