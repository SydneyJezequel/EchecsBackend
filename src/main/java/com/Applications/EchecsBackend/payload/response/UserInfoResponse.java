package com.Applications.EchecsBackend.payload.response;

import java.util.List;




/**
 * Classe qui renvoie les données d'un User
 */
public class UserInfoResponse {




    /****************************** Attributs ******************************/

    private Long id;
    private String username;
    private String email;
    private List<String> roles;




    /****************************** Constructeur ******************************/

    public UserInfoResponse(Long id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }




    /****************************** Getters & Setters ******************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }




}
