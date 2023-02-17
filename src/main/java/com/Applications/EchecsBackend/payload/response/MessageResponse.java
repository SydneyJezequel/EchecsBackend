package com.Applications.EchecsBackend.payload.response;




/**
 * Classe qui renvoie un message de retour pour une action donnée.
 */
public class MessageResponse {




    /****************************** Atributs ******************************/

    private String message;




    /****************************** Constructeur ******************************/

    public MessageResponse(String message) {
        this.message = message;
    }




    /****************************** Getters et Setters ******************************/

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
